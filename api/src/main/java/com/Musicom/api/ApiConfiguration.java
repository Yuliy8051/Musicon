package com.Musicom.api;

import com.Musicom.api.image_link_checker.ImageLinkChecker;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.Musicom.updater", "com.Musicom.data"})
public class ApiConfiguration {
    @Bean
    public ImageLinkChecker imageLinkChecker() {
        return new ImageLinkChecker(HttpClients.createDefault());
    }
}
