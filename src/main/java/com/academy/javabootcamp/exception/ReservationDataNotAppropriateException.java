package com.academy.javabootcamp.exception;

public class ReservationDataNotAppropriateException extends RuntimeException{
    public ReservationDataNotAppropriateException(String message){
        super(message);
    }
}
