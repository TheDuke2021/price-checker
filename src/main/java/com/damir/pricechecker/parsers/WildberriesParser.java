package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class WildberriesParser extends Parser {

    public WildberriesParser() {
        super("https://www.wildberries.ru/", "https://search.wb.ru/exactmatch/ru/common/v4/search?appType=1&couponsGeo=12,7,3,6,5,18,21&curr=rub&dest=-1216601,-337422,-1114902,-1198055&emp=0&lang=ru&locale=ru&pricemarginCoeff=1.0&reg=0&regions=80,64,83,4,38,33,70,69,68,86,30,40,48,1,22,66,31&resultset=catalog&sort=popular&spp=0&suppressSpellcheck=false&query=", "%20");
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    public List<Item> parse(String queryParameter) {
        ArrayList<Item> itemList = new ArrayList<>();
        queryParameter = queryParameter.replaceAll("\\+", splitter);


        try {
            //Parsing document

            JSONArray products;
            InputStream is = new URL(URL + queryParameter).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonText);
            products = (JSONArray) ((JSONObject) jsonObject.get("data")).get("products");

            for (int i = 0; i < MAX_COUNT; i++) {
                JSONObject product = (JSONObject) products.get(i);
                String productId = product.get("id").toString();
                String itemURL = "https://www.wildberries.ru/catalog/" + productId + "/detail.aspx";
                String itemPhotoURL = guessRightPhoto(productId);
                String itemName = (String) product.get("name");

                long itemPrice;

                String itemPriceString = product.get("salePriceU").toString();
                itemPriceString = itemPriceString.substring(0, itemPriceString.length() - 2);
                //Remove all characters, keeping only numbers
                itemPriceString = itemPriceString.replaceAll("[^\\d.]", "");
                itemPrice = Long.parseLong(itemPriceString);

                Item item = new Item(itemURL, itemPhotoURL, itemName, itemPrice, Item.Shop.WILDBERRIES);
                itemList.add(item);
            }
            return itemList;
        }catch (Exception e) {
            return itemList;
        }
    }

    private String guessRightPhoto(String productId) throws IOException{
        String headStart = "https://basket-";
        String headEnd = ".wb.ru/";
        String photoURL;

        for(int i = 1; i < 11; i++) {
            photoURL = headStart + String.format("%02d", i) + headEnd;
            photoURL += "vol" + productId.substring(0, productId.length()-5) + "/";
            photoURL += "part" + productId.substring(0, productId.length()-3) + "/";
            photoURL += productId + "/images/c516x688/1.jpg";

            HttpURLConnection connection = (HttpURLConnection) new URL(photoURL).openConnection();
            try {
                if (connection.getResponseCode() == 200)
                    return photoURL;
            }catch(IOException e) {
                //Ignoring
            }
        }

        return "";
    }


}
