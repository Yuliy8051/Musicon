package com.Musicom.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TrackSearchFormPage {
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    private final WebDriver webDriver;

    public TrackSearchFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public TrackSearchFormPage open() {
        webDriver.get("http://localhost:8080/track/search form");
        return this;
    }

    public TrackSearchFormPage fillInNameInput(String name) {
        nameInput.sendKeys(name);
        return this;
    }

    public SearchResultPage submitForm() {
        submitButton.click();
        return new SearchResultPage(webDriver);
    }
}
