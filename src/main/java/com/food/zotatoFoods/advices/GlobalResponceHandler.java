package com.food.zotatoFoods.advices;


import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class GlobalResponceHandler implements ResponseBodyAdvice<Object> {

    @SuppressWarnings("null")
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SuppressWarnings("null")
    @Override
    @Nullable
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
            ServerHttpResponse response) {

        List<String> allowedsRouts = List.of("/v3/api-docs", "/actuator");

        boolean IsallowedsRouts = allowedsRouts.stream()
                .anyMatch(route -> request.getURI().getPath().contains(route));

        if (body instanceof ApiResponce<?> || IsallowedsRouts)
            return body;

        return new ApiResponce<>(body);
    }

}

