package com.linkedin.linkedinclone.advices;

import com.linkedin.linkedinclone.exceptions.EmailExistsAlreadyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class EmailExistsAlreadyAdvice  {

    @ResponseBody
    @ExceptionHandler(EmailExistsAlreadyException.class)
    @ResponseStatus(HttpStatus.IM_USED)
    String emailFoundHandler(EmailExistsAlreadyException ex) {
        return ex.getMessage();
    }
}