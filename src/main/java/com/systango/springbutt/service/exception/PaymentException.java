package com.systango.springbutt.service.exception;

/**
 * Created by Arpit Khandelwal.
 */
public class PaymentException extends Exception {
    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(Throwable throwable) {
        super(throwable);
    }
}
