package com.mateusjose98.management.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class User {

    private Long id;
    @NotEmpty(message = "Primeiro nome não pode ser vazio!")
    private String firstName;
    @NotEmpty(message = "Sobrenome obrigatório")
    private String lastName;
    @NotEmpty(message = "Email obrigatório")
    @Email(message = "Email inválido. Verifique a escrita!")
    private String email;
    @NotEmpty(message = "Senha não pode ser vazia!")
    private String password;
    private String address;
    private String phone;
    private String title;
    private String bio;
    private String imageUrl;
    private boolean enabled;
    private boolean isNotLocked;
    private boolean isUsingMfa;
    private LocalDateTime createdAt;
}
