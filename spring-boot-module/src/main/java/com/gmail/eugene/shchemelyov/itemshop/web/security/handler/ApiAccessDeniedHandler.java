package com.gmail.eugene.shchemelyov.itemshop.web.security.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.gmail.eugene.shchemelyov.itemshop.repository.constant.ExceptionMessageConstant.TRY_TO_ACCESS;

public class ApiAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse,
                       AccessDeniedException e)
            throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.info("{} {} {}", authentication.getName(), TRY_TO_ACCESS, httpServletRequest.getRequestURI());
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/403");
        }
    }
}
