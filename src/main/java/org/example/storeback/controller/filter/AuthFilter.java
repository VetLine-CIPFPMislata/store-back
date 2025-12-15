package org.example.storeback.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final RequestMappingHandlerMapping handlerMapping;

    public AuthFilter(AuthService authService, RequestMappingHandlerMapping handlerMapping) {
        this.authService = authService;
        this.handlerMapping = handlerMapping;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("/api/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            HandlerExecutionChain handlerChain = handlerMapping.getHandler(request);

            if (handlerChain == null || !(handlerChain.getHandler() instanceof HandlerMethod)) {
                filterChain.doFilter(request, response);
                return;
            }

            HandlerMethod handlerMethod = (HandlerMethod) handlerChain.getHandler();
            RequiresRole requiresRole = handlerMethod.getMethodAnnotation(RequiresRole.class);

            if (requiresRole == null) {
                filterChain.doFilter(request, response);
                return;
            }


            String token = extractTokenFromHeader(request.getHeader("Authorization"));

            if (token == null) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado");
                return;
            }

            Optional<ClientDto> userOptional = authService.getUserFromToken(token);

            if (userOptional.isEmpty()) {
                sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
                return;
            }

            ClientDto user = userOptional.get();

            if (user.role() != requiresRole.value()) {
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                        "Acceso denegado. Se requiere rol: " + requiresRole.value());
                return;
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            sendErrorResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error al procesar la autenticación: " + e.getMessage());
        }
    }

    private String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
