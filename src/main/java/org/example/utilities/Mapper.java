package org.example.utilities;

import org.example.data.models.Note;
import org.example.data.models.User;
import org.example.dtos.requests.RegisterUserRequest;
import org.example.dtos.responses.CreateNoteResponse;
import org.example.dtos.responses.LoginResponse;
import org.example.dtos.responses.LogoutUserResponse;
import org.example.dtos.responses.RegisterUserResponse;

public class Mapper {
    public static RegisterUserResponse map(User savedUser) {
        RegisterUserResponse response = new RegisterUserResponse();
        response.setUsername(savedUser.getUsername());
        response.setPassword(savedUser.getPassword());
        response.setUserId(savedUser.getId());
        return response;
    }

    public static User map(RegisterUserRequest registerUserRequest) {
        User newUser = new User();
        newUser.setUsername(registerUserRequest.getUsername());
        newUser.setPassword(registerUserRequest.getPassword());
        return newUser;
    }

    public static LoginResponse mapLoginResponseTo(User user) {
        LoginResponse response = new LoginResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        return response;
    }

    public static LogoutUserResponse mapLogoutResponseTo(User user) {
        LogoutUserResponse response = new LogoutUserResponse();
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        return response;
    }

    public static CreateNoteResponse map(Note savedNote) {
        CreateNoteResponse response = new CreateNoteResponse();
        response.setNoteId(savedNote.getId());
        response.setTitle(savedNote.getTitle());
        response.setBody(savedNote.getBody());
        response.setDateCreated(savedNote.getCreationDate().toString());
        return response;
    }
}
