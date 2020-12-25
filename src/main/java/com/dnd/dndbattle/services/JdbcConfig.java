package com.dnd.dndbattle.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;


@Component
public class JdbcConfig {

    @Value("${datasource.url}")
    private String dndurl;


    private static final Logger logger = LoggerFactory.getLogger(JdbcConfig.class);

    @Bean(name = "ats")
    public void atsDataSource() {
        logger.info("Creating atsDataSource bean");
        connect();
        /*return new JdbcTemplate(createDataSource(atsDatasourceHost, atsDatasourcePort,
                atsDatasourceUsername, atsDatasourcePassword, atsDbName));*/
    }

    @Bean(name = "dnddb")
    public JdbcTemplate atsDataSource2() {
        logger.info("Creating atsDataSource bean");
        connect();
        JdbcTemplate aa = new JdbcTemplate(createDataSource());


        return aa;
    }

    private void connect(){
        Connection conn = null;
        try {
            // db parameters
            String url = dndurl;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    private DataSource createDataSource() {
        //final String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("org.sqlite.JDBC");
        basicDataSource.setUrl(dndurl);
        /*basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);*/
        basicDataSource.setMaxActive(1);
        return basicDataSource;
    }


}
