package com.mateusjose98.management.commons.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mateusjose98.management.api.HttpResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String[] PUBLIC_ROUTES = { "/user/new/password", "/user/login", "/user/verify/code", "/user/register", "/user/refresh/token", "/user/image" };
    private static final String HTTP_OPTIONS_METHOD = "OPTIONS";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filter) throws ServletException, IOException {
        try {
            String token = getToken(request);
            Long userId = getUserId(request);
            if(tokenProvider.isTokenValid(userId, token)) {
                List<GrantedAuthority> authorities = tokenProvider.getAuthorities(token);
                Authentication authentication = tokenProvider.getAuthentication(userId, authorities, request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else { SecurityContextHolder.clearContext(); }
            filter.doFilter(request, response);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            writeResponse(response, getHttpResponse("Erro na validação do token", HttpStatus.UNAUTHORIZED));
        }

    }

    private static HttpResponse getHttpResponse(String message, HttpStatus httpStatus) {
        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp(now().toString())
                .reason(message)
                .status(httpStatus)
                .statusCode(httpStatus.value())
                .build();
        return httpResponse;
    }

    private static void writeResponse(HttpServletResponse response, HttpResponse httpResponse) {
        OutputStream out;
        try{
            out = response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out, httpResponse);
            out.flush();
        }catch (Exception exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getHeader(AUTHORIZATION) == null || !request.getHeader(AUTHORIZATION).startsWith(TOKEN_PREFIX) ||
                request.getMethod().equalsIgnoreCase(HTTP_OPTIONS_METHOD) || asList(PUBLIC_ROUTES).contains(request.getRequestURI());
    }

    private Long getUserId(HttpServletRequest request) {
        return tokenProvider.getSubject(getToken(request), request);
    }

    private String getToken(HttpServletRequest request) {
        return ofNullable(request.getHeader(AUTHORIZATION))
                .filter(header -> header.startsWith(TOKEN_PREFIX))
                .map(token -> token.replace(TOKEN_PREFIX, EMPTY)).get();
    }
}



















