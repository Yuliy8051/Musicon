package com.Musicom.updater;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.Musicom.data", "com.Musicom.spotify_client"})
public class UpdaterConfig {
}
