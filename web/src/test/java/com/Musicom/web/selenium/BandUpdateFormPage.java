package com.Musicom.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BandUpdateFormPage {
    @FindBy(id = "id")
    private WebElement idInput;

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

    public BandUpdateFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public BandUpdateFormPage open() {
        webDriver.get("http://localhost:8080/band/update form");
        return this;
    }

    public BandUpdateFormPage fillInIdInput(int id) {
        idInput.clear();
        idInput.sendKeys(id+"");
        return this;
    }

    public BandUpdateFormPage fillInNameInput(String name) {
        nameInput.sendKeys(name);
        return this;
    }

    public BandUpdateFormPage fillInUrlInput(String url) {
        urlInput.sendKeys(url);
        return this;
    }

    public BandUpdateFormPage fillInPopularityInput(int popularity) {
        popularityInput.clear();
        popularityInput.sendKeys(popularity+"");
        return this;
    }

    public BandUpdateFormPage fillInImageInput(String imageUrl) {
        imageInput.sendKeys(imageUrl);
        return this;
    }

    public BandAllPage submitForm() {
        submitButton.click();
        return new BandAllPage(webDriver);
    }
}
