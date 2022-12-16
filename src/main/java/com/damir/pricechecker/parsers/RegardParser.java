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

public class RegardParser extends Parser{


    public RegardParser(WebDriver webDriver) {
        super("https://www.regard.ru/", "https://www.regard.ru/catalog?search=", "%20", webDriver);
    }

    @Override
    public List<Item> parse(String queryParameter) {
        ArrayList<Item> itemList = new ArrayList<>();
        queryParameter = queryParameter.replaceAll("\\+", splitter);
        webDriver.get(URL + queryParameter);

        //Scrolling page to load product photos
/*        try{
            for(int i = 0; i < 5; i++) {
                ((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0,500)");
                Thread.sleep(150);
            }
        }catch(InterruptedException e) {
            //TODO
        }*/

        //Parsing document
        Document doc = Jsoup.parse(webDriver.getPageSource());
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
//            e.printStackTrace();
            if(!itemList.isEmpty())
                return itemList;

            return null;
        }
    }
}
