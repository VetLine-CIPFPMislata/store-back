package org.example.storeback.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Optional;

@Component
@Order(0)
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

        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200"); // o el dominio de tu frontend
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if (request.getRequestURI().equals("/api/auth/login") ||
            request.getRequestURI().equals("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("OPTIONS request - skipping auth filter");
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

            Optional<ClientDto> userOptional;
            if (requiresRole.value() == Role.ADMIN) {
                userOptional = authService.getUserFromToken(token);
            } else {
                userOptional = authService.getAnyUserFromToken(token);
            }

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

            if (user.role() == Role.USER) {
                String requestURI = request.getRequestURI();

                Long userIdFromUrl = extractUserIdFromUrl(requestURI);

                if (userIdFromUrl != null && !userIdFromUrl.equals(user.id())) {
                    sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                            "No tienes permiso para acceder a recursos de otro usuario");
                    return;
                }
            }

            request.setAttribute("authenticatedUser", user);

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


    private Long extractUserIdFromUrl(String requestURI) {
        try {
            if (requestURI.contains("/user/")) {
                String[] parts = requestURI.split("/user/");
                if (parts.length > 1) {
                    String userIdPart = parts[1].split("/")[0].split("\\?")[0];
                    return Long.parseLong(userIdPart);
                }
            }


            if (requestURI.matches(".*/carts/\\d+/items.*")) {
                String[] parts = requestURI.split("/");
                for (int i = 0; i < parts.length - 1; i++) {
                    if (parts[i].equals("carts") && parts[i + 1].matches("\\d+")) {
                        return Long.parseLong(parts[i + 1]);
                    }
                }
            }

            if (requestURI.matches(".*/orders/\\d+/checkout.*")) {
                String[] parts = requestURI.split("/");
                for (int i = 0; i < parts.length - 1; i++) {
                    if (parts[i].equals("orders") && parts[i + 1].matches("\\d+")) {
                        return Long.parseLong(parts[i + 1]);
                    }
                }
            }
        } catch (NumberFormatException e) {
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
