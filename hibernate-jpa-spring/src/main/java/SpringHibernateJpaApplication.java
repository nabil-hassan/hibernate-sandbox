import dao.ItemDao;
import model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringHibernateJpaApplication {

    private static final Logger LOG = LoggerFactory.getLogger(SpringHibernateJpaApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting application context");

        ApplicationContext context = new AnnotationConfigApplicationContext("config");
        LOG.info("Application context was started successfully");

        Item item = new Item();
        item.setCode("FA0001");
        item.setDescription("Full armor");

        ItemDao dao = context.getBean(ItemDao.class);
        dao.persist(item);
        LOG.info("Persisted item: {}", item);
    }
}
