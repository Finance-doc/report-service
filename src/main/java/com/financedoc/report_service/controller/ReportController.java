package com.financedoc.report_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @GetMapping("/test")
    public String test(@RequestHeader("X-User-Email") String email) {
        return "Report Service Test OK & X-User-Email = "+ email;
    }
}
