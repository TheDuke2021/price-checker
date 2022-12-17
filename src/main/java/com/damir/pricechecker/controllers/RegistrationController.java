package com.damir.pricechecker.controllers;

import com.damir.pricechecker.data.AccountRepository;
import com.damir.pricechecker.models.Account;
import com.damir.pricechecker.models.FavoriteItem;
import com.damir.pricechecker.models.RegistrationForm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping
    public String registerForm() {
        return "register";
    }

    @PostMapping
    public String processRegistration(@Valid @ModelAttribute RegistrationForm registrationForm,
                                      BindingResult result, HttpServletRequest request)
            throws ServletException {

        //Check on duplicate
        if(accountRepository.existsByUsername(registrationForm.getUsername()))
            result.addError(new FieldError("account",
                    "duplicateUsername",
                    "Аккаунт с такой почтой уже существует"));

        if(result.hasErrors())
            return "register";

        Account accountToSave = new Account(registrationForm.getUsername(),
                passwordEncoder.encode(registrationForm.getPassword()),
                new Date());
        accountToSave.addFavoriteItem(new FavoriteItem(accountToSave.getId(), "https:google.com"));
        accountRepository.save(accountToSave);

        //Auto login
        request.login(accountToSave.getUsername(), registrationForm.getPassword());

        return "redirect:/profile";
    }
}
