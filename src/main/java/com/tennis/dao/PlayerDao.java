package com.tennis.dao;

import com.tennis.entity.Player;
import com.tennis.exception.DaoException;
import com.tennis.exception.ErrorInfo;
import com.tennis.util.SessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlayerDao {

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
            throw new DaoException(ErrorInfo.RETRIEVING_FROM_DATABASE_FAILED, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
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
            throw new DaoException(ErrorInfo.SAVING_TO_DATABASE_FAILED, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

