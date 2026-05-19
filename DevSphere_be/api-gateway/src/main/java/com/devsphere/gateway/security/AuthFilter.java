package com.devsphere.gateway.security;

import com.devsphere.gateway.util.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter (ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if(path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")|| authHeader.length() <= 7) {
            return ResponseUtil.error(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "MISSING_TOKEN",
                    "Authorization token is required"
            );
        }

        try {
            String token = authHeader.substring(7).trim();
            String userId = jwtUtil.extractUserId(token);

            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(exchange.getRequest().mutate().header("X-User-Id", userId).build())
                    .build();

            return chain.filter(modifiedExchange);
        }  catch (ExpiredJwtException e) {

            log.warn("JWT expired");

            return ResponseUtil.error(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "TOKEN_EXPIRED",
                    "JWT token has expired"
            );

        } catch (SignatureException e) {

            log.warn("Invalid JWT signature");

            return ResponseUtil.error(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "INVALID_SIGNATURE",
                    "JWT signature is invalid"
            );

        } catch (MalformedJwtException e) {

            log.warn("Malformed JWT");

            return ResponseUtil.error(
                    exchange,
                    HttpStatus.BAD_REQUEST,
                    "MALFORMED_TOKEN",
                    "JWT token is malformed"
            );

        } catch (Exception e) {

            log.error("Authentication failed", e);

            return ResponseUtil.error(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "AUTH_FAILED",
                    "Authentication failed"
            );
        }
    }
    @Override
    public int getOrder() {
        return -1;
    }
}
