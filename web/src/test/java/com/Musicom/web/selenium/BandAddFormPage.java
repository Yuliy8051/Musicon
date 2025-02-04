package com.Musicom.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BandAddFormPage {
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "url")
    private WebElement urlInput;

    @FindBy(id = "popularity")
    private WebElement popularityInput;

    @FindBy(id = "image")
    private WebElement imageInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    private final WebDriver webDriver;

    public BandAddFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public BandAddFormPage open() {
        webDriver.get("http://localhost:8080/band/add form");
        return this;
    }

    public BandAddFormPage fillInNameInput(String name) {
        nameInput.sendKeys(name);
        return this;
    }

    public BandAddFormPage fillInUrlInput(String url) {
        urlInput.sendKeys(url);
        return this;
    }

    public BandAddFormPage fillInPopularityInput(int popularity) {
        popularityInput.clear();
        popularityInput.sendKeys(popularity+"");
        return this;
    }

    public BandAddFormPage fillInImageInput(String imageUrl) {
        imageInput.sendKeys(imageUrl);
        return this;
    }

    public BandAllPage submitForm() {
        submitButton.click();
        return new BandAllPage(webDriver);
    }
}
