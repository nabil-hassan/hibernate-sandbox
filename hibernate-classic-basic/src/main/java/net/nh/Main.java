package net.nh;

import net.nh.h2.H2DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class Main {

    private static final String H2_URL = "jdbc:h2:file:/tmp/h2.classic.db;LOCK_MODE=0;DB_CLOSE_DELAY=-1;MODE=Oracle;" +
            "INIT=CREATE SCHEMA IF NOT EXISTS public";
    private static final String USER = "sa", PASS = "sa";

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        H2DataSourceFactory factory = new H2DataSourceFactory();
        factory.createDataSource(H2_URL, USER, PASS);
        while (true) {}
    }


}
