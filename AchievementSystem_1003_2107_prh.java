// 代码生成时间: 2025-10-03 21:07:59
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer.Default;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Path("/achievements")
public class AchievementResource {
    private final Map<String, Integer> achievements;
    private final ObjectMapper objectMapper;

    public AchievementResource(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.achievements = new HashMap<>();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAchievements() {
        try {
            return Response.ok(objectMapper.writeValueAsString(achievements)).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error listing achievements").build();
        }
    }

    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addAchievement(String achievementName, Integer points) {
        try {
            achievements.put(achievementName, points);
            return Response.ok("Achievement added").build();
        } catch (Exception e) {
            return Response.serverError().entity("Error adding achievement").build();
        }
    }
}

public class AchievementApplication extends Application<AchievementConfiguration> {
    public static void main(String[] args) throws Exception {
        new AchievementApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AchievementConfiguration> bootstrap) {
        // Initialize any application-wide services here
    }

    @Override
    public void run(AchievementConfiguration configuration, Environment environment) {
        environment.jersey().register(new AchievementResource(objectMapper()));
    }
}

// Configuration class for Dropwizard
public class AchievementConfiguration extends Configuration {
    // Add configuration properties here
}
