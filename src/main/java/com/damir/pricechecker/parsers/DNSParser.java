package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.List;

public class DNSParser extends Parser{


    public DNSParser(WebDriver webDriver) {
        super("https://www.dns-shop.ru", "https://www.dns-shop.ru/search/?q=", "+", webDriver);
    }

    @Override
    public List<Item> parse(String queryParameter) {
        ArrayList<Item> itemList = new ArrayList<>();
        queryParameter = queryParameter.replaceAll("\\+", splitter);
        webDriver.get(URL + queryParameter);

        //Scrolling page to load product photos
        try{
            for(int i = 0; i < 5; i++) {
                ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,500)");
                Thread.sleep(150);
            }
        }catch(InterruptedException e) {
            //TODO
        }

        //Parsing document
        Document doc = Jsoup.parse(webDriver.getPageSource());
        try {
            Elements htmlItems = doc.getElementsByClass("catalog-products").first().children();
            for (int i = 0; i < MAX_COUNT; i++) {
                Element htmlItem = htmlItems.get(i);
                String itemURL = domain + htmlItem.getElementsByTag("a").first().attr("href");
                String itemPhotoURL = htmlItem.getElementsByClass("catalog-product__image-link").first()
                        .getElementsByTag("picture").first().children().get(2).attr("src");
                String itemName = htmlItem.getElementsByClass("catalog-product__name").first()
                        .firstElementChild().text();

                long itemPrice;
                if (htmlItem.getElementsByClass("product-buy__price").first() != null) {
                    String itemPriceString = htmlItem.getElementsByClass("product-buy__price").first().firstChild().toString();
                    //Remove all characters, keeping only numbers
                    itemPriceString = itemPriceString.replaceAll("[^\\d.]", "");
                    itemPrice = Long.parseLong(itemPriceString);
                } else {
                    itemPrice = NULL_PRICE;
                }

                Item item = new Item(itemURL, itemPhotoURL, itemName, itemPrice, Item.Shop.DNS);
                itemList.add(item);
            }
            return itemList;
        }catch (Exception e) {
//            e.printStackTrace();
            if(!itemList.isEmpty())
                return itemList;

            return null;
        }
    }
}
