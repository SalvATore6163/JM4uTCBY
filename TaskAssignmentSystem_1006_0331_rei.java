// 代码生成时间: 2025-10-06 03:31:21
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/tasks")
public class TaskAssignmentResource {
    private Map<String, String> tasks = new HashMap<>();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTasks() {
        return Response.ok(tasks).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAndAssignTask(String task, String assignee) {
        if (task == null || assignee == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Task and assignee cannot be null.").build();
        }
        String taskId = UUID.randomUUID().toString();
        tasks.put(taskId, task + " assigned to " + assignee);
        return Response.ok(taskId).build();
    }
}

public class TaskAssignmentSystem extends Application<TaskAssignmentConfig> {
    @Override
    public void initialize(Bootstrap<TaskAssignmentConfig> bootstrap) {
        // Initialize any additional components here
        bootstrap.addBundle(new ViewBundle<>(){
            @Override
            public void configure(ViewsConfiguration configuration) {
                configuration.setRenderer(new FreemarkerViewRenderer());
            }
        });
    }

    @Override
    public void run(TaskAssignmentConfig configuration, Environment environment) throws Exception {
        environment.jersey().register(new TaskAssignmentResource());
    }

    public static void main(String[] args) throws Exception {
        new TaskAssignmentSystem().run(args);
    }
}
