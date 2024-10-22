package com.billingsystem.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpsEnforcerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Redirect to HTTPS if the request is not secure
        if (!httpRequest.isSecure()) {
            String httpsUrl = "https://" + httpRequest.getServerName() + ":8443" + httpRequest.getRequestURI();
            if (httpRequest.getQueryString() != null) {
                httpsUrl += "?" + httpRequest.getQueryString();
            }
            httpResponse.sendRedirect(httpsUrl);
        } else {
            chain.doFilter(request, response); // Proceed if the request is already secure
        }
    }
}
