package com.damir.pricechecker.parsers;

import com.damir.pricechecker.models.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DNSParser extends Parser{


    public DNSParser() {
        super("https://www.dns-shop.ru", "https://www.dns-shop.ru/search/?q=", "+");
    }

    @Override
    public List<Item> parse(String queryParameter) {
        ArrayList<Item> itemList = new ArrayList<>();
        queryParameter = queryParameter.replaceAll("\\+", splitter);

        try {
            //Parsing document
            Map<String, String> headers = new HashMap<>();
            headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
            headers.put("Accept-Encoding", "gzip, deflate, br");
            headers.put("Accept-Language", "ru");
            headers.put("Connection", "keep-alive");
            headers.put("Cookie", "lang=ru; city_path=moscow; current_path=75a2da2a93c8cd1c2e00f91901d024508daafdcdf99566e6de24aeb998c59557a%3A2%3A%7Bi%3A0%3Bs%3A12%3A%22current_path%22%3Bi%3A1%3Bs%3A114%3A%22%7B%22city%22%3A%2230b7c1f3-03fb-11dc-95ee-00151716f9f5%22%2C%22cityName%22%3A%22%5Cu041c%5Cu043e%5Cu0441%5Cu043a%5Cu0432%5Cu0430%22%2C%22method%22%3A%22geoip%22%7D%22%3B%7D; _ab_=46eb681d8dd7155805c0d06277254f0cd7d2465c3f20559d1f09b713f3fefe59a%3A2%3A%7Bi%3A0%3Bs%3A4%3A%22_ab_%22%3Bi%3A1%3Ba%3A1%3A%7Bs%3A21%3A%22search-category-score%22%3Bs%3A20%3A%22category_score_exact%22%3B%7D%7D; rerf=AAAAAGOfAv8DuiIjA6drAg==; ipp_uid=1671365374741/gb22ts40nl2h26P0//62H6CUKwGaX3elf4BcO3Q==; ipp_key=v1671365374741/v33947245ba5adc7a72e273//GPGtSVWlPO+tMzR3Y4NsA==");
            headers.put("Host", "www.dns-shop.ru");
            headers.put("Sec-Fetch-Dest", "document");
            headers.put("Sec-Fetch-Mode", "navigate");
            headers.put("Sec-Fetch-Site", "none");
            headers.put("Sec-Fetch-User", "?1");
            headers.put("Upgrade-Insecure-Requests", "1");
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36");
            headers.put("sec-ch-ua", "\"Not?A_Brand\";v=\"8\", \"Chromium\";v=\"108\", \"Google Chrome\";v=\"108\"");
            headers.put("sec-ch-ua-mobile", "?0");
            headers.put("sec-ch-ua-platform", "\"Windows\"");

            Map<String, String> cookies = new HashMap<>();
            cookies.put("lang", "ru");
            cookies.put("city_path", "moscow");
            cookies.put("current_path", "75a2da2a93c8cd1c2e00f91901d024508daafdcdf99566e6de24aeb998c59557a%3A2%3A%7Bi%3A0%3Bs%3A12%3A%22current_path%22%3Bi%3A1%3Bs%3A114%3A%22%7B%22city%22%3A%2230b7c1f3-03fb-11dc-95ee-00151716f9f5%22%2C%22cityName%22%3A%22%5Cu041c%5Cu043e%5Cu0441%5Cu043a%5Cu0432%5Cu0430%22%2C%22method%22%3A%22geoip%22%7D%22%3B%7D");
            cookies.put("_ab_", "46eb681d8dd7155805c0d06277254f0cd7d2465c3f20559d1f09b713f3fefe59a%3A2%3A%7Bi%3A0%3Bs%3A4%3A%22_ab_%22%3Bi%3A1%3Ba%3A1%3A%7Bs%3A21%3A%22search-category-score%22%3Bs%3A20%3A%22category_score_exact%22%3B%7D%7D");
            cookies.put("rerf", "AAAAAGOfAv8DuiIjA6drAg==");
            cookies.put("ipp_uid", "1671365374741/gb22ts40nl2h26P0//62H6CUKwGaX3elf4BcO3Q==");
            cookies.put("ipp_key", "v1671365374741/v33947245ba5adc7a72e273//GPGtSVWlPO+tMzR3Y4NsA==");

            Document doc = Jsoup.connect(URL + queryParameter).cookies(cookies).headers(headers).maxBodySize(0).get();
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
            e.printStackTrace();
            return itemList;
        }
    }
}
