package com.hoaxify.ws.user.dto;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(
        @NotBlank
        @Size(min = 4, max = 99)
        String username,

        @NotBlank
        //@UniqueEmail
        @Email
        String email,

        @Size(min = 8, max = 99)
        @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constraint.password.pattern}")
        String password
) {
    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

}
