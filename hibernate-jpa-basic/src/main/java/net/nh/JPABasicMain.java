package net.nh;

import net.nh.entity.Message;
import net.nh.h2.H2DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.sql.SQLException;

public class JPABasicMain {

    private static final String H2_URL = "jdbc:h2:file:/tmp/h2.jpa.basic.db;LOCK_MODE=0;DB_CLOSE_DELAY=-1;MODE=Oracle;" +
            "INIT=CREATE SCHEMA IF NOT EXISTS public";
    private static final String USER = "sa", PASS = "sa";

    private static final Logger LOG = LoggerFactory.getLogger(JPABasicMain.class);

    public static void main (String[] args) throws SQLException {
        LOG.info("Starting application");

        new H2DataSourceFactory().createDataSource(H2_URL, USER, PASS);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-basic-persistence-unit");

        // First unit of work
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Message message = new Message();
        message.setText("Hello world");
        em.persist(message);
        tx.commit();
        em.close();

        LOG.info("Done");
    }


}
