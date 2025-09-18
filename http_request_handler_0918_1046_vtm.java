// 代码生成时间: 2025-09-18 10:46:17
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer;
import io.dropwizard.views.mustache.MustacheViewRenderer.Default.MustacheLayout;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class HttpRequestHandler {

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello, World!";
    }

    @POST
    @Path("/greet")
    @Produces(MediaType.TEXT_PLAIN)
    public Response greet(String name) {
        if (name == null || name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Name cannot be empty").build();
        }
        return Response.ok("Hello, " + name).build();
    }
}

public class MyApplication extends Application<MyConfiguration> {
    public static void main(String[] args) throws Exception {
        new MyApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<MyConfiguration> bootstrap) {
        // Initialize any application components here.
        bootstrap.addBundle(new ViewBundle<>()
            .setRenderer(new FreemarkerViewRenderer())
            .setRenderer(new MustacheViewRenderer.Default("{{