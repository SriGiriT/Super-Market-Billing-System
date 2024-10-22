package com.billingsystem.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.billingsystem.Model.User;
import com.billingsystem.service.UserService;
import com.billingsystem.utility.JWTUtil;
import com.billingsystem.utility.LoggerUtil;

public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        UserService userService = new UserService();
        Cookie[] cookies = httpRequest.getCookies();
        String token = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("auth_token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null) {
            DecodedJWT decodedJWT = JWTUtil.validateToken(token);
            if (decodedJWT != null) {
                String phoneNumber = JWTUtil.getPhoneNumber(decodedJWT);
                String role = JWTUtil.getRole(decodedJWT);
                User user = (User)(session != null ? session.getAttribute("user") : null);
                if(user == null) {
                	user = userService.checkUser(phoneNumber);
                	session = httpRequest.getSession(true);
                	session.setAttribute("user", user);
                }
                if(user==null) {
                	LoggerUtil.getInstance().getLogger().error("Invalid auth-token in filters");
                }
                if (role != null && role.equals("ADMIN") && httpRequest.getRequestURI().contains("/admin")) {
                    chain.doFilter(request, response);
                    return;
                }

                chain.doFilter(request, response);
                return;
            }
        }

        if (!httpRequest.getRequestURI().contains("/login")) {
            httpResponse.sendRedirect("login.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
