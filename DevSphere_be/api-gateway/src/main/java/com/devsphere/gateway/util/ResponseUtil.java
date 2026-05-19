package com.devsphere.gateway.util;

import com.devsphere.gateway.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class ResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Mono<Void> error(ServerWebExchange exchange, HttpStatus status, String message, String error) {
        try {
            exchange.getResponse().setStatusCode(status);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            ErrorResponse response = new ErrorResponse(error, message);

            String body = objectMapper.writeValueAsString(response);

            byte[] bytes = body.getBytes(StandardCharsets.UTF_8);

            return exchange.getResponse().writeWith(
                    Mono.just(
                            exchange.getResponse().bufferFactory().wrap(bytes)
                    )
            );
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }
}
