package com.github.dkanellis.fikeyserverexample;

import com.github.dkanellis.fikey.storage.DataStorage;
import com.github.dkanellis.fikey.storage.Requests;
import com.github.dkanellis.fikeyserverexample.views.login.AuthenticateDeviceResource;
import com.github.dkanellis.fikeyserverexample.views.register.RegisterDeviceResource;
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
        DataStorage.getInstance().init();
        Requests.getInstance().init();
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html", "static"));
    }

    @Override
    public void run(FiKeyConfiguration fiKeyConfiguration, Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new RegisterDeviceResource());
        environment.jersey().register(new AuthenticateDeviceResource());
    }
}
