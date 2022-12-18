package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RegardParser extends Parser{


    public RegardParser() {
        super("https://www.regard.ru", "https://www.regard.ru/catalog?search=", "%20");
    }

    @Override
    public List<Item> parse(String queryParameter) {
        ArrayList<Item> itemList = new ArrayList<>();
        queryParameter = queryParameter.replaceAll("\\+", splitter);

        //Parsing document
        Document doc = null;
        try {
            doc = Jsoup.connect(URL + queryParameter).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Elements htmlItems = doc.getElementsByClass("rendererWrapper").first().firstElementChild().firstElementChild().children();
            for (int i = 0; i < MAX_COUNT; i++) {
                Element htmlItem = htmlItems.get(i);
                String itemURL = domain + htmlItem.getElementsByTag("a").first().attr("href");
                String itemPhotoURL = domain + htmlItem.getElementsByTag("img").first().attr("src");
                String itemName = htmlItem.getElementsByTag("h6").first().attr("title");

                long itemPrice;

                String itemPriceString = htmlItem.firstElementChild().child(1).child(1).getElementsByTag("span").first().text();
                //Remove all characters, keeping only numbers
                itemPriceString = itemPriceString.replaceAll("[^\\d.]", "");
                itemPrice = Long.parseLong(itemPriceString);

                Item item = new Item(itemURL, itemPhotoURL, itemName, itemPrice, Item.Shop.REGARD);
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
