package com.gbill.getallfinalconsumerbill.exception;

public class ConnectionFaildAuthenticationException extends RuntimeException{
    public ConnectionFaildAuthenticationException(String message){
        super(message);
    }

    public ConnectionFaildAuthenticationException(String message,Throwable cause){
        super(message,cause);
    }

}
