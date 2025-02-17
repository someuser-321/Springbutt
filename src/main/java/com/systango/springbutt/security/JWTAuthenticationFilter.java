package com.systango.springbutt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systango.springbutt.domain.model.user.ApplicationUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.systango.springbutt.security.SecurityConstants.*;


/**
 * Created by Arpit Khandelwal.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            ApplicationUser user;
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            // A small hack to test if the request is sent from web form or api json post
            if (username != null && password != null) {
                // This indicates request was sent from the web view
                user = new ApplicationUser().setUsername(username).setPassword(password);
            } else {
                // This indicates request was sent as JSON data
                user = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
            }

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
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
        if (auth.getPrincipal() != null) {
            // The Auth Mechanism stores the Username the Principal.
            // The username is stored in the Subject field of the Token
            User user = (User) auth.getPrincipal();
            String login = user.getUsername();
            if (login != null && login.length() > 0) {
                Claims claims = Jwts.claims().setSubject(login);
                List<String> roles = new ArrayList<>();
                user.getAuthorities().stream().forEach(authority -> roles.add(authority.getAuthority()));
                claims.put("roles", roles);
                String token = Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                        .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                        .compact();
                res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
            }
        }
    }
}