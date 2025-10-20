package com.tennis.dao;

import com.tennis.entity.Match;
import com.tennis.util.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class MatchDao {
    public void save(Match match) {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            session.persist(match);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при сохранении матча", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Match> findAll() {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            List<Match> matches = session.createQuery("from Match", Match.class)
                    .list();

            transaction.commit();

            return matches;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка получения матчей", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    public List<Match> findByName(String name) {
        Session session = null;
        Transaction transaction = null;

        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            List<Match> matches = session.createQuery("FROM Match m WHERE m.player1.name = :name OR " +
                                "m.player2.name = :name", Match.class)
                    .setParameter("name", name)
                    .list();

            transaction.commit();
            return matches;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка получения матчей по фильтру", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
