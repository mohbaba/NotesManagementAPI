package org.example.data.repositories;

import org.example.data.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotesRepository extends MongoRepository<Note, String> {
    Note deleteNoteById(String id);
}
