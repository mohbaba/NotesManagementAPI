package org.example.services;

import org.example.data.models.Note;
import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dtos.requests.*;
import org.example.dtos.responses.*;
import org.example.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.utilities.Mapper.*;

@Service
public class UserServicesImpl implements UserServices{
    @Autowired
    UserRepository userRepository;
    @Autowired
    NoteServices noteServices;
    @Override
    public RegisterUserResponse register(RegisterUserRequest registerUserRequest) {
        checkNullFields(registerUserRequest);
        validateUsername(registerUserRequest.getUsername());
        User newUser = map(registerUserRequest);
        return map(userRepository.save(newUser));
    }

    private void validateUsername(String username){
        check(username);
        if (userRepository.existsByUsername(username))throw new UsernameExistsException(String.format("%s already exists",username));
    }

    private static void check(String username){
        boolean isInvalid = username.isEmpty() || username.isBlank();
        boolean containSpace = username.contains(" ");
        if (isInvalid)throw new IncorrectUsernameFormatException("Enter valid username");
        if (containSpace)throw new IncorrectUsernameFormatException("Remove spaces");
    }

    private static void checkNullFields(RegisterUserRequest registerUserRequest){
        if (registerUserRequest.getUsername() == null)throw new FieldRequiredException("Username " +
                "required");
        if (registerUserRequest.getPassword() == null)throw new FieldRequiredException("Password " +
                "required");
    }



    @Override
    public long countUsers() {
        return userRepository.count();
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        validateLogin(loginRequest, user);
        user.setLoggedIn(true);
        return mapLoginResponseTo(userRepository.save(user));
    }

    @Override
    public LogoutUserResponse logout(LogoutUserRequest logoutUserRequest){
        checkUserLoggedIn(logoutUserRequest.getUsername());
        User user = userRepository.findByUsername(logoutUserRequest.getUsername());
        checkUserExists(user);
        user.setLoggedIn(false);
        return mapLogoutResponseTo(userRepository.save(user));
    }



    private void checkUserLoggedIn(String username){
        if (!isLoggedIn(username))throw new LoginRequiredException("User must be logged in");
    }


    private void validateLogin(LoginRequest loginRequest, User user) {
        check(loginRequest.getUsername());
        if (user == null)throw new UserNotFoundException(String.format("%s does not exist", loginRequest.getUsername()));
        if (!loginRequest.getPassword().equals(user.getPassword()))throw new IncorrectPasswordException(
                "Password " +
                        "is not correct");
    }

    @Override
    public boolean isLoggedIn(String username) {
        User user = userRepository.findByUsername(username);
        checkUserExists(user);
        return user.isLoggedIn();
    }

    @Override
    public CreateNoteResponse writeNote(CreateNoteRequest createNoteRequest) {
        if (createNoteRequest.getUsername() == null)throw new FieldRequiredException("Username " +
                "required");
        checkUserLoggedIn(createNoteRequest.getUsername());
        User user = userRepository.findByUsername(createNoteRequest.getUsername());
        Note savedNote = noteServices.createNote(createNoteRequest);
        addNoteToUser(user, savedNote);
        return map(savedNote);
    }



    private void addNoteToUser(User user, Note savedNote) {
        List<Note> userNotes = user.getNotes();
        userNotes.add(savedNote);
        user.setNotes(userNotes);
        userRepository.save(user);
    }

    @Override
    public int countNumberOfNotesFor(String username) {
        checkUserLoggedIn(username);
        User user = userRepository.findByUsername(username);
        checkUserExists(user);
        return user.getNotes().size();
    }

    @Override
    public CreateNoteResponse editNote(EditNoteRequest editNoteRequest) {
        if (editNoteRequest.getUsername() == null)throw new FieldRequiredException("Username " +
                "required");
        checkUserLoggedIn(editNoteRequest.getUsername());
        User user = userRepository.findByUsername(editNoteRequest.getUsername());
        Note editedNote = noteServices.editNote(editNoteRequest);
        replaceNote(user, editedNote);
        return map(editedNote);
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        if (deleteNoteRequest.getUsername() == null)throw new FieldRequiredException("Username " +
                "required");
        checkUserLoggedIn(deleteNoteRequest.getUsername());
        User user = userRepository.findByUsername(deleteNoteRequest.getUsername());
        checkUserExists(user);
        Note deletedNote = noteServices.deleteNote(deleteNoteRequest);
        return deleteNoteFromUser(user, deletedNote);
    }

    @Override
    public Note findNote(ViewNoteRequest viewNoteRequest) {
        if (viewNoteRequest.getNoteId() == null)throw new FieldRequiredException("Note id " +
                "required");
        if(viewNoteRequest.getUsername() == null) throw new FieldRequiredException("Username " +
                "required");
        checkUserLoggedIn(viewNoteRequest.getUsername());
        return noteServices.findNote(viewNoteRequest);
    }

    @Override
    public ViewNoteResponse viewNote(ViewNoteRequest viewNoteRequest) {
        Note note = findNote(viewNoteRequest);
        ViewNoteResponse response = new ViewNoteResponse();
        response.setNoteId(note.getId());
        response.setTitle(note.getTitle());
        response.setContent(note.getBody());
        return response;
    }

    @Override
    public List<Note> findMatchingNotes(ViewNoteRequest viewNoteRequest) {
        if (viewNoteRequest.getUsername() == null) {
            throw new FieldRequiredException("Username is required");
        }
        checkUserLoggedIn(viewNoteRequest.getUsername());
        User user = userRepository.findByUsername(viewNoteRequest.getUsername());
        checkUserExists(user);
        List<Note> matchingNotes = new ArrayList<>();
        for (Note note : user.getNotes()) {
            if (note.getTitle().contains(viewNoteRequest.getTitle()))matchingNotes.add(note);
        }
        return matchingNotes;
    }

    @Override
    public FindNotesResponse findNotes(ViewNoteRequest viewNoteRequest) {
        List<Note> matchingNotes = findMatchingNotes(viewNoteRequest);
        List<NoteResponse> mappedNotes = new ArrayList<>();

        for (Note note : matchingNotes) {
            mappedNotes.add(mapToNoteResponse(note));
        }

        return new FindNotesResponse(mappedNotes);
    }
    private NoteResponse mapToNoteResponse(Note note) {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setId(note.getId());
        noteResponse.setTitle(note.getTitle());
        noteResponse.setBody(note.getBody());
        noteResponse.setDateCreated(note.getCreationDate().toString());
        return noteResponse;
    }

    private DeleteNoteResponse deleteNoteFromUser(User user, Note deletedNote) {
        user.getNotes().remove(deletedNote);
        userRepository.save(user);
        DeleteNoteResponse response = new DeleteNoteResponse();
        response.setDeleted(true);
        return response;
    }

    private void replaceNote(User user, Note editedNote) {
        List<Note> userNotes = user.getNotes();
        userNotes.removeIf(eachNote -> eachNote.getId().equals(editedNote.getId()));
        userNotes.add(editedNote);
        user.setNotes(userNotes);
        userRepository.save(user);
    }

    private void checkUserExists(User user){
        if (user == null)throw new UserNotFoundException("User does not exist");
    }
}
