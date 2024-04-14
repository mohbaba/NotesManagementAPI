package org.example.services;

import org.example.data.models.Note;
import org.example.dtos.requests.CreateNoteRequest;
import org.example.dtos.requests.DeleteNoteRequest;
import org.example.dtos.requests.EditNoteRequest;
import org.example.dtos.requests.ViewNoteRequest;

public interface NoteServices {
    Note createNote(CreateNoteRequest createNoteRequest);

    Note editNote(EditNoteRequest editNoteRequest);

    Note deleteNote(DeleteNoteRequest deleteNoteRequest);

    Note findNote(ViewNoteRequest viewNoteRequest);
}
