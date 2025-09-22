// 代码生成时间: 2025-09-22 10:23:26
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseMigrationTool extends Application<DatabaseMigrationToolConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseMigrationTool.class);

    private ExecutorService executorService;

    @Override
    public String getName() {
        return "DatabaseMigrationTool";
    }

    @Override
    public void initialize(Bootstrap<DatabaseMigrationToolConfiguration> bootstrap) {
        // Initialize the migrations bundle
        bootstrap.addBundle(new MigrationsBundle<DatabaseMigrationToolConfiguration>()
                .addLiquibaseChangeLog("db/changelog.xml", "db.changelog"));
    }

    @Override
    public void run(DatabaseMigrationToolConfiguration configuration, Environment environment) {
        // Set up the executor service
        executorService = Executors.newSingleThreadExecutor();

        // Perform database migration
        executorService.submit(() -> {
            try {
                Liquibase liquibase = new Liquibase(
                        "db/changelog.xml",
                        new ClassLoaderResourceAccessor(),
                        configuration.getDataSourceFactory().getDataSource().getConnection()
                );
                liquibase.update("test"); // Update with the correct context names
                LOGGER.info("Database migration completed successfully.");
            } catch (Exception e) {
                LOGGER.error("Error during database migration: ", e);
            }
        });
    }

    public static void main(String[] args) throws Exception {
        new DatabaseMigrationTool().run(args);
    }
}

// Configuration class for database settings
class DatabaseMigrationToolConfiguration extends io.dropwizard.Configuration {
    // Define the data source factory
    private DataSourceFactory dataSourceFactory;

    // Getters and setters for dataSourceFactory
    public DataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }
}
