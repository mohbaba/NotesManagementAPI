package org.example.dtos.requests;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    private String username;
    private String noteId;
}
