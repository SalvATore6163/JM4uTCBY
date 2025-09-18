// 代码生成时间: 2025-09-19 06:53:34
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.View;
import io.dropwizard.views.ViewsBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/cart")
public class ShoppingCartResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartResource.class);
    private final Map<String, Integer> cart = new HashMap<>(); // Maps item IDs to quantities

    public ShoppingCartResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCart() {
        return Response.ok(cart).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItem(String itemId) {
        try {
            if (itemId == null || itemId.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Item ID cannot be empty").build();
            }
            int quantity = cart.getOrDefault(itemId, 0) + 1;
            cart.put(itemId, quantity);
            return Response.ok("Item added to cart").build();
        } catch (Exception e) {
            LOGGER.error("Error adding item to cart", e);
            return Response.serverError().entity("Error adding item to cart").build();
        }
    }

    @POST
    @Path("/remove")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeItem(String itemId) {
        try {
            if (itemId == null || itemId.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Item ID cannot be empty").build();
            }
            if (cart.containsKey(itemId)) {
                int quantity = cart.get(itemId);
                if (quantity > 1) {
                    cart.put(itemId, quantity - 1);
                } else {
                    cart.remove(itemId);
                }
                return Response.ok("Item removed from cart").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Item not found in cart").build();
            }
        } catch (Exception e) {
            LOGGER.error("Error removing item from cart", e);
            return Response.serverError().entity("Error removing item from cart\).build();
        }
    }
}

public class ShoppingCartApp extends Application<ShoppingCartConfig> {
    public static void main(String[] args) throws Exception {
        new ShoppingCartApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<ShoppingCartConfig> bootstrap) {
        // Initialize any additional configurations here
    }

    @Override
    public void run(ShoppingCartConfig configuration, Environment environment) {
        environment.jersey().register(new ShoppingCartResource());
        environment.jersey().register(new ViewsBundle());
    }
}
