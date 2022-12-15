package com.damir.pricechecker.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    private String URL;
    private String photoURL;
    private String name;
    private String price;
    private Shop shop;

    public enum Shop {
        DNS, REGARD, CITILINK
    }
}
