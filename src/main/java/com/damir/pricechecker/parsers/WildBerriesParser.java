//package com.damir.pricechecker.parsers;
//
//import com.damir.pricechecker.models.Item;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class WildBerriesParser extends Parser {
//
//    public WildBerriesParser() {
//        super("https://www.wildberries.ru/", "https://search.wb.ru/exactmatch/ru/common/v4/search?appType=1&couponsGeo=12,7,3,6,5,18,21&curr=rub&dest=-1216601,-337422,-1114902,-1198055&emp=0&lang=ru&locale=ru&pricemarginCoeff=1.0&reg=0&regions=80,64,83,4,38,33,70,69,68,86,30,40,48,1,22,66,31&resultset=catalog&sort=popular&spp=0&suppressSpellcheck=false&query=", "%20");
//    }
//
//    @Override
//    public List<Item> parse(String queryParameter) {
//        ArrayList<Item> itemList = new ArrayList<>();
//        queryParameter = queryParameter.replaceAll("\\+", splitter);
//
//        //Parsing document
//        JSONParser jsonParser = new JSONParser();
//        try {
//            doc = Jsoup.connect(URL + queryParameter).get();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        try {
//            Elements htmlItems = doc.getElementsByClass("GroupGrid").first().children();
//            for (int i = 0; i < MAX_COUNT; i++) {
//                Element htmlItem = htmlItems.get(i);
//                String itemURL = domain + htmlItem.getElementsByTag("a").first().attr("href");
//                String itemPhotoURL = htmlItem.getElementsByClass("ProductCardVertical__picture").first().attr("src");
//                String itemName = htmlItem.getElementsByClass("ProductCardVertical__name").first().text();
//
//                long itemPrice;
//
//                String itemPriceString = htmlItem.getElementsByClass("ProductCardVerticalPrice__price-current_current-price").first().text();
//                //Remove all characters, keeping only numbers
//                itemPriceString = itemPriceString.replaceAll("[^\\d.]", "");
//                itemPrice = Long.parseLong(itemPriceString);
//
//                Item item = new Item(itemURL, itemPhotoURL, itemName, itemPrice, Item.Shop.CITILINK);
//                itemList.add(item);
//            }
//            return itemList;
//        }catch (Exception e) {
//            if(!itemList.isEmpty())
//                return itemList;
//
//            return null;
//        }
//    }
//
//
//}
