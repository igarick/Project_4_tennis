package com.tennis.filter;

import com.tennis.exception.ConnectionException;
import com.tennis.exception.MatchesParamFilterException;
import com.tennis.util.JspHelper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter("/*")
public class ServletFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(ServletFilter.class);
    private static final String MATCHES_JSP = "matches";


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (IOException e) {
            log.warn("Impossible to perform this operation", e);
            throw e;
        } catch (MatchesParamFilterException ex) {
            httpServletResponse.sendRedirect(MATCHES_JSP);
        } catch (ConnectionException e) {
            httpServletRequest.setAttribute("errorCode", e.getErrorInfo().getStatusCode());
            httpServletRequest.setAttribute("errorMessage", e.getErrorInfo().getMessage());
            httpServletRequest.getRequestDispatcher("error.jsp").forward(httpServletRequest, httpServletResponse);
        }

//        catch (AppException e) {
//            log.error("Request failed: {}", e.getErrorInfo().getMessage(), e);
//            try {
//                servletRequest.setAttribute("errorCode", 500);
//                servletRequest.setAttribute("errorMessage", "Ошибка подключения к базе данных");
//                servletRequest.getRequestDispatcher("error.jsp").forward(servletRequest, servletResponse);
//            } catch (ServletException | IOException ex) {
//                log.error("Unexpected error", ex);
//                throw ex;
//            }
//        }
    }
}
