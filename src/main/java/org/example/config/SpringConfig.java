package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.DriverManager;

@Configuration
@ComponentScan(basePackages = "org.example")
public class SpringConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource= new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/Atto");
        dataSource.setUsername("postgres");
        dataSource.setPassword("toshiba1990");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate= new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }
}
