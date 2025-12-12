package org.example.storeback.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.storeback.domain.models.Role;
import org.example.storeback.domain.service.AuthService;
import org.example.storeback.domain.service.dto.ClientDto;
import org.example.storeback.domain.validation.RequiresAuth;
import org.example.storeback.domain.validation.RequiresRole;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final AuthService authService;

    public AuthInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("Interceptando petición a: " + request.getRequestURI());

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            System.out.println("No es un HandlerMethod, permitiendo...");
            return true;
        }

        RequiresAuth requiresAuth = handlerMethod.getMethodAnnotation(RequiresAuth.class);
        RequiresRole requiresRole = handlerMethod.getMethodAnnotation(RequiresRole.class);

        System.out.println("@RequiresAuth: " + (requiresAuth != null ? "SÍ" : "NO"));
        System.out.println("@RequiresRole: " + (requiresRole != null ? requiresRole.value() : "NO"));

        if (requiresAuth == null && requiresRole == null) {
            System.out.println("No requiere autenticación, permitiendo...");
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + (authHeader != null ? authHeader : "NULL"));

        String token = extractTokenFromHeader(authHeader);

        if (token == null) {
            System.out.println("Token no encontrado, bloqueando petición (401)");
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado");
            return false;
        }

        System.out.println("Token extraído: " + token.substring(0, Math.min(20, token.length())) + "...");

        Optional<ClientDto> userOptional = authService.getUserFromToken(token);

        if (userOptional.isEmpty()) {
            System.out.println("Token inválido, bloqueando petición (401)");
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
            return false;
        }

        ClientDto user = userOptional.get();
        System.out.println("Usuario encontrado: " + user.email() + " - Rol: " + user.role());

        request.setAttribute("currentUser", user);

        if (requiresRole != null) {
            Role requiredRole = requiresRole.value();
            System.out.println("Rol requerido: " + requiredRole + " - Rol del usuario: " + user.role());

            if (user.role() != requiredRole) {
                System.out.println("Rol insuficiente, bloqueando petición (403)");
                sendErrorResponse(response, HttpServletResponse.SC_FORBIDDEN,
                    "No tienes permisos para realizar esta acción. Se requiere rol: " + requiredRole);
                return false;
            }
        }

        System.out.println("Autenticación exitosa, permitiendo petición");
        return true;
    }

    private String extractTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws Exception {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}
