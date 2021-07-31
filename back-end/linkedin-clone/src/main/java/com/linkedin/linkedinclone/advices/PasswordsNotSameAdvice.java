package com.linkedin.linkedinclone.advices;

import com.linkedin.linkedinclone.exceptions.EmailNotFoundException;
import com.linkedin.linkedinclone.exceptions.PasswordsNotSameException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PasswordsNotSameAdvice {

    @ResponseBody
    @ExceptionHandler(PasswordsNotSameException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String differentPasswordsHandler(PasswordsNotSameException ex) {
        return ex.getMessage();
    }
}
