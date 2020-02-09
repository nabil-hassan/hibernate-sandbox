package net.nh.h2;

import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class H2DataSourceFactory {

    private static final Logger LOG = LoggerFactory.getLogger(H2DataSourceFactory.class);

    private Server webServer;

    public DataSource createDataSource(String url, String user, String password) throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL(url);
        ds.setUser(user);
        ds.setPassword(password);

        // Test connectivity...
        Connection connection = null;
        try {
            connection = ds.getConnection();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        LOG.info("Created data source with URL: {}", url);


        webServer = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8091");
        webServer.start();
        String shortUrl = url.substring(0, url.indexOf(";"));
        LOG.info("Web server started - visit http://localhost:8091 and use db url: {}", shortUrl);

        return ds;
    }

    public void stopServer() {
        if (webServer != null) {
            webServer.stop();
        }
    }

}
