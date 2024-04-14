package org.example.services;

import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dtos.requests.*;
import org.example.exceptions.IncorrectUsernameFormatException;
import org.example.exceptions.UsernameExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServicesTest {
    @Autowired
    UserServices userServices;
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setup(){
        userRepository.deleteAll();
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("feyi");
        registerUserRequest.setPassword("pass");
        userServices.register(registerUserRequest);
        assertEquals(1L,userServices.countUsers());
    }

    private void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
    }

    @Test
    public void userLogsIn_UserIsLoggedInTest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
        assertTrue(userServices.isLoggedIn("feyi"));
    }

    @Test
    public void userLogsInWithCaseSensitiveData_ThrowsExceptionTest(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("Feyi");
        loginRequest.setPassword("pass");
        userServices.login(loginRequest);
        assertTrue(userServices.isLoggedIn("feyi"));
    }

    @Test
    public void userRegistersWithCaseSensitiveDataEqualsAnotherNameInRepo_ThrowsExceptionTest(){
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUsername("Feyi");
        registerUserRequest.setPassword("pass");
        assertThrows(UsernameExistsException.class,()->userServices.register(registerUserRequest));
    }

    @Test
    public void registerUserWithEmptyString_throwsExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class,()->userServices.register(request));
    }

    @Test
    public void registerUserWithSpace_throwsIllegalArgumentExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("  ");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class,()->userServices.register(request));
    }

    @Test
    public void registerUserWithNameSeparatedBySpace_throwsIllegalArgumentExceptionTest() {
        RegisterUserRequest request = new RegisterUserRequest();

        request.setUsername("User Name");
        request.setPassword("pass");
        assertThrows(IncorrectUsernameFormatException.class,()->userServices.register(request));
    }

    @Test
    public void userCreateNote_NoteIsCreatedTest(){
        login();
        CreateNoteRequest request = new CreateNoteRequest();
        request.setUsername("feyi");
        request.setTitle("Title");
        request.setContent("Content");
        userServices.writeNote(request);
        assertEquals(1,userServices.countNumberOfNotesFor("feyi"));

    }

    @Test
    public void userEditNote_NoteIsUpdatedTest(){
        login();
        CreateNoteRequest request = new CreateNoteRequest();
        request.setUsername("feyi");
        request.setTitle("Title");
        request.setContent("Content");
        userServices.writeNote(request);
        assertEquals(1,userServices.countNumberOfNotesFor("feyi"));

        User user = userRepository.findByUsername("feyi");
        String id = user.getNotes().getFirst().getId();

        EditNoteRequest editNoteRequest = new EditNoteRequest();
        editNoteRequest.setNoteId(id);
        editNoteRequest.setUsername("feyi");
        editNoteRequest.setTitle("New Title");
        editNoteRequest.setBody("New Body");
        userServices.editNote(editNoteRequest);
        assertEquals(1,userServices.countNumberOfNotesFor("feyi"));
        assertEquals("New Title",userRepository.findByUsername("feyi").getNotes().getFirst().getTitle());


    }

    @Test
    public void deleteCreatedNote_noteIsDeletedTest(){
        login();
        CreateNoteRequest request = new CreateNoteRequest();
        request.setUsername("feyi");
        request.setTitle("Title");
        request.setContent("Content");
        userServices.writeNote(request);
        assertEquals(1,userServices.countNumberOfNotesFor("feyi"));

        DeleteNoteRequest deleteNoteRequest = new DeleteNoteRequest();
        deleteNoteRequest.setUsername("feyi");
        deleteNoteRequest.setNoteId(userRepository.findByUsername("feyi").getNotes().getFirst().getId());
        userServices.deleteNote(deleteNoteRequest);
        assertEquals(0,userServices.countNumberOfNotesFor("feyi"));

    }

    @Test
    public void findCreatedNote_NoteDetailsCanBeFoundTest(){
        login();
        CreateNoteRequest request = new CreateNoteRequest();
        request.setUsername("feyi");
        request.setTitle("Title");
        request.setContent("Content");
        userServices.writeNote(request);
        assertEquals(1,userServices.countNumberOfNotesFor("feyi"));

        ViewNoteRequest viewNoteRequest = new ViewNoteRequest();
        viewNoteRequest.setNoteId(userRepository.findByUsername("feyi").getNotes().getFirst().getId());
        viewNoteRequest.setUsername("feyi");
        assertEquals("Title",userServices.findNote(viewNoteRequest).getTitle());
        assertEquals("Content",userServices.findNote(viewNoteRequest).getBody());

    }

}