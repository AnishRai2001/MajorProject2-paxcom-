package com.example.demo.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    // ⚠️ WARNING: In a real application, fetch this from a secure configuration source.
    private static final String SECRET_KEY = "YourSecretKeyHere"; 

    // Use startsWith for endpoints to cover all methods (GET, POST, etc.)
    private static final List<String> publicEndpoints = List.of(
            "/api/users/login",
            "/api/users/register",
            "/api/users/refresh-token"
    );

    // Map: Base Path -> List of Allowed Roles
    private static final Map<String, List<String>> roleAccessMap = Map.of(
            "/api/doctors", List.of("ROLE_ADMIN", "ROLE_DOCTOR"),
            "/api/appointments", List.of("ROLE_PATIENT", "ROLE_ADMIN")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // 1. PUBLIC ENDPOINTS CHECK: Use startsWith to cover all methods for public APIs
        if (publicEndpoints.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        // 2. AUTH HEADER CHECK (401 UNAUTHORIZED)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                    .build()
                    .verify(token);
            
            // Extract claims, ensuring 'id' is present (required for downstream services)
            String role = jwt.getClaim("role").asString();
            String email = jwt.getSubject();
            Long userId = jwt.getClaim("id").asLong(); // user id - assuming 'id' claim is present

            // 3. ROLE AUTHORIZATION CHECK (403 FORBIDDEN)
            if (!isAuthorized(path, role)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            // 4. Inject Headers for Downstream Services
            exchange = exchange.mutate()
                    .request(r -> r
                            .header("X-User-Role", role)
                            .header("X-User-Email", email)
                            // Convert Long to String for header value
                            .header("X-User-Id", String.valueOf(userId)) 
                    )
                    .build();

        } catch (Exception e) {
            // Token is invalid, expired, or claims are missing (e.g., id or role)
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // 5. Continue to next filter/service
        return chain.filter(exchange);
    }

    /**
     * Checks if the given role is authorized to access the path.
     * Uses startsWith for path matching (e.g., /api/appointments/123).
     */
    private boolean isAuthorized(String path, String role) {
        for (var entry : roleAccessMap.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return entry.getValue().contains(role);
            }
        }
        // ⚠️ SECURE-BY-DEFAULT: If a path requires a JWT (passed public check) 
        // but isn't explicitly mapped in roleAccessMap, deny access (Return false).
        // This prevents access to unmapped, protected endpoints.
        return false;
    }

    @Override
    public int getOrder() {
        return -1; // Ensures this filter runs early
    }
}