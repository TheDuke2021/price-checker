package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;

import java.util.List;

public abstract class Parser {

    public final long NULL_PRICE = -1;
    public final int MAX_COUNT = 10;

    protected String domain;
    protected String URL;
    protected String splitter;

    public Parser(String domain, String URL, String splitter) {
        this.domain = domain;
        this.URL = URL;
        this.splitter = splitter;
    }

    public abstract List<Item> parse(String queryParameter);

}
