package com.itellyou.api.controller.question;

import com.itellyou.model.collab.CollabInfoModel;
import com.itellyou.model.common.ResultModel;
import com.itellyou.model.question.*;
import com.itellyou.model.sys.EntityType;
import com.itellyou.model.user.UserBankType;
import com.itellyou.model.user.UserDraftModel;
import com.itellyou.model.user.UserInfoModel;
import com.itellyou.service.collab.CollabInfoService;
import com.itellyou.service.question.*;
import com.itellyou.service.user.UserDraftService;
import com.itellyou.service.user.UserSingleService;
import com.itellyou.util.DateUtils;
import com.itellyou.util.IPUtils;
import com.itellyou.util.StringUtils;
import com.itellyou.util.annotation.MultiRequestBody;
import com.itellyou.util.serialize.filter.Labels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/question/{questionId:\\d+}/answer")
public class AnswerDocController {

    private final QuestionAnswerSearchService searchService;
    private final QuestionAnswerSingleService answerSingleService;
    private final CollabInfoService collabInfoService;
    private final UserSingleService userSearchService;
    private final UserDraftService draftService;
    private final QuestionSearchService questionSearchService;
    private final QuestionAnswerPaidReadService answerPaidReadService;
    private final QuestionAnswerDocService answerDocService;
    private final QuestionAnswerVersionSearchService versionSearchService;
    private final QuestionAnswerVersionSingleService versionSingleService;

    @Autowired
    public AnswerDocController(QuestionAnswerSearchService searchService, QuestionAnswerSingleService answerSingleService, CollabInfoService collabInfoService, UserSingleService userSearchService, UserDraftService draftService, QuestionSearchService questionSearchService, QuestionAnswerPaidReadService answerPaidReadService, QuestionAnswerDocService answerDocService, QuestionAnswerVersionSearchService versionSearchService, QuestionAnswerVersionSingleService versionSingleService){
        this.searchService = searchService;
        this.answerSingleService = answerSingleService;
        this.collabInfoService = collabInfoService;
        this.userSearchService = userSearchService;
        this.draftService = draftService;
        this.questionSearchService = questionSearchService;
        this.answerPaidReadService = answerPaidReadService;
        this.answerDocService = answerDocService;
        this.versionSearchService = versionSearchService;
        this.versionSingleService = versionSingleService;
    }

    @PostMapping("/create")
    public ResultModel create(HttpServletRequest request, UserInfoModel userInfoModel, @PathVariable Long questionId, @MultiRequestBody String content, @MultiRequestBody String html, @MultiRequestBody String save_type){
        if(userInfoModel == null){
            return new ResultModel(401,"未登录");
        }

        String clientIp = IPUtils.getClientIp(request);
        Long ipLong = IPUtils.toLong(clientIp);

        // 如果已创建过回答，就为其新增一个草稿版本
        QuestionAnswerModel answerModel = answerSingleService.findByQuestionIdAndUserId(questionId,userInfoModel.getId(),"draft");
        if(answerModel != null){
            try{
                //已发布的问答可能有其他用户在编辑中，所以不能单纯的新增一个版本
                if(answerModel.isPublished()){
                    return new ResultModel(0,"只能创建一个回答");
                }
                if(answerModel.isDisabled() || answerModel.isDeleted()) return new ResultModel(0,"回答状态不正确");
                QuestionAnswerVersionModel versionModel = answerDocService.addVersion(answerModel.getId(),questionId,userInfoModel.getId(),content,html,StringUtils.getFragmenter(content),"一般编辑更新",save_type,ipLong,false,false);
                if(versionModel == null) return new ResultModel(0,"创建失败");
                return new ResultModel(answerModel.getId());
            }catch (Exception e){
                return new ResultModel(0,e.getMessage());
            }
        }
        try{
            Long id = answerDocService.create(questionId,userInfoModel.getId(),content,html,StringUtils.getFragmenter(content),"创建回答",save_type,ipLong);
            if(id == null) return new ResultModel(0,"创建失败");
            return new ResultModel(id);
        }catch (Exception e){
            return new ResultModel(0,"创建失败，" + e.getMessage());
        }
    }

    @GetMapping("/{id:\\d+}/edit")
    public ResultModel draft(HttpServletRequest request, UserInfoModel userInfoModel, @PathVariable Long id, @PathVariable Long questionId, @RequestParam(required = false) boolean ot){
        if(userInfoModel == null){
            return new ResultModel(401,"未登录");
        }
        //获取回答详情
        QuestionAnswerDetailModel detailModel = searchService.getDetail(id,questionId,"draft",null,null,true,null,false,null,false);
        if(detailModel == null || detailModel.isDisabled() || detailModel.isDeleted()){
            return new ResultModel(404,"无可用的回答");
        }
        //暂时只能创建者有权限编辑
        if(!detailModel.getCreatedUserId().equals(userInfoModel.getId())){
            return new ResultModel(401,"无权限编辑");
        }

        if(ot){
            String clientIp = IPUtils.getClientIp(request);
            String key = "question/" + questionId + "/answer/" + detailModel.getId();
            CollabInfoModel collabInfoModel = collabInfoService.createDefault(key,userInfoModel.getId(),clientIp);
            if(collabInfoModel == null){
                return new ResultModel(0,"创建协作失败");
            }
            detailModel.setCollab(collabInfoModel);
        }
        return new ResultModel(detailModel,new Labels.LabelModel(QuestionAnswerDetailModel.class,"draft","collab"));
    }

    @PutMapping("/{id:\\d+}/content")
    public ResultModel content(HttpServletRequest request, UserInfoModel userInfoModel, @PathVariable Long id, @PathVariable Long questionId, @MultiRequestBody String content, @MultiRequestBody String html, @MultiRequestBody String save_type){
        if(userInfoModel == null){
            return new ResultModel(401,"未登录");
        }
        String clientIp = IPUtils.getClientIp(request);
        Long ipLong = IPUtils.toLong(clientIp);
        try {
            QuestionAnswerModel answerModel = answerSingleService.findById(id);
            if(answerModel == null || answerModel.isDisabled() || answerModel.isDeleted()) return new ResultModel(404,"无可用回答");
            //暂时只能创建者有权限编辑
            if(!answerModel.getCreatedUserId().equals(userInfoModel.getId())){
                return new ResultModel(401,"无权限编辑");
            }
            String description = StringUtils.getFragmenter(content);
            QuestionAnswerVersionModel versionModel = answerDocService.addVersion(answerModel.getId(),questionId,userInfoModel.getId(),content,html,description,"一般编辑更新",save_type,ipLong,false,false);
            if(versionModel == null) return new ResultModel(0,"更新内容失败");
            QuestionAnswerDetailModel detailModel = searchService.getDetail(answerModel.getId(),questionId,"draft",null,userInfoModel.getId());

            return new ResultModel(detailModel,new Labels.LabelModel(QuestionAnswerDetailModel.class,"draft"));
        }catch (Exception e){
            return new ResultModel(0,e.getMessage());
        }
    }

    @PutMapping("/{id:\\d+}/paidread")
    public ResultModel paidRead(UserInfoModel userInfoModel, @PathVariable Long id, @PathVariable Long questionId, @RequestBody Map<String ,Object> params){
        QuestionAnswerModel infoModel = answerSingleService.findById(id);
        try{
            if(infoModel == null || infoModel.isDisabled() || infoModel.isDeleted()) return new ResultModel(404,"无可用回答");
            //暂时只能创建者有权限编辑
            if(!infoModel.getCreatedUserId().equals(userInfoModel.getId())){
                return new ResultModel(401,"无权限编辑");
            }
            QuestionAnswerPaidReadModel paidReadModel = new QuestionAnswerPaidReadModel();
            paidReadModel.setAnswerId(id);
            paidReadModel.setPaidToRead(false);
            Object paidObject = params.get("paid");
            if(paidObject != null){
                paidReadModel.setPaidToRead(true);
                Map<String,Object> paidMap = (Map<String,Object>)paidObject;
                UserBankType bankType = UserBankType.valueOf(paidMap.get("type").toString().toUpperCase());
                if(bankType.equals(UserBankType.SCORE)) throw new Exception("参数错误");
                paidReadModel.setPaidType(bankType);
                Double amount = Math.abs(Double.valueOf(paidMap.get("amount").toString()));
                paidReadModel.setPaidAmount(amount);
            }
            Object starObject = params.get("star");
            paidReadModel.setStarToRead(false);
            if(starObject != null){
                paidReadModel.setStarToRead(Boolean.valueOf(starObject.toString()));
            }
            Object scaleObject = params.getOrDefault("scale",0);
            if(scaleObject == null) scaleObject = 0;
            Double scale = Math.abs(Double.valueOf(scaleObject.toString()));
            if(scale > 50) scale = 50.0;
            paidReadModel.setFreeReadScale(scale == 0 ? 0 : new BigDecimal(scale).divide(new BigDecimal(100)).doubleValue());
            if(paidReadModel.getStarToRead() || paidReadModel.getPaidToRead()){
                if(answerPaidReadService.insertOrUpdate(paidReadModel) != 1) throw new Exception("设置失败");
            }else{
                answerPaidReadService.deleteByAnswerId(id);
            }
            QuestionAnswerDetailModel detailModel = searchService.getDetail(id,questionId,"draft",null,userInfoModel.getId());
            return new ResultModel(detailModel,new Labels.LabelModel(QuestionAnswerDetailModel.class,"draft"));
        }catch (Exception e){
            return new ResultModel(0,e.getMessage());
        }
    }

    @PutMapping(value = {"/{id:\\d+}/rollback","/rollback"})
    public ResultModel rollback(HttpServletRequest request, UserInfoModel userInfoModel, @PathVariable(required = false) Long id, @PathVariable Long questionId, @MultiRequestBody Long version_id){
        if(userInfoModel == null){
            return new ResultModel(401,"未登录");
        }
        String clientIp = IPUtils.getClientIp(request);
        Long ip = IPUtils.toLong(clientIp);
        QuestionAnswerVersionModel answerVersion = null;
        // 在回答未发布的情况下恢复用户之前的版本，此处只有创建用户在未发布回答时才能访问到
        if(id == null){
            QuestionDetailModel questionModel = questionSearchService.getDetail(questionId);
            if(questionModel == null) return new ResultModel(404,"question is not find ");
            //一个用户只能创建一个正常状态的回答
            QuestionAnswerDetailModel detailModel = searchService.getDetail(null,questionId,"draft",null,userInfoModel.getId(),true,null,false,false,false);
            if(detailModel == null){
                return new ResultModel(404,"Not find");
            }
            //获取需要恢复的版本数据
            answerVersion = versionSingleService.find(version_id);
            if(answerVersion == null || !detailModel.getId().equals(answerVersion.getAnswerId())){
                return  new ResultModel(404,"无可用的版本");
            }
            //如果用户恢复的是之前的最后一个版本，那么直接返回这个版本
            if(detailModel.getDraft() != null && detailModel.getDraft().equals(answerVersion.getVersion())){
                String url = "/question/" + questionId + "/answer/" + id.toString();
                String title = questionModel.getTitle();
                title = StringUtils.isEmpty(title) ? "无标题" : title;
                UserDraftModel draftModel = new UserDraftModel(detailModel.getCreatedUserId(),url,title,StringUtils.getFragmenter(answerVersion.getContent()), EntityType.ANSWER,id, DateUtils.toLocalDateTime(),ip,userInfoModel.getId());
                draftService.insertOrUpdate(draftModel);
                return new ResultModel(detailModel,new Labels.LabelModel(QuestionAnswerDetailModel.class,"draft"));
            }
            //其它版本，需要重新创建一个新的版本
            id = detailModel.getId();
        }else{
            QuestionAnswerModel answerModel = answerSingleService.findById(id);
            if(answerModel == null || answerModel.isDisabled() || answerModel.isDeleted()) return new ResultModel(404,"无可用回答");
            //暂时只能创建者有权限编辑
            if(!answerModel.getCreatedUserId().equals(userInfoModel.getId())){
                return new ResultModel(401,"无权限编辑");
            }
            id = answerModel.getId();
        }

        answerVersion = answerVersion == null ? versionSingleService.find(version_id) : answerVersion;
        if(answerVersion == null || !id.equals(answerVersion.getAnswerId())){
            return  new ResultModel(404,"无可用的版本");
        }

        try {
            QuestionAnswerVersionModel versionModel = answerDocService.addVersion(id, questionId, userInfoModel.getId(), answerVersion.getContent(), answerVersion.getHtml(),answerVersion.getDescription(), "回滚到版本[" + answerVersion.getVersion() + "]", "rollback", ip ,false,true);
            if(versionModel == null) return new ResultModel(0,"回滚失败");
        }catch (Exception e){
            return new ResultModel(0,"回滚失败");
        }
        QuestionAnswerDetailModel detailModel = searchService.getDetail(id,questionId,"draft",null,userInfoModel.getId());
        return new ResultModel(detailModel,new Labels.LabelModel(QuestionAnswerDetailModel.class,"draft"));
    }

    @PutMapping("/{id:\\d+}/publish")
    public ResultModel publish(HttpServletRequest request, UserInfoModel userInfoModel, @PathVariable Long questionId, @PathVariable Long id, @MultiRequestBody String remark){
        if(userInfoModel == null){
            return new ResultModel(401,"未登录");
        }
        QuestionAnswerDetailModel detailModel = searchService.getDetail(id,questionId,"draft",null,null,true,null,false,null,false);
        if(detailModel == null){
            return new ResultModel(404,"无可用的回答");
        }
        //暂时只能创建者有权限编辑
        if(!detailModel.getCreatedUserId().equals(userInfoModel.getId())){
            return new ResultModel(401,"无权限发布");
        }
        try {
            QuestionAnswerVersionModel versionModel = answerDocService.addVersion(id, questionId, userInfoModel.getId(), detailModel.getContent(), detailModel.getHtml(),detailModel.getDescription(), remark, "publish", IPUtils.toLong(request),true,true);
            if(versionModel == null) return new ResultModel(0,"发布失败");
        }catch (Exception e){
            return new ResultModel(0,"发布失败");
        }
        detailModel = searchService.getDetail(id,questionId,"version",userInfoModel.getId(),userInfoModel.getId(),true,null,false,true,false);
        return new ResultModel(detailModel);
    }

    @PostMapping("/{id:\\d+}/collab")
    public ResultModel collab(@MultiRequestBody @NotBlank String token, @PathVariable Long questionId, @PathVariable Long id){
        CollabInfoModel collabInfoModel = collabInfoService.findByToken(token);
        if(collabInfoModel == null || collabInfoModel.isDisabled() == true){
            return new ResultModel(0,"错误的Token");
        }
        QuestionAnswerModel answerModel = answerSingleService.findById(id);
        if(answerModel == null || answerModel.isDisabled() || answerModel.isDeleted()){
            return new ResultModel(0,"没有可用的文档");
        }
        UserInfoModel userInfo = userSearchService.findById(collabInfoModel.getCreatedUserId());
        if(userInfo == null || userInfo.isDisabled()){
            return new ResultModel(0,"用户状态不正确");
        }
        Map<String,Object> mapData = new HashMap<>();
        mapData.put("doc",answerModel);
        mapData.put("user",userInfo);
        return new ResultModel(mapData);
    }
}
