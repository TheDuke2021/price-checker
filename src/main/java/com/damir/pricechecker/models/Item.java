package com.damir.pricechecker.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class Item implements Comparable<Item>{

    private String URL;
    private String photoURL;
    private String name;
    private long price;
    @Enumerated(value = EnumType.STRING)
    private Shop shop;

    public enum Shop {
        DNS, REGARD, CITILINK, PLEER, WILDBERRIES
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
