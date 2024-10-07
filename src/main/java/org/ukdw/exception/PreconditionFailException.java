package org.ukdw.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Project: SRM-BE
 * Package: com.srmbe.exception.common
 * <p>
 * Creator: dendy
 * Date: 7/1/2020
 * Time: 8:58 AM
 * <p>
 * Description : PreconditionFailException
 */

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class PreconditionFailException extends RuntimeException {
    public PreconditionFailException(String message) {
        super(message);
    }
}