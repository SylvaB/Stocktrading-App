package com.kenzie.app;

public class InvalidUserInputException extends IllegalArgumentException{
        public InvalidUserInputException(String message){
            super(message);
        }
    }
