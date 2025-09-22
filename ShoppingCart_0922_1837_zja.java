// 代码生成时间: 2025-09-22 18:37:05
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
import java.util.ArrayList;
import java.util.List;

@Path("/cart")
public class ShoppingCartResource {
    private final List<String> cartItems;

    public ShoppingCartResource() {
        this.cartItems = new ArrayList<>();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartItems() {
        return Response.ok(cartItems).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/add")
    public Response addItem(String item) {
        try {
            cartItems.add(item);
            return Response.ok().entity("Item added to cart: " + item).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error adding item to cart: " + e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/remove")
    public Response removeItem(String item) {
        try {
            cartItems.remove(item);
            return Response.ok().entity("Item removed from cart: " + item).build();
        } catch (Exception e) {
            return Response.serverError().entity("Error removing item from cart: " + e.getMessage()).build();
        }
    }
}

public class ShoppingCartApplication extends Application<ShoppingCartConfiguration> {
    @Override
    public void initialize(Bootstrap<ShoppingCartConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<ShoppingCartConfiguration>()
                .setRenderer(new FreemarkerViewRenderer())
                .setRenderer(new MustacheViewRenderer()));
    }

    @Override
    public void run(ShoppingCartConfiguration configuration, Environment environment) throws Exception {
        environment.jersey().register(new ShoppingCartResource());
    }
}
