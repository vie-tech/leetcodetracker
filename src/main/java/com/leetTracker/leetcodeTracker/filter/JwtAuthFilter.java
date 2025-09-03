package com.leetTracker.leetcodeTracker.filter;

import com.leetTracker.leetcodeTracker.service.CustomUserDetailService;
import com.leetTracker.leetcodeTracker.utilities.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Entered filter");
               String token = null;
               Cookie[] cookies = request.getCookies();

               if (request.getServletPath().startsWith("/api/user/login") ||
                       request.getServletPath().startsWith("/api/user/register")) {
                   filterChain.doFilter(request, response);
                   return;
               }


               if (cookies != null) {
                   System.out.println("Entered Cookie check");
                   for (Cookie cookie : cookies) {
                       if ("custom_access_cookie".equals(cookie.getName())) {
                           token = cookie.getValue();
                           break; // Stop loop once token is found
                       }
                   }
               }

               if (token != null) {
                   System.out.println("Entered Token check");
                   try {
                       String username = jwtUtil.extractUserName(token);
                       if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                           UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
                           if (userDetails != null && jwtUtil.validateToken(token, userDetails)) {
                               var authToken = new UsernamePasswordAuthenticationToken(userDetails, null
                                       , userDetails.getAuthorities());
                               SecurityContextHolder.getContext().setAuthentication(authToken);
                           }
                       }
                   } catch (Exception e) {
                       System.out.println("Error in JWT filter: " + e.getMessage());
                       response.setStatus(HttpStatus.UNAUTHORIZED.value());
                       response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                       String jsonResponse = String.format(
                               "{\"error\": \"auth_error\", \"message\": \"Authentication failed: %s\", " +
                                       "\"status\": %d}",
                               e.getMessage(), HttpStatus.UNAUTHORIZED.value()
                       );
                       response.getWriter().write(jsonResponse);
                       return; // Stop filter chain
                   }
               }

               filterChain.doFilter(request, response);
    }
}
