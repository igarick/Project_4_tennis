package com.tennis.dao;

import com.tennis.dto.PlayersNamesDto;
import com.tennis.model.Player;
import com.tennis.util.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class NewMatchDao {

    public Optional<Player> findByName(String name) {
        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            Player player = session.createQuery("from Player where name = :name", Player.class)
                    .setParameter("name", name)
                    .getSingleResult();

            session.getTransaction().commit();
            return Optional.of(player);
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
    }

    public Player save(String name) {
        SessionFactory factory = SessionManager.getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            Player player = Player.builder().name(name).build();
            session.persist(player);

            session.getTransaction().commit();
            return player;
        } catch (HibernateException e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Ошибка при сохранении игрока", e);
        }
    }

//    public Optional<String> save(PlayersNamesDto dto) {
//        SessionFactory factory = SessionManager.getSessionFactory();
//        try {
//            Session session = factory.getCurrentSession();
//            session.beginTransaction();
//
//            List<Player> players = session.createQuery("from Player where name in (:names)", Player.class)
//                    .setParameter("names", List.of(dto.first(), dto.second()))
//                    .getResultList();
//
//            List<String> names = players.stream()
//                    .map(Player::getName)
//                    .toList();
//
//            if (!names.isEmpty()) {
//                String message = "Следующие имена заняты: " + String.join(", ", names);
//                return Optional.of(message);
//            }
//
//            Player player1 = Player.builder().name(dto.first()).build();
//            Player player2 = Player.builder().name(dto.second()).build();
//
//            session.persist(player1);
//            session.persist(player2);
//
//
//            session.getTransaction().commit();
//        } finally {
//        }
//        return Optional.empty();
//    }
}

