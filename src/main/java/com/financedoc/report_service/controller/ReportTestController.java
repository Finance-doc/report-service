package com.financedoc.report_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportTestController {

    @GetMapping("/test")
    public String test(
            @RequestHeader(value = "X-User-Id") String userId
    ){
        return "Report Service Test OK :: X-User-Id = "+userId;
    }

}
