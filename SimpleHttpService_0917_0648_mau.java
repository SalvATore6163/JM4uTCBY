// 代码生成时间: 2025-09-17 06:48:20
package com.example.httpservice;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello World";
    }
}

public class SimpleHttpService extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new SimpleHttpService().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        // Initializes ViewBundle with Freemarker template.
        bootstrap.addBundle(new ViewBundle<Configuration>() {
            @Override
            public void run(Configuration configuration, Environment environment) {
                environment.views().configuration(new Configuration().templateLoaderPath("/mustache"));
                environment.views().defaultRenderer(new MustacheViewRenderer());
            }
        });
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        environment.jersey().register(new HelloResource());
    }
}