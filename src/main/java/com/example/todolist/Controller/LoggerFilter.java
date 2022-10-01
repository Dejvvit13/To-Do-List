package com.example.todolist.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(filterName = "LoggerFilter")
@Component
public class LoggerFilter implements Filter {

    private final static Logger logger = LoggerFactory.getLogger(LoggerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (request instanceof HttpServletRequest httpRequest) {
            logger.info("[doFilter] " + httpRequest.getMethod() + " " + httpRequest.getRequestURI());
        }
        chain.doFilter(request, response);
    }

}
