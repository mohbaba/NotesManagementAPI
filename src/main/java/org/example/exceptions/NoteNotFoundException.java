package org.example.exceptions;

public class NoteNotFoundException extends NotesManagerException{
    public NoteNotFoundException(String message){
        super(message);
    }
}
