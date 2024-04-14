package org.example.exceptions;

public class IncorrectPasswordException extends NotesManagerException{
    public IncorrectPasswordException(String message){
        super(message);
    }
}
