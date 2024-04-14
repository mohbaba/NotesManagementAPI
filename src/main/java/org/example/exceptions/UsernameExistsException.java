package org.example.exceptions;

public class UsernameExistsException extends NotesManagerException{
    public UsernameExistsException(String message){
        super(message);
    }
}
