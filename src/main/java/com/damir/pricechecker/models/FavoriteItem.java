package com.damir.pricechecker.models;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class FavoriteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Long userId;
    @Embedded
    private Item item;

    public FavoriteItem(Long userId, Item item) {
        this.userId = userId;
        this.item = item;
    }

    public FavoriteItem(Item item) {
        this.item = item;
    }
}
