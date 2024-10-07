package org.ukdw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Project: SRM-BE
 * Package: com.srmbe.exception.general
 * <p>
 * Creator: dendy
 * Date: 7/1/2020
 * Time: 7:47 AM
 * <p>
 * Description : bad request exception
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
