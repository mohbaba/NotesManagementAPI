package org.example.dtos.responses;


import lombok.Data;

@Data
public class LogoutUserResponse {
    private String userId;
    private String username;
}
