package com.damir.pricechecker.controllers;


import com.damir.pricechecker.models.Account;
import com.damir.pricechecker.models.Item;
import com.damir.pricechecker.parsers.CitilinkParser;
import com.damir.pricechecker.parsers.DNSParser;
import com.damir.pricechecker.parsers.RegardParser;
import com.damir.pricechecker.parsers.WildberriesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    DNSParser dnsParser;
    @Autowired
    RegardParser regardParser;
    @Autowired
    CitilinkParser citilinkParser;
    @Autowired
    WildberriesParser wildBerriesParser;

    List<Item> itemList = new ArrayList<>();


    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> authorities = authentication.getAuthorities()
                .stream().map(e -> e.getAuthority()).toList();
        model.addAttribute("authorities", authorities);
        if (authorities.contains("ROLE_USER")) {
            Account account = (Account) authentication.getPrincipal();
            model.addAttribute("favoriteItems", account.getFavoriteItems());
        }

        return "index";
    }


    @PostMapping("/")
    @ResponseBody
    public List<Item> processSearchQuery(@RequestBody String queryParameter) {
        itemList.clear();
        queryParameter = queryParameter.substring(queryParameter.indexOf("=") + 1);
//        itemList.addAll(dnsParser.parse(queryParameter));
        itemList.addAll(citilinkParser.parse(queryParameter));
        itemList.addAll(wildBerriesParser.parse(queryParameter));
        itemList.addAll(regardParser.parse(queryParameter));

        Collections.sort(itemList);
        return itemList;
    }
}
