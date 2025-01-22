package com.Musicom.spotify_client.provider;

import com.Musicom.spotify_client.exception.ThreadSleepException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

@Slf4j
@RequiredArgsConstructor
public class TokenCodeProvider {
    private final String email;

    private final String password;

    private final SpotifyClientUriProvider uriProvider;

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
        webDriver = new ChromeDriver();
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
            log.error(ex.getMessage()); // TODO: move into exception handler
            throw new ThreadSleepException(ex.getMessage());
        }
    }
}
