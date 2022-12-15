package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;

import java.util.List;

public abstract class Parser {
    public abstract List<Item> parse(String queryParameter);
}
