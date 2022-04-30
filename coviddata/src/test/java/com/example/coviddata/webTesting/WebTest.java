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

    /*
    @Test
    void searchCityByLatAndLon() {
        browser.get("http://127.0.0.1:8000/");

        JavascriptExecutor js = (JavascriptExecutor) browser;
        js.executeScript("window.scrollBy(0,1000)");

        browser.manage().window().setSize(new Dimension(1116, 698));
        try {
            if (browser.findElements(By.id("searchBtn2")).size() <= 0 && !browser.findElement(By.id("searchBtn2")).isDisplayed()) {
                WebDriverWait wait2 = new WebDriverWait(browser, 120);
                wait2.until(ExpectedConditions.visibilityOf(browser.findElement(By.id("searchBtn2"))));
                browser.findElement(By.id("latitude")).click();
                browser.findElement(By.id("latitude")).sendKeys("40.64427");
                browser.findElement(By.id("longitude")).click();
                browser.findElement(By.id("longitude")).sendKeys("-8.64554");
                browser.findElement(By.id("searchBtn2")).click();
                assertEquals("Name: \"Aveiro\"", browser.findElement(By.id("name2")).getText());
            }
        } catch (StaleElementReferenceException e) {
            return;
        }
    }

    @Test
    void searchCacheDetails() {
        browser.get("http://127.0.0.1:8000/");
        JavascriptExecutor js = (JavascriptExecutor) browser;
        js.executeScript("window.scrollBy(0,1500)");

        try {
            if (browser.findElements(By.id("searchBtn3")).size() <= 0 && !browser.findElement(By.id("searchBtn3")).isDisplayed()) {
                WebDriverWait wait2 = new WebDriverWait(browser, 120);
                wait2.until(ExpectedConditions.visibilityOf(browser.findElement(By.id("searchBtn3"))));
                browser.findElement(By.id("searchBtn3")).click();
                assertEquals("hits: 0", browser.findElement(By.id("hits")).getText());
            }
        } catch (StaleElementReferenceException e) {
            return;
        }
    }
    */
}