package org.ukdw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Creator: dendy
 * Date: 7/11/2020
 * Time: 12:25 PM
 * description : authentication related exception. all authentication related anomaly should throw this error.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class AuthenticationExceptionImpl extends AuthenticationException {

    public AuthenticationExceptionImpl(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AuthenticationExceptionImpl(String msg) {
        super(msg);
    }
}
