package com.damir.pricechecker;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class PriceCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCheckerApplication.class, args);
	}

	@Bean
	public WebDriver webDriver() {
		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.addArguments("--disable-features=NetworkService");
		options.addArguments("--dns-prefetch-disable");
//		options.addArguments("--headless");
		options.addArguments("--disable-extensions");
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Damir\\Desktop\\chromedriver.exe");
		WebDriver driver = new ChromeDriver(options);
		return driver;
	}
}
