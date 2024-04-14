package org.example.dtos.responses;

import lombok.Data;

@Data
public class ViewNoteResponse {
    private String noteId;
    private String title;
    private String content;
}
