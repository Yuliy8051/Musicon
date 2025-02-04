package com.Musicom.api.image_link_checker;

import lombok.AllArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

@AllArgsConstructor
public class ImageLinkChecker {
    private CloseableHttpClient httpClient;

    public boolean isImageLink(String url) {
        try (CloseableHttpClient client = httpClient) {
            HttpHead headRequest = new HttpHead(url);
            try (CloseableHttpResponse response = client.execute(headRequest)) {
                String contentType = response.getFirstHeader("Content-Type").getValue();
                return contentType.startsWith("image/");
            }
        } catch (IOException ex) {
            return false;
        } catch (IllegalStateException ex) {
            httpClient = HttpClients.createDefault();
            return isImageLink(url);
        }
    }
}
