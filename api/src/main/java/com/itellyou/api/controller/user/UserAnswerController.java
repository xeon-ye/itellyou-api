package com.itellyou.api.controller.user;

import com.itellyou.api.handler.response.Result;
import com.itellyou.model.sys.PageModel;
import com.itellyou.model.question.QuestionAnswerDetailModel;
import com.itellyou.model.user.UserInfoModel;
import com.itellyou.service.question.QuestionAnswerSearchService;
import com.itellyou.util.serialize.filter.Labels;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user/answer")
public class UserAnswerController {

    private final QuestionAnswerSearchService answerSearchService;

    public UserAnswerController(QuestionAnswerSearchService answerSearchService){
        this.answerSearchService = answerSearchService;
    }

    @GetMapping("")
    public Result list(UserInfoModel userModel, @RequestParam(required = false) Integer offset, @RequestParam(required = false) Integer limit){
        if(userModel == null) return new Result(401,"未登陆");
        Map<String,String> order = new HashMap<>();
        order.put("created_time","desc");
        PageModel<QuestionAnswerDetailModel> pageData = answerSearchService.page(null,userModel.getId(),userModel.getId(),true,null,false,null,false,null,null,order,offset,limit);
        return new Result(pageData,new Labels.LabelModel(QuestionAnswerDetailModel.class,"base","question"));
    }
}
