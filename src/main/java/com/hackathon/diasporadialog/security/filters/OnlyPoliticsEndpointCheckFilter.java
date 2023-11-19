package com.hackathon.diasporadialog.security.filters;


import com.hackathon.diasporadialog.domain.enums.Role;
import com.hackathon.diasporadialog.exceptions.UserNotGrantedToDoActionException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class OnlyPoliticsEndpointCheckFilter extends OncePerRequestFilter {

    private final List<Pattern> officialEndpoints = List.of(
            Pattern.compile("^.*/api/v1/meetings/zoom$")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        var endpoint = request.getRequestURL().toString();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && matchesDynamicPattern(endpoint)) {
            var userDetails = (UserDetails) authentication.getPrincipal();
            var role = "";
            for (GrantedAuthority authority : userDetails.getAuthorities()) {
                role = authority.toString();
            }

            if (!role.equals(Role.OFFICIAL.toString()))
                throw new UserNotGrantedToDoActionException("User doesn't have authorities to do this action !");

        }
        filterChain.doFilter(request, response);
    }

    private boolean matchesDynamicPattern(String endpoint) {
        for (Pattern pattern : officialEndpoints) {
            if (pattern.matcher(endpoint).matches()) {
                return true;
            }
        }
        return false;
    }
}
