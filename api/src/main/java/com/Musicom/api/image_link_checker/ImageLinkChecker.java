package com.Musicom.api.image_link_checker;


import org.apache.hc.client5.http.classic.methods.HttpHead;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.stereotype.Component;

@Component
public class ImageLinkChecker {
    public boolean isImageLink(String url) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpHead headRequest = new HttpHead(url);
            try (CloseableHttpResponse response = client.execute(headRequest)) {
                String contentType = response.getFirstHeader("Content-Type").getValue();
                return contentType.startsWith("image/");
            }
        } catch (Exception e) {
            return false;
        }
    }
}
