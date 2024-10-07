/**
 * Author: dendy
 * Date:01/10/2024
 * Time:13:23
 * Description: class to delegate AuthenticationException and AccessDeniedException
 *  Since these exceptions are thrown by the authentication filters behind the DispatcherServlet and before invoking
 *  the controller methods, @ControllerAdvice won’t be able to catch these exceptions.
 *  Spring security exceptions can be directly handled by adding custom filters and constructing the response body.
 *  To handle these exceptions at a global level via @ExceptionHandler and @ControllerAdvice, we need a custom
 *  implementation of AuthenticationEntryPoint. AuthenticationEntryPoint is used to send an HTTP response that
 *  requests credentials from a client. Although there are multiple built-in implementations for the security entry
 *  point, we need to write a custom implementation for sending a custom response message.
 *  //https://www.baeldung.com/spring-security-exceptionhandler
 */

package org.ukdw.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("delegateAuthenticationEntryPoint")
@RequiredArgsConstructor
public class DelegateAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(DelegateAuthenticationEntryPoint.class);

    //Here we’ve injected the DefaultHandlerExceptionResolver and delegated the handler to this resolver.
    // This security exception can now be handled with controller advice (GlobalExceptionHandler)
    // with an exception handler method.
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        logger.error(authException.getMessage());
        resolver.resolveException(request, response, null, authException);
    }
}