package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class DNSParser extends Parser{

    private final String domain = "https://www.dns-shop.ru";
    private final String URL = "https://www.dns-shop.ru/search/?q=";
    private final String SPLITTER = "+";
    private WebDriver webDriver;

    public DNSParser(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public List<Item> parse(String queryParameter) {
        ArrayList<Item> itemList = new ArrayList<>();
//        queryParameter = queryParameter.replaceAll("\\s+", SPLITTER);
        queryParameter = queryParameter.replaceAll("\\+", SPLITTER);
        webDriver.get(URL + queryParameter);
        try{
            for(int i = 0; i < 5; i++) {
                ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,500)");
                Thread.sleep(150);
            }
        }catch(InterruptedException e) {
            //TODO
        }
        Document doc = Jsoup.parse(webDriver.getPageSource());
        try {
            Elements htmlItems = doc.getElementsByClass("catalog-products").first().children();
            final int COUNT = 5;
            for (int i = 0; i < COUNT; i++) {
                Element htmlItem = htmlItems.get(i);
                String itemURL = domain + htmlItem.getElementsByTag("a").first().attr("href");
                String itemPhotoURL = htmlItem.getElementsByClass("catalog-product__image-link").first()
                        .getElementsByTag("picture").first().children().get(2).attr("src");
                String itemName = htmlItem.getElementsByClass("catalog-product__name").first()
                        .firstElementChild().text();

                String itemPrice = "";
                if (htmlItem.getElementsByClass("product-buy__price").first() != null) {
                    itemPrice = htmlItem.getElementsByClass("product-buy__price").first().firstChild().toString();
                    itemPrice = itemPrice.replaceAll("[^\\d.]", "");
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setGroupingSeparator(' ');
                    DecimalFormat df = new DecimalFormat("###,###,###", symbols);
                    itemPrice = df.format(Integer.parseInt(itemPrice));
                    itemPrice += "р";
                } else {
                    itemPrice = "Нет цены";
                }

                Item item = new Item(itemURL, itemPhotoURL, itemName, itemPrice, Item.Shop.DNS);
                itemList.add(item);
            }
            return itemList;
        }catch (Exception e) {
            if(!itemList.isEmpty())
                return itemList;

            return null;
        }
    }
}
