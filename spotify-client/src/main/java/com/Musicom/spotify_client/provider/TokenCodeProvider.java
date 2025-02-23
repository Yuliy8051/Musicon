package com.Musicom.spotify_client.provider;

import com.Musicom.spotify_client.exception.SpotifyClientException;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@RequiredArgsConstructor
public class TokenCodeProvider {
    private final String email;

    private final String password;

    private final SpotifyClientUriProvider uriProvider;

    private final WebDriverProvider webDriverProvider;

    private WebDriver webDriver;

    public String fetchCodeForToken() {
        openAuthorization();
        submitLogIn();
        String url = fetchUrlWithCode();

        String queryParam = "code=";
        int indexOfCodeValue = url.indexOf(queryParam) + queryParam.length();
        return url.substring(indexOfCodeValue);
    }

    private void openAuthorization() {
        webDriver = webDriverProvider.getWebDriver();
        String authorizationUrl = uriProvider.authorizationUrl();
        webDriver.get(authorizationUrl);
    }

    private void submitLogIn() {
        WebElement usernameInput = webDriver.findElement(By.id("login-username"));
        WebElement passwordInput = webDriver.findElement(By.id("login-password"));
        WebElement submitButton = webDriver.findElement(By.id("login-button"));

        usernameInput.sendKeys(email);
        passwordInput.sendKeys(password);
        submitButton.click();
    }

    private String fetchUrlWithCode() {
        sleep();
        WebElement acceptButton = webDriver.findElement(By.tagName("button"));
        acceptButton.click();

        sleep();
        String url = webDriver.getCurrentUrl();
        webDriver.close();
        return url;
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (Exception ex) {
            throw new SpotifyClientException.ThreadSleepException(ex.getMessage());
        }
    }
}
