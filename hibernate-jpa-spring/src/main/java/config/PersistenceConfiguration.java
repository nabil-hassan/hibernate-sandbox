package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "dao")
public class PersistenceConfiguration {

    @Value("${data.source.url}")
    private String dataSourceUrl;

    @Value("${data.source.user}")
    private String dataSourceUser;

    @Value("${data.source.password}")
    private String dataSourcePassword;

    @Value("${data.source.min.pool.size:5}")
    private int minPoolSize;

    @Value("${data.source.max.pool.size:20}")
    private int maxPoolSize;

    @Value("${data.source.connection.test.period:3000}")
    private int connectionTestPeriod;

    @Bean
    public DataSource h2PooledDataSource() {
        ComboPooledDataSource ds = new ComboPooledDataSource();
        ds.setJdbcUrl(dataSourceUrl);
        ds.setUser(dataSourceUser);
        ds.setPassword(dataSourcePassword);
        ds.setMinPoolSize(minPoolSize);
        ds.setMaxPoolSize(maxPoolSize);
        ds.setIdleConnectionTestPeriod(connectionTestPeriod);
        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        // N.B. 1 - this adapter only allows a handful of properties
        // If you require additional Hibernate properties, you need to specify them in the entity manager factory

        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setGenerateDdl(true);
        adapter.setShowSql(true);
        //N.B. if you use a db mode with the H2 database, you need to change to the dialect accordingly
        // e.g. for Oracle mode, you would use an Oracle 10g dialect (I think)....
        adapter.setDatabasePlatform("org.hibernate.dialect.H2Dialect");
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JpaVendorAdapter jpaVendorAdapter,
                                                                       DataSource h2DataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(h2DataSource);
        entityManagerFactory.setPackagesToScan("model");
        entityManagerFactory.setJpaDialect(new HibernateJpaDialect());
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);

        // Add any other Hibernate or JPA properties we require, that are not covered by the vendor adapter.
        // Show SQL is just used as an example, since it is already covered by the vendor adapter...
        Properties hibernateAndJpaProperties = new Properties();
//        hibernateAndJpaProperties.put("hibernate.show_sql", "true");
        entityManagerFactory.setJpaProperties(hibernateAndJpaProperties);
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory,
                                                    DataSource h2DataSource) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        transactionManager.setDataSource(h2DataSource);
        return transactionManager;
    }
}