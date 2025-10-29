package com.tennis.util.listener;

import com.tennis.entity.Match;
import com.tennis.entity.Player;
import com.tennis.util.SessionManager;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@WebListener
public class DataInitializer implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Session session = null;
        Transaction transaction = null;

        try {
            SessionFactory factory = SessionManager.getSessionFactory();
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();

            List<Player> players = createPlayers(session);
            createMatches(session, players);

            transaction.commit();
            log.info("Data initialized successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.info("Data initialization error");
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private List<Player> createPlayers(Session session) {
        List<Player> players = List.of(
                new Player(null, "TOM"),
                new Player(null, "JERY"),
                new Player(null, "ROBOCOP"),
                new Player(null, "HOTTY"),
                new Player(null, "PRETTY"),
                new Player(null, "HARRY"),
                new Player(null, "STRANGE"),
                new Player(null, "RICK"),
                new Player(null, "MORTY"),
                new Player(null, "OFFER"));

        for (Player player : players) {
            session.persist(player);
        }
        return players;
    }

    private void createMatches(Session session, List<Player> players) {
        Match match1 = new Match(null, players.get(0), players.get(1), players.get(1));
        Match match2 = new Match(null, players.get(2), players.get(3), players.get(3));
        Match match3 = new Match(null, players.get(3), players.get(2), players.get(2));
        Match match4 = new Match(null, players.get(9), players.get(1), players.get(9));
        Match match5 = new Match(null, players.get(6), players.get(5), players.get(5));
        Match match6 = new Match(null, players.get(8), players.get(7), players.get(7));
        Match match7 = new Match(null, players.get(4), players.get(3), players.get(3));
        Match match8 = new Match(null, players.get(5), players.get(9), players.get(9));
        Match match9 = new Match(null, players.get(0), players.get(6), players.get(6));
        Match match10 = new Match(null, players.get(7), players.get(2), players.get(2));
        Match match11 = new Match(null, players.get(4), players.get(1), players.get(1));
        Match match12 = new Match(null, players.get(8), players.get(3), players.get(8));
        Match match13 = new Match(null, players.get(3), players.get(6), players.get(3));
        Match match14 = new Match(null, players.get(6), players.get(7), players.get(7));
        Match match15 = new Match(null, players.get(0), players.get(4), players.get(4));

        session.persist(match1);
        session.persist(match2);
        session.persist(match3);
        session.persist(match4);
        session.persist(match5);
        session.persist(match6);
        session.persist(match7);
        session.persist(match8);
        session.persist(match9);
        session.persist(match10);
        session.persist(match11);
        session.persist(match12);
        session.persist(match13);
        session.persist(match14);
        session.persist(match15);
    }
}
