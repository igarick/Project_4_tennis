package com.tennis.util;

import com.tennis.entity.Match;
import com.tennis.entity.Player;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

            Player player1 = new Player(null, "TOM");
            Player player2 = new Player(null, "JERY");
            Player player3 = new Player(null, "ROBOCOP");
            Player player4 = new Player(null, "HOTTY");
            Player player5 = new Player(null, "PRETTY");
            Player player6 = new Player(null, "HARRY");
            Player player7 = new Player(null, "STRANGE");
            Player player8 = new Player(null, "RICK");
            Player player9 = new Player(null, "MORTY");
            Player player10 = new Player(null, "TURN OFFER");
//            Player player11 = new Player(null, "VSPISH");
//            Player player12 = new Player(null, "TREE");
//            Player player13 = new Player(null, "MS DOOR");
//            Player player14 = new Player(null, "PAM PARA");
//            Player player15 = new Player(null, "PAM");

            session.persist(player1);
            session.persist(player2);
            session.persist(player3);
            session.persist(player4);
            session.persist(player5);
            session.persist(player6);
            session.persist(player7);
            session.persist(player8);
            session.persist(player9);
            session.persist(player10);

            Match match1 = new Match(null, player1, player2, player2);
            Match match2 = new Match(null, player3, player4, player4);
            Match match3 = new Match(null, player4, player3, player3);
            Match match4 = new Match(null, player10, player2, player10);
            Match match5 = new Match(null, player7, player6, player6);
            Match match6 = new Match(null, player9, player8, player8);
            Match match7 = new Match(null, player5, player4, player4);
            Match match8 = new Match(null, player6, player10, player10);
            Match match9 = new Match(null, player1, player7, player7);
            Match match10 = new Match(null, player8, player3, player3);
            Match match11 = new Match(null, player5, player2, player2);
            Match match12 = new Match(null, player9, player4, player9);
            Match match13 = new Match(null, player4, player7, player4);
            Match match14 = new Match(null, player7, player8, player8);
            Match match15 = new Match(null, player1, player5, player5);

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

            transaction.commit();
            log.info("Data initialized successfully");
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
                log.info("Data initialization error");
            }
            throw new RuntimeException(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
