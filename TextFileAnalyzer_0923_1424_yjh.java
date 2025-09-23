// 代码生成时间: 2025-09-23 14:24:29
 * @author [Your Name]
 * @version 1.0
 */
import io.dropwizard.Application;
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Path("/analyze")
public class TextFileAnalyzerResource {
    private final String filePath;

    public TextFileAnalyzerResource(String filePath) {
        this.filePath = filePath;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String analyze() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        int wordCount = lines.stream()
            .flatMap(line -> line.split("\W+")) // Split the line into words
            .filter(word -> !word.isEmpty()) // Filter out empty words
            .distinct() // Remove duplicates
            .count(); // Count distinct words

        return "Total distinct words: " + wordCount;
    }
}

public class TextFileAnalyzerApplication extends Application<TextFileAnalyzerConfiguration> {
    @Override
    public void initialize(Bootstrap<TextFileAnalyzerConfiguration> bootstrap) {
        // Nothing to initialize here
    }

    @Override
    public void run(TextFileAnalyzerConfiguration configuration, Environment environment) {
        // Set up resources and providers
        environment.jersey().register(new TextFileAnalyzerResource(configuration.getFilePath()));
    }

    public static void main(String[] args) throws Exception {
        new TextFileAnalyzerApplication().run(args);
    }
}

/*
 * Configuration class for the application
 */
class TextFileAnalyzerConfiguration extends Configuration {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
