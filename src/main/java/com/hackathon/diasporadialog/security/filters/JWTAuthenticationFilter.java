package com.hackathon.diasporadialog.security.filters;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.hackathon.diasporadialog.exceptions.JWTInvalidException;
import com.hackathon.diasporadialog.security.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    private final List<RequestMatcher> publicEndpoints = Arrays.asList(
            new AntPathRequestMatcher("/h2-console/**"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/authentication/**"),
            new AntPathRequestMatcher("/error")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var authHeader = request.getHeader("Authorization");

        if (this.publicEndpoints.stream().anyMatch(matcher -> matcher.matches(request))) {
            System.out.println("Public URL");
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || (authHeader.contains("Bearer") && authHeader.length() < 7)
                || authHeader.isBlank()
                || !authHeader.contains("Bearer")) {
            throw new JWTInvalidException("Invalid JWT Token in Authorization header! It is blank.");
        }


        var jwt = authHeader.substring(7);

        try {
            var username = jwtUtil.validateTokenAndRetrieveClaim(jwt);

            var userDetails = userDetailsService.loadUserByUsername(username);

            var authToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (JWTVerificationException ex) {
            throw new JWTInvalidException("Invalid JWT Token! Your token has not passed validation.", ex);
        }


        filterChain.doFilter(request, response);

    }
}
