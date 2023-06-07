package net.nh;

import net.nh.domain.Customer;
import net.nh.h2.H2DataSourceFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.concurrent.Executors;

public class Main {

    private static final String H2_URL = "jdbc:h2:file:/tmp/h2.classic.db;LOCK_MODE=0;DB_CLOSE_DELAY=-1;MODE=Oracle;" +
            "INIT=CREATE SCHEMA IF NOT EXISTS public";
    private static final String USER = "sa", PASS = "sa";

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        H2DataSourceFactory factory = new H2DataSourceFactory();
        factory.createDataSource(H2_URL, USER, PASS);

        LOG.info("Create Session Factory");
        SessionFactory sf = new Configuration().addResource("hbm/customer.hbm.xml").buildSessionFactory();

        Customer c = new Customer("Bob", "Jones");
        Session session = null;
        Transaction tx = null;
        try {
            LOG.info("Save customer");
            session = sf.openSession();
            tx = session.beginTransaction();
            session.save(c);
            tx.commit();
            LOG.info("Customer saved successfully");
        } catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
            }
            ex.printStackTrace();
            LOG.error("Unable to persist customer", ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        Executors.newFixedThreadPool(1);
    }

}
