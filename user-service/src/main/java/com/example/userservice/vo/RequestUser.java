package com.example.userservice.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class RequestUser {

    @NotNull(message = "Email cannot be null")
    @Size(min= 2, message = "Emial not be less than two characters")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min= 2, message = "Name not be less than two characters")
    @Email
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min= 8, message = "Password must be eqaul or grater than 8 characters")
    @Email
    private String pwd;
}
