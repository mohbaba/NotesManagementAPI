package org.example.dtos.requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EditNoteRequest {
    private String noteId;
    private String username;
    private String title;
    private String body;
    private LocalDateTime editedDate = LocalDateTime.now();
}
