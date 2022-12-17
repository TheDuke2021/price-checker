package com.damir.pricechecker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class FavoriteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itemId;


    private Long id;
    private String URL;

    public FavoriteItem(Long id, String URL) {
        this.id = id;
        this.URL = URL;
    }
}
