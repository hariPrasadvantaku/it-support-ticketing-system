package com.itsupport.ticketing.security;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String role = authentication.getAuthorities().iterator().next().getAuthority();

		if (role.equals("ROLE_ADMIN")) {
			response.sendRedirect("/admin/dashboard");
		} else if (role.equals("ROLE_SUPPORT")) {
			response.sendRedirect("/support/dashboard");
		} else {
			response.sendRedirect("/user/dashboard");
		}
	}
}