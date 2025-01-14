package io.reactivestax.spring_boot_app.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class AppConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        // Override default HikariCP properties
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/employee_db"); // Database URL
        hikariConfig.setUsername("postgres"); // Database username
        hikariConfig.setPassword("postgres22"); // Database password
        hikariConfig.setMaximumPoolSize(10); // Maximum number of connections in the pool
        hikariConfig.setMinimumIdle(5); // Minimum number of idle connections
        hikariConfig.setIdleTimeout(30000); // Idle timeout (in milliseconds)
        hikariConfig.setConnectionTimeout(20000); // Connection timeout (in milliseconds)
        // hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver"); // JDBC Driver

        return new HikariDataSource(hikariConfig);
    }
}
