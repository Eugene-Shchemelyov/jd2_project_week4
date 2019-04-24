package com.gmail.eugene.shchemelyov.itemshop.service.exception;

public class ServiceException extends RuntimeException {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
