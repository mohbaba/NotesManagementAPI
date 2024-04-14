package org.example.dtos.responses;

import lombok.Data;

@Data
public class RegisterUserResponse {
    private String userId;
    private String username;
    private String password;
}
