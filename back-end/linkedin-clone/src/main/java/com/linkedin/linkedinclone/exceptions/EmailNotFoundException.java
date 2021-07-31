package com.linkedin.linkedinclone.exceptions;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String email) {
        super("Email "+ email +" doesn't exist");
    }
}
