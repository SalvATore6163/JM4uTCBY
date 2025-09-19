// 代码生成时间: 2025-09-19 23:23:27
import com.dropwizard.Application;
import com.dropwizard.configuration.Environment;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpSession;

@Path("/theme")
public class ThemeResource {
    private final String theme;

    public ThemeResource(String theme) {
        this.theme = theme;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response setTheme(HttpSession session) {
        session.setAttribute("theme", theme);
        return Response.ok("Theme set to " + theme).build();
    }
}

public class ThemeSwitcherApplication extends Application<ThemeSwitcherConfiguration> {
    @Override
    public String getName() {
        return "ThemeSwitcher";
    }

    @Override
    public void initialize(Bootstrap<ThemeSwitcherConfiguration> bootstrap) {
        // Nothing to do here
    }

    @Override
    public void run(ThemeSwitcherConfiguration configuration, Environment environment) {
        // Register the ViewBundle to enable template rendering
        environment.jersey().register(new ThemeResource(configuration.getTheme()));
    }
}

// Configuration class for ThemeSwitcherApplication
public class ThemeSwitcherConfiguration extends Configuration {
    private String theme;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}

// This is the main class to start the Dropwizard application
public class ThemeSwitcherApp {
    public static void main(String[] args) throws Exception {
        new ThemeSwitcherApplication().run(args);
    }
}

// Add this to your pom.xml to include Dropwizard dependencies
// <dependencies>
//     <dependency>
//         <groupId>io.dropwizard</groupId>
//         <artifactId>dropwizard-core</artifactId>
//         <version>YOUR_DROPWIZARD_VERSION</version>
//     </dependency>
// </dependencies>

// Also, include the ViewBundle to handle the rendering of views
// <dependency>
//     <groupId>io.dropwizard</groupId>
//     <artifactId>dropwizard-views</artifactId>
//     <version>YOUR_DROPWIZARD_VERSION</version>
// </dependency>