package com.damir.pricechecker;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PriceCheckerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCheckerApplication.class, args);
	}

	@Bean
	public WebDriver webDriver() {
		System.setProperty("webdriver.chrome.driver","src/main/resources/selenium/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		return driver;
	}
}
