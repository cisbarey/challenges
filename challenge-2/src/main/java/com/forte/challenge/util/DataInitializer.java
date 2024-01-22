package com.forte.challenge.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final DataSource dataSource;

    public DataInitializer(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try (Connection conn = this.dataSource.getConnection()) {
            Statement stmt = conn.createStatement();

            stmt.execute("INSERT INTO room (id, name) VALUES (1, 'Habitación 101')");
            stmt.execute("INSERT INTO room (id, name) VALUES (2, 'Habitación 102')");
            stmt.execute("INSERT INTO room (id, name) VALUES (3, 'Habitación 103')");
            stmt.execute("INSERT INTO room (id, name) VALUES (4, 'Habitación 104')");
            stmt.execute("INSERT INTO room (id, name) VALUES (5, 'Habitación 105')");
            stmt.execute("INSERT INTO room (id, name) VALUES (6, 'Habitación 106')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
