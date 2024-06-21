package com.WalkiePaw.presentation.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class AuthCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
