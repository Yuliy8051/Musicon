package com.Musicom.spotify_client;

import com.Musicom.spotify_client.provider.SpotifyClientUriProvider;
import com.Musicom.spotify_client.provider.TokenCodeProvider;
import com.Musicom.spotify_client.provider.WebDriverProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TokenCodeProviderTest {

    private String email;

    private String password;

    private SpotifyClientUriProvider uriProvider;

    private WebDriverProvider webDriverProvider;

    private TokenCodeProvider tokenCodeProvider;

    @BeforeEach
    public void setUp() {
        email = "testEmail";
        password = "testPassword";
        uriProvider = Mockito.mock(SpotifyClientUriProvider.class);
        webDriverProvider = Mockito.mock(WebDriverProvider.class);
        tokenCodeProvider = new TokenCodeProvider(email, password, uriProvider, webDriverProvider);
    }

    @Test
    public void shouldReturnCodeForToken() {
        WebDriver webDriver = mock(WebDriver.class);
        String authorizationUrl = "authorizationUrl";
        By byIdUsername = By.id("login-username");
        By byIdPassword = By.id("login-password");
        By byIdButton = By.id("login-button");
        WebElement usernameInput = mock(WebElement.class);
        WebElement passwordInput = mock(WebElement.class);
        WebElement submitButton = mock(WebElement.class);
        By byTagButton = By.tagName("button");
        WebElement acceptButton = mock(WebElement.class);
        String currentUrl = "code=testCode";
        String code = "testCode";

        when(webDriverProvider.getWebDriver()).thenReturn(webDriver);
        when(uriProvider.authorizationUrl()).thenReturn(authorizationUrl);
        when(webDriver.findElement(byIdUsername)).thenReturn(usernameInput);
        when(webDriver.findElement(byIdPassword)).thenReturn(passwordInput);
        when(webDriver.findElement(byIdButton)).thenReturn(submitButton);
        when(webDriver.findElement(byTagButton)).thenReturn(acceptButton);
        when(webDriver.getCurrentUrl()).thenReturn(currentUrl);

        String result = tokenCodeProvider.fetchCodeForToken();

        verify(webDriverProvider).getWebDriver();
        verify(uriProvider).authorizationUrl();
        verify(webDriver).get(authorizationUrl);
        verify(usernameInput).sendKeys(email);
        verify(passwordInput).sendKeys(password);
        verify(submitButton).click();
        verify(acceptButton).click();
        verify(webDriver).getCurrentUrl();
        verify(webDriver).close();

        assertEquals(code, result);
    }
}
