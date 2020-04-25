package com.itellyou.api.controller;

import com.itellyou.api.handler.response.Result;
import com.itellyou.model.upload.UploadFileConfigModel;
import com.itellyou.model.upload.UploadFileModel;
import com.itellyou.service.ali.OfficePreviewService;
import com.itellyou.service.upload.UploadFileConfigService;
import com.itellyou.service.upload.UploadFileService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@Validated
@RestController
public class PreviewController {

    private final OfficePreviewService officePreviewService;
    private final UploadFileService fileService;
    private final UploadFileConfigService fileConfigService;

    public PreviewController(OfficePreviewService officePreviewService, UploadFileService fileService, UploadFileConfigService fileConfigService) {
        this.officePreviewService = officePreviewService;
        this.fileService = fileService;
        this.fileConfigService = fileConfigService;
    }

    @GetMapping("/preview")
    public Result preview(@RequestParam @NotNull String key){
        try {
            UploadFileModel fileModel = fileService.findByKey(key);
            UploadFileConfigModel fileConfigModel = fileConfigService.findConfig(fileModel.getExtname(),null,null,null,null);
            return new Result(fileModel).extend("preview",fileConfigModel.isDoc() ? officePreviewService.GetPreviewURL(key) : null).extend("config",fileConfigModel);
        }catch (Exception e){
            return new Result(0,e.getLocalizedMessage());
        }
    }
}
