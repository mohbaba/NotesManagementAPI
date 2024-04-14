package org.example.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FindNotesResponse {
    private List<NoteResponse> notes;
}
