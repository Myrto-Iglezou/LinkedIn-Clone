package com.linkedin.linkedinclone.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.linkedinclone.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import static com.linkedin.linkedinclone.security.SecurityConstants.HEADER_STRING;
import static com.linkedin.linkedinclone.security.SecurityConstants.TOKEN_PREFIX;
import static com.linkedin.linkedinclone.security.SecurityConstants.SECRET;
import static com.linkedin.linkedinclone.security.SecurityConstants.EXPIRATION_TIME;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserRepository repository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            com.linkedin.linkedinclone.model.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), com.linkedin.linkedinclone.model.User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {


        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        PrintWriter out = res.getWriter();
        com.linkedin.linkedinclone.model.User user = repository.findUserByUsername(((User) auth.getPrincipal()).getUsername());
        if(user == null)
            throw new RuntimeException("User "+((User) auth.getPrincipal()).getUsername()+" in successfulAuthentication not found");
        user.setPassword(null);
        String userJsonString = new ObjectMapper().writeValueAsString(user);
        out.print(userJsonString);
        out.flush();
    }
}
