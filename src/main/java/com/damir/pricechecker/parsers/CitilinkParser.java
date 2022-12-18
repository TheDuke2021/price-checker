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
public class CitilinkParser extends Parser{

    public CitilinkParser() {
        super("https://www.citilink.ru", "https://www.citilink.ru/search/?text=", "+");
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
            Elements htmlItems = doc.getElementsByClass("GroupGrid").first().children();
            for (int i = 0; i < MAX_COUNT; i++) {
                Element htmlItem = htmlItems.get(i);
                String itemURL = domain + htmlItem.getElementsByTag("a").first().attr("href");
                String itemPhotoURL = htmlItem.getElementsByClass("ProductCardVertical__picture").first().attr("src");
                String itemName = htmlItem.getElementsByClass("ProductCardVertical__name").first().text();

                long itemPrice;

                String itemPriceString = htmlItem.getElementsByClass("ProductCardVerticalPrice__price-current_current-price").first().text();
                //Remove all characters, keeping only numbers
                itemPriceString = itemPriceString.replaceAll("[^\\d.]", "");
                itemPrice = Long.parseLong(itemPriceString);

                Item item = new Item(itemURL, itemPhotoURL, itemName, itemPrice, Item.Shop.CITILINK);
                itemList.add(item);
            }
            return itemList;
        }catch (Exception e) {
            return itemList;

        }
    }
}
