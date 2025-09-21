// 代码生成时间: 2025-09-21 12:53:29
package com.example.helloworld;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import com.example.helloworld.db.User;
import com.example.helloworld.db.UserDao;
import com.example.helloworld.resources.UserResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

public class UserApplication extends Application<UserConfig> {

    public static void main(String[] args) throws Exception {
        new UserApplication().run(args);
    }

    @Override
    public String getName() {
        return "user-service";
    }

    @Override
    public void initialize(Bootstrap<UserConfig> bootstrap) {
        // Initialize configuration and setup bundles
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new MigrationsBundle<UserConfig>() {
            public DataSourceFactory getDataSourceFactory(UserConfig configuration) {
                return configuration.getDataSourceFactory();
            }
        });
        bootstrap.addBundle(new HibernateBundle<UserConfig, User>(User.class) {
            public DBIFactory getDBIFactory() {
                return new DBIFactory();
            }
        });
    }

    @Override
    public void run(UserConfig configuration, Environment environment) {
        // Set up the DAO with DBI
        final UserDao userDao = new UserDao(configuration.getHibernateBundle());
        final DBI dbi = new DBIFactory().build(environment, configuration.getDataSourceFactory(), "postgresql");
        final UserDAO userDAO = dbi.onDemand(UserDAO.class);

        // Set up the resource
        final UserResource userResource = new UserResource(userDAO);
        environment.jersey().register(userResource);

        // Set up CORS filters
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter(CrossOriginFilter.class, "CORS");
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}
