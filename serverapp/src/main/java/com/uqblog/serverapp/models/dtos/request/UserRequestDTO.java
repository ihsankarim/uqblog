package com.uqblog.serverapp.models.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Username is required!")
    @Size(min = 3, message = "Username must have atleast 3 characters!")
    @Size(max = 20, message = "Username can have almost 20 characters!")
    private String username;

    @NotBlank(message = "Password is required!")
    private String password;
    
    @Email(message = "Email is not in valid format!")
    @NotBlank(message = "Email is required!")
    private String email;
}
