package com.egnyte.blog.server.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        registerEndpoints();

    }

    private void registerEndpoints() {
        // register(...);

    }

    @Override
    public String getApplicationName() {
        return super.getApplicationName();
    }
}
