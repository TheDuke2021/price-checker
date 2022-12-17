package com.damir.pricechecker.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegistrationForm {

    @NotNull(message = "Поле \"Почта\" не должно быть пустым")
    @NotEmpty(message = "Поле \"Почта\" не должно быть пустым")
    @Email(message = "Некорректный адрес почты")
    private String username;

    @NotNull(message = "Поле \"Пароль\" не должно быть пустым")
    @NotEmpty(message = "Поле \"Пароль\" не должно быть пустым")
    private String password;
}
