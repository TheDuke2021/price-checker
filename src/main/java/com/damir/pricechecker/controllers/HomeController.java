package com.damir.pricechecker.controllers;


import com.damir.pricechecker.models.Item;
import com.damir.pricechecker.parsers.DNSParser;
import com.fasterxml.jackson.databind.node.TextNode;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    WebDriver webDriver;
    DNSParser dnsParser;

    List<Item> itemList = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        dnsParser = new DNSParser(webDriver);
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }


    @PostMapping("/")
    @ResponseBody
    public List<Item> processSearchQuery(@RequestBody String queryParameter) {
        queryParameter = queryParameter.substring(queryParameter.indexOf("=") + 1);
        return dnsParser.parse(queryParameter);
    }
}
