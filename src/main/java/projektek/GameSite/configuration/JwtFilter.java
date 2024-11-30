package projektek.GameSite.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import projektek.GameSite.controllers.CustomResponse;
import projektek.GameSite.services.implementation.user.JwtService;
import projektek.GameSite.services.implementation.user.UserServiceImpl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    ApplicationContext context;

    @Autowired
    public JwtFilter(JwtService jwtService, ApplicationContext context) {
        this.jwtService = jwtService;
        this.context = context;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token;
        String username;

        if (
                request.getRequestURI().equals("/api/login")
                || request.getRequestURI().equals("/api/register")
                || request.getRequestURI().startsWith("/thumbnails/")
                || request.getRequestURI().startsWith("/images/")
                || request.getRequestURI().startsWith("/ws/")
        ) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username = jwtService.extractUserName(token);
            } else {
                unauthorizedResponse (
                        response,
                        "JWT Token is missing"
                );
                return;
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = context.getBean(UserServiceImpl.class).loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            unauthorizedResponse(
                    response,
                    "JWT Token expired"
            );
        }
    }

    private void unauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(401);
        Map<String, String> errors = new HashMap<>();
        errors.put("jwt", message);
        CustomResponse customResponse = new CustomResponse(message, null, errors);

        response.getWriter().write(new ObjectMapper().writeValueAsString(customResponse));
    }
}
