package com.github.dkanellis.fikey;

import com.github.dkanellis.fikey.views.RegisterDeviceResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * @author Dimitris Kanellis
 */
public class FiKeyApplication extends Application<FiKeyConfiguration> {

    public static void main(String[] args) throws Exception {
        new FiKeyApplication().run(args);
    }

    @Override
    public String getName() {
        return "FiKey";
    }

    @Override
    public void initialize(Bootstrap<FiKeyConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
        ;
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html", "static"));
    }

    @Override
    public void run(FiKeyConfiguration fiKeyConfiguration, Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/api/*");
        final RegisterDeviceResource resource = new RegisterDeviceResource();
        environment.jersey().register(resource);
    }
}
