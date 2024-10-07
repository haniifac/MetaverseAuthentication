package org.ukdw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Creator: dendy
 * Date: 7/11/2020
 * Time: 12:25 PM
 * Description : InvalidTokenException
 */
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
