package com.Musicom.api.image_link_checker;

import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.message.BasicHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ImageLinkCheckerTest {
    @Mock
    private CloseableHttpClient mockHttpClient;

    @InjectMocks
    private ImageLinkChecker imageLinkChecker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnTrueWhenLinkPointsAtImage() throws Exception {
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        String url = "http://example.com/image.jpg";
        Header contentTypeHeader = new BasicHeader("Content-Type", "image/jpeg");
        when(mockResponse.getFirstHeader("Content-Type")).thenReturn(contentTypeHeader);
        when(mockHttpClient.execute(any(HttpHead.class))).thenReturn(mockResponse);

        boolean result = imageLinkChecker.isImageLink(url);

        assertTrue(result);
        verify(mockHttpClient).execute(any(HttpHead.class));
        verify(mockResponse).getFirstHeader("Content-Type");
    }

    @Test
    void shouldReturnFalseWhenLinkDoesNotPointAtImage() throws Exception {
        CloseableHttpResponse mockResponse = mock(CloseableHttpResponse.class);
        String url = "http://example.com/file.txt";
        Header contentTypeHeader = new BasicHeader("Content-Type", "text/plain");
        when(mockResponse.getFirstHeader("Content-Type")).thenReturn(contentTypeHeader);
        when(mockHttpClient.execute(any(HttpHead.class))).thenReturn(mockResponse);

        boolean result = imageLinkChecker.isImageLink(url);

        assertFalse(result);
        verify(mockHttpClient).execute(any(HttpHead.class));
        verify(mockResponse).getFirstHeader("Content-Type");
    }

    @Test
    void shouldReturnFalseWhenExceptionIsThrown() throws Exception {
        String url = "http://example.com/image.jpg";
        when(mockHttpClient.execute(any(HttpHead.class))).thenThrow(new IOException());

        boolean result = imageLinkChecker.isImageLink(url);

        assertFalse(result);
        verify(mockHttpClient).execute(any(HttpHead.class));
    }
}
