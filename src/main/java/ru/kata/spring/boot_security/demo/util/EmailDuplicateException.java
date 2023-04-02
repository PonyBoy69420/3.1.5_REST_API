package ru.kata.spring.boot_security.demo.util;

public class EmailDuplicateException extends RuntimeException{
    public EmailDuplicateException(String message){
        super(message);
    }
}
