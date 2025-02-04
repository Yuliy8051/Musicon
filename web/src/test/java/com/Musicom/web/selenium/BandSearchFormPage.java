package com.Musicom.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BandSearchFormPage {
    @FindBy(id = "name")
    private WebElement nameInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    private final WebDriver webDriver;

    public BandSearchFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public BandSearchFormPage open() {
        webDriver.get("http://localhost:8080/band/search form");
        return this;
    }

    public BandSearchFormPage fillInNameInput(String name) {
        nameInput.sendKeys(name);
        return this;
    }

    public SearchResultPage submitForm() {
        submitButton.click();
        return new SearchResultPage(webDriver);
    }
}
