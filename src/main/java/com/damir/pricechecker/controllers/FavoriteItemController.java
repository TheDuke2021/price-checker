package com.damir.pricechecker.controllers;

import com.damir.pricechecker.data.AccountRepository;
import com.damir.pricechecker.models.Account;
import com.damir.pricechecker.models.FavoriteItem;
import com.damir.pricechecker.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class FavoriteItemController {

    @Autowired
    AccountRepository accountRepository;


    @PostMapping(path = "/favorite-item", produces = "application/json")
    public Long addFavoriteItem(@RequestBody Item item, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        account.addFavoriteItem(new FavoriteItem(account.getId(), item));
        Account accountToSave = accountRepository.save(account);
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(accountToSave,
                        accountToSave.getPassword(),
                        accountToSave.getAuthorities()));

        return accountToSave.getFavoriteItems().stream().filter(r -> r.getItem().equals(item)).findFirst().get().getId();
    }

    @DeleteMapping("/favorite-item/{id}")
    public void removeFavoriteItem(@PathVariable Long id, Authentication authentication) {
        Account account = (Account) authentication.getPrincipal();
        FavoriteItem itemToRemove = account.getFavoriteItems().stream().filter(r -> r.getId() == id).findFirst().get();
        account.removeFavoriteItem(itemToRemove);
        Account accountToSave = accountRepository.save(account);
        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken(accountToSave,
                        accountToSave.getPassword(),
                        accountToSave.getAuthorities()));
    }

}
