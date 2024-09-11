package com.game.repository;

import com.game.entity.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.Environment.*;

@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {
    private final SessionFactory sessionFactory;

    public PlayerRepositoryDB() {
        Properties properties = new Properties();
//        properties.put(URL, "jdbc:mysql://localhost:3306/rpg");
        properties.put(DIALECT, "org.hibernate.dialect.MySQL8Dialect");
//        properties.put(DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(USER, "root");
        properties.put(PASS, "root");
        properties.put(HBM2DDL_AUTO, "update");
        properties.put(SHOW_SQL, true);
        properties.put(DRIVER, "com.p6spy.engine.spy.P6SpyDriver");
        properties.put(URL, "jdbc:p6spy:mysql://localhost:3306/rpg");

        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.p6spy.engine.spy.P6SpyDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        sessionFactory = new Configuration()
                .addAnnotatedClass(Player.class)
                .addProperties(properties)
                .buildSessionFactory();
    }

    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {
        try (Session session = sessionFactory.openSession()) {
            if (pageNumber - 1 > 0) {
                NativeQuery<Player> query = session.createNativeQuery("SELECT * FROM player", Player.class);
                query.setFirstResult((pageNumber - 1) * pageSize);
                query.setMaxResults(pageSize);

                return query.getResultList();
            }

            return Collections.emptyList();
        }
    }

    @Override
    public int getAllCount() {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createNamedQuery("getAllPlayers", Long.class);
            return Math.toIntExact(query.uniqueResult());
        }
    }

    @Override
    public Player save(Player player) {
        return null;
    }

    @Override
    public Player update(Player player) {
        return null;
    }

    @Override
    public Optional<Player> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Player player) {

    }

    @PreDestroy
    public void beforeStop() {
        sessionFactory.close();
    }
}