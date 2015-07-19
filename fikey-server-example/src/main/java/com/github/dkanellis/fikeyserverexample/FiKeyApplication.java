package com.github.dkanellis.fikeyserverexample;

import com.github.dkanellis.fikey.FiKeyAuth;
import com.github.dkanellis.fikeyserverexample.views.login.AuthenticateResource;
import com.github.dkanellis.fikeyserverexample.views.register.RegisterResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

/**
 * @author Dimitris Kanellis
 */
public class FiKeyApplication extends Application<FiKeyConfiguration> {

    public static final String APP_ID = "https://localhost:8080";

    public static void main(String[] args) throws Exception {
        new FiKeyApplication().run(args);
    }

    @Override
    public String getName() {
        return "FiKey";
    }

    @Override
    public void initialize(Bootstrap<FiKeyConfiguration> bootstrap) {
        FiKeyAuth.initDefaultStorage();
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html", "static"));
    }

    @Override
    public void run(FiKeyConfiguration fiKeyConfiguration, Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new RegisterResource());
        environment.jersey().register(new AuthenticateResource());
    }
}
