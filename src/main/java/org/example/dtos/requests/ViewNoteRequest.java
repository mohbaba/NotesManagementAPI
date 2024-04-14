package org.example.dtos.requests;

import lombok.Data;

@Data
public class ViewNoteRequest {
    private String noteId;
    private String username;
}
