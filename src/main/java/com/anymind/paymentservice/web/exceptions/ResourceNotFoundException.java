package com.anymind.paymentservice.web.exceptions;

/**
 * Created by Wilson
 * On 26-12-2022, 11:03
 */
public class ResourceNotFoundException extends RuntimeException{
    private String message;
    public ResourceNotFoundException(String message){
        super(message);
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
