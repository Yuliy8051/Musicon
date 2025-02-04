package com.Musicom.web.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BandDeleteFormPage {
    @FindBy(id = "id")
    private WebElement idInput;

    @FindBy(id = "submit")
    private WebElement submitButton;

    private WebDriver webDriver;

    public BandDeleteFormPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public BandDeleteFormPage open() {
        webDriver.get("http://localhost:8080/band/delete form");
        return this;
    }

    public BandDeleteFormPage fillInIdInput(int id) {
        idInput.clear();
        idInput.sendKeys(id+"");
        return this;
    }

    public BandAllPage submitForm() {
        submitButton.click();
        return new BandAllPage(webDriver);
    }
}
