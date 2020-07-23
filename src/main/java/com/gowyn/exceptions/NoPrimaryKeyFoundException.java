package com.gowyn.exceptions;

public class NoPrimaryKeyFoundException extends Exception{
    public NoPrimaryKeyFoundException(String message){
        super(message);
    }
}
