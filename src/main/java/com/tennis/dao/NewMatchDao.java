package com.tennis.dao;

import com.tennis.dto.PlayersNamesDto;
import com.tennis.model.Player;
import com.tennis.util.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class NewMatchDao {

    public List<Player> findByName(String name) {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            List<Player> players = session.createQuery("from Player where name = :name", Player.class)
                    .setParameter("name", name)
                    .getResultList();

            transaction.commit();
            return players;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return List.of();
    }

    public Player save(String name) {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            Player player = Player.builder().name(name).build();
            session.persist(player);

            transaction.commit();
            return player;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при сохранении игрока", e);
        } finally {
            if (session != null) {
                session.close();
            }
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

