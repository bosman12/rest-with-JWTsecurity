package com.example.rest_example.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
        private String token;
        private Long id;
        private String username;

        public AuthenticationResponseDto(String token, Long id, String username) {
            this.token = token;
            this.id = id;
            this.username = username;
        }
}
