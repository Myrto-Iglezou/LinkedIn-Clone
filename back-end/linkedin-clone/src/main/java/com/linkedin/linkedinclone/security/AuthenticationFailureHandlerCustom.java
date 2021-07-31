package com.linkedin.linkedinclone.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class AuthenticationFailureHandlerCustom implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setContentType("application/json");
        Date date = new Date();
        httpServletResponse.getWriter().append("{\"timestamp\": " + "\"" + date.toString()+ "\","
                + "\"status\": 401, "
                + "\"error\": \"Unauthorized\", "
                + "\"message\": " + "\"" + e.getMessage() + "\","
                + "\"path\": \"/login\"}");
    }
}
