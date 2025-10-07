package com.tennis.filter;

import com.tennis.exception.AppException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/*")
public class ServletFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ServletFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException e) {
            log.warn("Impossible to perform this operation", e);
            throw e;
        } catch (AppException e) {
            log.error("Request failed: {}", e.getErrorInfo().getMessage(), e);
            try {
                servletRequest.setAttribute("errorCode", 500);
                servletRequest.setAttribute("errorMessage", "Ошибка подключения к базе данных");
                servletRequest.getRequestDispatcher("error.jsp").forward(servletRequest, servletResponse);
            } catch (ServletException | IOException ex) {
                log.error("Unexpected error", ex);
                throw ex;
            }
        }
    }
}
