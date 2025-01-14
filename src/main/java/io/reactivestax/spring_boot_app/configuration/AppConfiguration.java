package io.reactivestax.spring_boot_app.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class AppConfiguration {

    //this annotation will ensure this bean does not get created
    //if you are running your app in any profile other than PROD
    @Profile("prod")
     @Bean
     public DataSource dataSource() {
         HikariConfig hikariConfig = new HikariConfig();

         // Override default HikariCP properties
         hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5430/employee_db00"); // Database URL
         hikariConfig.setUsername("postgres00"); // Database username
         hikariConfig.setPassword("postgres00"); // Database password
         hikariConfig.setMaximumPoolSize(10); // Maximum number of connections in the pool
         hikariConfig.setMinimumIdle(5); // Minimum number of idle connections
         hikariConfig.setIdleTimeout(30000); // Idle timeout (in milliseconds)
         hikariConfig.setConnectionTimeout(20000); // Connection timeout (in milliseconds)
         // hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver"); // JDBC Driver

         return new HikariDataSource(hikariConfig);
     }
}
