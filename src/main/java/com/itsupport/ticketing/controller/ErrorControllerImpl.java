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

	 @RequestMapping("/error")
	    public String handleError(HttpServletRequest request) {

	        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

	        if (status != null) {

	            int code = Integer.parseInt(status.toString());

	            if (code == 404) {
	                request.setAttribute("errorMessage", "Page not found");
	            } else if (code == 403) {
	                request.setAttribute("errorMessage", "Access denied");
	            } else {
	                request.setAttribute("errorMessage", "Internal server error");
	            }

	            request.setAttribute("status", code);
	        }

	        return "error";
	    }
}
