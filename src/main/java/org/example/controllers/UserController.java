package org.example.controllers;

import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.NotesManagerException;
import org.example.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class UserController {
    @Autowired
    UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUserRequest registerUserRequest){
        try {
            RegisterUserResponse response = userServices.register(registerUserRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        }catch (NotesManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginResponse response = userServices.login(loginRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (NotesManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/add_to_note")
    public ResponseEntity<?> writeToNote(@RequestBody CreateNoteRequest createNoteRequest){
        try {
            CreateNoteResponse response = userServices.writeNote(createNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (NotesManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PatchMapping("/edit_note")
    public ResponseEntity<?> editNote(@RequestBody EditNoteRequest editNoteRequest){
        try {
            CreateNoteResponse response = userServices.editNote(editNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (NotesManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete_note")
    public ResponseEntity<?> deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest){
        try {
            DeleteNoteResponse response = userServices.deleteNote(deleteNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (NotesManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/view_note")
    public ResponseEntity<?> viewNote(@RequestBody ViewNoteRequest viewNoteRequest){
        try {
            ViewNoteResponse response = userServices.viewNote(viewNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (NotesManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/find_notes")
    public ResponseEntity<?> findNotes(@RequestBody ViewNoteRequest viewNoteRequest) {
        try {
            FindNotesResponse response = userServices.findNotes(viewNoteRequest);
            return new ResponseEntity<>(new ApiResponse(true, response), CREATED);
        } catch (NotesManagerException error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutUserRequest logoutUserRequest){
        try {
            LogoutUserResponse response = userServices.logout(logoutUserRequest);
            return new ResponseEntity<>(new ApiResponse(true,response),CREATED);
        }catch (NotesManagerException error){
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), BAD_REQUEST);
        }
    }
}
