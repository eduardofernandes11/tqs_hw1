package com.example.coviddata.webTesting;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {

    WebDriver browser;
    
    JavascriptExecutor js;

    @BeforeEach
    void startUp(){
        System.setProperty("webdriver.chrome.driver", "/Users/eduardo/Universidade/3Ano/TQS/chromedriver");
        browser = new ChromeDriver();
        js = (JavascriptExecutor) browser;
    }

    @AfterEach
    void tearDown() {
        browser.close();
    }

    @Test
    public void searchCityByName() throws InterruptedException {
        browser.get("http://127.0.0.1:8080/");
        js.executeScript("window.scrollBy(0,750)");
        browser.manage().window().setSize(new Dimension(1116, 697));
        browser.findElement(By.id("input")).click();
        browser.findElement(By.id("input")).sendKeys("Washington");
        browser.findElement(By.id("submitBtn")).click();
        TimeUnit.SECONDS.sleep(1);
        assertEquals("City: Washington", browser.findElement(By.id("city-name")).getText());
    }
}