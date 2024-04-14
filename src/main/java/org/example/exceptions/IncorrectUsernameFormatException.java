package org.example.exceptions;

public class IncorrectUsernameFormatException extends NotesManagerException{
    public IncorrectUsernameFormatException(String message){
        super(message);
    }
}
