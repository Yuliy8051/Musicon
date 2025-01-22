package com.Musicom.api;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.Musicom.updater", "com.Musicom.data"})
public class ApiConfiguration {
}
