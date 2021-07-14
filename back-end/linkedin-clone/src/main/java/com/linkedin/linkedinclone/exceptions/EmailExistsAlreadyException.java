package com.linkedin.linkedinclone.exceptions;

public class EmailExistsAlreadyException extends RuntimeException {
    public EmailExistsAlreadyException(String email) {
        super("Email "+ email +" exists already");
    }
}
