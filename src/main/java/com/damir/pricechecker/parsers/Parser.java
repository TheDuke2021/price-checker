package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public abstract class Parser {

    public final long NULL_PRICE = -1;
    public final int MAX_COUNT = 10;

    protected String domain;
    protected String URL;
    protected String splitter;
    protected final WebDriver webDriver;

    public Parser(String domain, String URL, String splitter, WebDriver webDriver) {
        this.domain = domain;
        this.URL = URL;
        this.splitter = splitter;
        this.webDriver = webDriver;
    }

    public abstract List<Item> parse(String queryParameter);

/*    protected String getFormattedPrice(String price) {
        //Remove all characters, keeping only numbers
        price = price.replaceAll("[^\\d.]", "");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        DecimalFormat df = new DecimalFormat("###,###,###", symbols);
        price = df.format(Integer.parseInt(price));
        price += "р";

        return price;
    }*/
}
