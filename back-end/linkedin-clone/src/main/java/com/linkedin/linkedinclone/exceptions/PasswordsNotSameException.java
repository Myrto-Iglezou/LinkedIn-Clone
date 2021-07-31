package com.linkedin.linkedinclone.exceptions;

public class PasswordsNotSameException extends RuntimeException {
    public PasswordsNotSameException() {
        super("Passwords are different");
    }
}
