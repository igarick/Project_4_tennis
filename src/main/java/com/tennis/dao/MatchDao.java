package com.tennis.dao;

import com.tennis.entity.Match;
import com.tennis.util.SessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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

    public List<Match> findAll(int offset, int limit) {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            Query<Match> selectQuery = session.createQuery("FROM Match ORDER BY ID asc", Match.class);
            selectQuery.setFirstResult(offset);
            selectQuery.setMaxResults(limit);

            List<Match> matches = selectQuery.list();

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

    public List<Match> findByName(String name, int offset, int limit) {
        Session session = null;
        Transaction transaction = null;

        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            Query<Match> selectQuery = session.createQuery("FROM Match m WHERE m.player1.name = :name OR " +
                                                           "m.player2.name = :name", Match.class)
                    .setParameter("name", name);
            selectQuery.setFirstResult(offset);
            selectQuery.setMaxResults(limit);
            List<Match> matches = selectQuery.list();

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

    public Long countMatches() {
        Session session = null;
        Transaction transaction = null;
        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            Query<Long> countQuery = session.createQuery("SELECT count (m.ID) FROM Match m", Long.class);
            Long result = countQuery.uniqueResult();

            transaction.commit();
            return result;
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

    public Long countPlayersByName(String name) {
        Session session = null;
        Transaction transaction = null;

        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            Query<Long> countQuery = session.createQuery("SELECT count (m.ID) FROM Match m " +
                                                         "WHERE m.player1.name = :name OR " +
                                                         "m.player2.name = :name", Long.class)
                    .setParameter("name", name);
            Long result = countQuery.uniqueResult();
            transaction.commit();
            return result;
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
//---------------------- ГПТ вар---------------
//    public Long countId() {
//        String hql = "SELECT count(m.id) FROM Match m";
//        return executeCountQuery(hql, query -> {});
//    }
//
//    public Long countIdByName(String name) {
//        String hql = "SELECT count(m.id) FROM Match m WHERE m.player1.name = :name OR m.player2.name = :name";
//        return executeCountQuery(hql, query -> query.setParameter("name", name));
//    }
//
//    private Long executeCountQuery(String hql, Consumer<Query<?>> parameterSetter) {
//        Session session = null;
//        Transaction transaction = null;
//
//        try {
//            SessionFactory factory = SessionManager.getSessionFactory();
//            session = factory.getCurrentSession();
//            transaction = session.beginTransaction();
//
//            Query<Long> query = session.createQuery(hql, Long.class);
//            parameterSetter.accept(query); // установить параметры, если есть
//
//            Long result = query.uniqueResult();
//            transaction.commit();
//            return result;
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            throw new RuntimeException("Ошибка выполнения count-запроса", e);
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//        }
//    }

}
