package com.itsupport.ticketing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class DashboardController {

    @GetMapping("/user/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    @GetMapping("/support/dashboard")
    public String supportDashboard() {
        return "support/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }
}
