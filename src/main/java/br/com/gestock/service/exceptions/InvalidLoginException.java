package br.com.gestock.service.exceptions;

public class InvalidLoginException extends RuntimeException{
    
    public InvalidLoginException(String message){
        super(message);
    }
}
