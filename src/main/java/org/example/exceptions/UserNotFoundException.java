package org.example.exceptions;

public class UserNotFoundException extends NotesManagerException{
    public UserNotFoundException(String message){
        super(message);
    }
}
