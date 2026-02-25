package com.itsupport.ticketing.controller;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController {

   
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

  
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int code = Integer.parseInt(status.toString());
            model.addAttribute("code", code);
        }

        return "error"; 
    }
}
