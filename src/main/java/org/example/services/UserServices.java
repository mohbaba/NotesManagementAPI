package org.example.services;

import org.example.data.models.Note;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.w3c.dom.html.HTMLDocument;

public interface UserServices {
    RegisterUserResponse register(RegisterUserRequest registerUserRequest);

    long countUsers();

    LoginResponse login(LoginRequest loginRequest);

    LogoutUserResponse logout(LogoutUserRequest logoutUserRequest);

    boolean isLoggedIn(String username);

    CreateNoteResponse writeNote(CreateNoteRequest createNoteRequest);

    int countNumberOfNotesFor(String username);

    CreateNoteResponse editNote(EditNoteRequest editNoteRequest);

    DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest);

    Note findNote(ViewNoteRequest viewNoteRequest);
    ViewNoteResponse viewNote(ViewNoteRequest viewNoteRequest);
}
