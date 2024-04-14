package org.example.exceptions;

public class LoginRequiredException extends NotesManagerException{
    public LoginRequiredException(String message){
        super(message);
    }
}
