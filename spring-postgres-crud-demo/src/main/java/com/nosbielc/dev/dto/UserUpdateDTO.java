package com.nosbielc.dev.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {

    private String name;

    @Email(message = "Email deve ser válido")
    private String email;

    private String phone;

    private Boolean active;
}