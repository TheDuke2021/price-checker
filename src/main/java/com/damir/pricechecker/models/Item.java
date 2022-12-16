package com.damir.pricechecker.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item implements Comparable<Item>{

    private String URL;
    private String photoURL;
    private String name;
    private long price;
    private Shop shop;

    public enum Shop {
        DNS, REGARD, CITILINK
    }

    @Override
    public int compareTo(Item o) {
        if(price <= -1)
            return 1;
        if(o.price <= -1)
            return -1;

        return (int) Math.signum(price - o.price);
    }
}
