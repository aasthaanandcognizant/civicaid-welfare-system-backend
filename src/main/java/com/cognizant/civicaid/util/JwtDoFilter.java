package com.cognizant.civicaid.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtDoFilter extends OncePerRequestFilter {

    private final AuthUtil authUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String tokenFromRequest=request.getHeader("Authorization");

        if(tokenFromRequest == null || !tokenFromRequest.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        String token=tokenFromRequest.split("Bearer")[1];

        String userNameFromToken=authUtil.getUserNameFromToken(token);

        if(userNameFromToken!=null &&
                SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetailsFromUserDetailsService=
                    userDetailsService.loadUserByUsername(userNameFromToken);

            if(authUtil.validateBothTokenAndUserDetails(token,
                    userDetailsFromUserDetailsService)){

                UsernamePasswordAuthenticationToken authenticationObject=new
                        UsernamePasswordAuthenticationToken(userDetailsFromUserDetailsService,
                        null,userDetailsFromUserDetailsService.getAuthorities());

                authenticationObject.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationObject);

            }
        }
        filterChain.doFilter(request,response);
    }
}
