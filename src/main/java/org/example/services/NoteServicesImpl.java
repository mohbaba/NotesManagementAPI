package org.example.services;

import org.example.data.models.Note;
import org.example.data.repositories.NotesRepository;
import org.example.dtos.requests.CreateNoteRequest;
import org.example.dtos.requests.DeleteNoteRequest;
import org.example.dtos.requests.EditNoteRequest;
import org.example.dtos.requests.ViewNoteRequest;
import org.example.exceptions.FieldRequiredException;
import org.example.exceptions.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteServicesImpl implements NoteServices{
    @Autowired
    NotesRepository notesRepository;
    @Override
    public Note createNote(CreateNoteRequest createNoteRequest) {
        if (createNoteRequest.getTitle() == null || createNoteRequest.getContent() == null)throw new FieldRequiredException("Title and content required");
        Note note = new Note();
        note.setTitle(createNoteRequest.getTitle());
        note.setBody(createNoteRequest.getContent());

        return notesRepository.save(note);
    }

    @Override
    public Note editNote(EditNoteRequest editNoteRequest) {
        if (editNoteRequest.getNoteId() == null)throw new FieldRequiredException("Note Id " +
                "required");
        Optional<Note> note = notesRepository.findById(editNoteRequest.getNoteId());
        if (note.isEmpty())throw new NoteNotFoundException(String.format("Note with id: %s does " +
                "does not exist",editNoteRequest.getNoteId()));
        if (editNoteRequest.getTitle() != null)note.get().setTitle(editNoteRequest.getTitle());
        if (editNoteRequest.getBody() != null)note.get().setBody(editNoteRequest.getBody());

        return notesRepository.save(note.get());
    }

    @Override
    public Note deleteNote(DeleteNoteRequest deleteNoteRequest) {
        if (deleteNoteRequest.getNoteId() == null)throw new FieldRequiredException("Id required");
        Note deletedNote = notesRepository.deleteNoteById(deleteNoteRequest.getNoteId());
        if (deletedNote == null)throw new NoteNotFoundException(String.format("Note not found " +
                "with id: %s",deleteNoteRequest.getNoteId()));
        return deletedNote;
    }

    @Override
    public Note findNote(ViewNoteRequest viewNoteRequest) {
        if (viewNoteRequest.getNoteId() == null)throw new FieldRequiredException("Note id " +
                "required");
        Optional<Note> note = notesRepository.findById(viewNoteRequest.getNoteId());
        if (note.isEmpty())throw new NoteNotFoundException(String.format("Note with id: %s does " +
                "not exist",viewNoteRequest.getNoteId()));

        return note.get();
    }
}
