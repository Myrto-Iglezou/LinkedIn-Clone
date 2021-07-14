package com.linkedin.linkedinclone.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String str) {
        super(str);
    }
}
