package org.example.dtos.responses;

import lombok.Data;

@Data
public class CreateNoteResponse {
    private String noteId;
    private String title;
    private String body;

}
