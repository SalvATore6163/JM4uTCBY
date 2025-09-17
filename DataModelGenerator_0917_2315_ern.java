// 代码生成时间: 2025-09-17 23:15:49
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.SessionFactory;
import io.dropwizard.views.ViewBundle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.UUID;

// 数据模型类
class DataModel {
    private UUID id;
    private String name;
    private String description;

    public DataModel() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

// REST资源类
@Path("/datamodels")
public class DataModelResource {

    private final SessionFactory sessionFactory;

    public DataModelResource(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<DataModel> list() {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            List<DataModel> models = session.createQuery("FROM DataModel", DataModel.class).list();
            session.getTransaction().commit();
            return models;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw new RuntimeException("Error retrieving data models", e);
        } finally {
            session.close();
        }
    }
}

// Dropwizard应用程序类
public class DataModelGenerator extends Application<DataModelGeneratorConfiguration> {
    private final HibernateBundle<DataModelGeneratorConfiguration> hibernateBundle = new HibernateBundle<<DataModelGeneratorConfiguration>() {

        @Override
        public SessionFactory getSessionFactory(DataModelGeneratorConfiguration configuration) {
            return configuration.getSessionFactory();
        }
    };

    public static void main(String[] args) throws Exception {
        new DataModelGenerator().run(args);
    }

    @Override
    public void initialize(Bootstrap<DataModelGeneratorConfiguration> bootstrap) {
        // 初始化配置类
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new ViewBundle<<DataModelGeneratorConfiguration>()));
    }

    @Override
    public void run(DataModelGeneratorConfiguration configuration, Environment environment) {
        // 配置REST资源
        environment.jersey().register(new DataModelResource(hibernateBundle.getSessionFactory()));
    }
}
