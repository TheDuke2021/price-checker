package com.damir.pricechecker.controllers;

import com.damir.pricechecker.data.AccountRepository;
import com.damir.pricechecker.models.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Controller
public class ProfileController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/profile")
    public String showPage(Model model, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        if(account.getAvatar() != null) {
            String accountImage = Base64.getEncoder().encodeToString(account.getAvatar());
            model.addAttribute("accountImage", accountImage);
        }

        model.addAttribute("account", account);

        return "profile";
    }

    @PostMapping("/profile/avatar")
    public String updateAvatar(MultipartFile file, Authentication authentication) throws IOException {
        Account account = (Account) authentication.getPrincipal();
        account.setAvatar(file.getBytes());
        accountRepository.save(account);
        return "redirect:/profile";
    }

    @PostMapping("/profile/password")
    public String updatePassword(String password, Authentication authentication, HttpServletRequest request)
        throws ServletException {
        Account account = (Account) authentication.getPrincipal();
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        request.logout();
        request.login(account.getUsername(), password);

        return "redirect:/profile";
    }
}
