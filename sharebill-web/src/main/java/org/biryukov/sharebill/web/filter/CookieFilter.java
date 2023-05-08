package org.biryukov.sharebill.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.biryukov.sharebill.service.jdbcrepo.GlobalSessionJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class CookieFilter extends OncePerRequestFilter {

    private String GLOBAL_ID = "GLOBAL_ID";

    private Set<UUID> globalSessionCash = new HashSet<>();

    @Autowired
    private GlobalSessionJdbcRepository globalSessionJdbcRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<Cookie> cookies = request.getCookies() != null
                ? Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(GLOBAL_ID))
                .toList()
                : Collections.emptyList();

        if (cookies.isEmpty()) {
            UUID globalSessionId = UUID.randomUUID();

            Cookie cookie = new Cookie(GLOBAL_ID, globalSessionId.toString());
            cookie.setHttpOnly(true);
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath("/");

            response.addCookie(cookie);

        } else {
            Cookie cookie = cookies.get(0);
            UUID globalSessionId = UUID.fromString(cookie.getValue());
            if (!globalSessionCash.contains(globalSessionId)) {
                if (globalSessionJdbcRepository.findByGlobalSessionId(globalSessionId).isEmpty()) {
                    globalSessionJdbcRepository.createGlobalSession(globalSessionId);
                }
                globalSessionCash.add(globalSessionId);
            }
        }
        filterChain.doFilter(request, response);
    }
}