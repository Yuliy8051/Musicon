package com.Musicom.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AlbumSearchFormPage {
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    private final WebDriver webDriver;

    public AlbumSearchFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public AlbumSearchFormPage open() {
        webDriver.get("http://localhost:8080/album/search form");
        return this;
    }

    public AlbumSearchFormPage fillInNameInput(String name) {
        nameInput.sendKeys(name);
        return this;
    }

    public SearchResultPage submitForm() {
        submitButton.click();
        return new SearchResultPage(webDriver);
    }
}
