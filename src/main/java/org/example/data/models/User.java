package org.example.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Notes Users ")
public class User {
    private String id;
    private boolean isLoggedIn;
    private String username;
    private String password;
    private List<Note> notes = new ArrayList<>();
}
