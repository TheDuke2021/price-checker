package com.damir.pricechecker.controllers;


import com.damir.pricechecker.models.Item;
import com.damir.pricechecker.parsers.DNSParser;
import com.damir.pricechecker.parsers.RegardParser;
import com.fasterxml.jackson.databind.node.TextNode;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    WebDriver webDriver;
    DNSParser dnsParser;
    RegardParser regardParser;

    List<Item> itemList = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        dnsParser = new DNSParser(webDriver);
        regardParser = new RegardParser(webDriver);
        //Parse data to cache
        dnsParser.parse("GTX 1660 Ti");
        regardParser.parse("GTX 1660 Ti");
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @PostMapping("/")
    @ResponseBody
    public List<Item> processSearchQuery(@RequestBody String queryParameter) {
        itemList.clear();
        queryParameter = queryParameter.substring(queryParameter.indexOf("=") + 1);
        List<Item> tempList;
        tempList = dnsParser.parse(queryParameter);
        if(tempList != null)
            itemList.addAll(tempList);
        tempList = regardParser.parse(queryParameter);
        if(tempList != null)
            itemList.addAll(tempList);

        Collections.sort(itemList);
        return itemList;
    }
}
