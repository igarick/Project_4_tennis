package com.tennis;

import com.tennis.model.Player;
import com.tennis.util.SessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {
    public static void main(String[] args) {
        Player player1 = Player.builder().name("кuuu").build();

        SessionFactory factory = SessionManager.getFactory();


//        SessionFactory factory = new Configuration()
//                .configure("hibernate.cfg.xml")
////                .addAnnotatedClass(Player.class)
//                .buildSessionFactory();

        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.persist(player1);

            System.out.println("///////////////////////////////////");
//            Player saved = session.get(Player.class, 1);
//            System.out.println("Saved player: " + saved);
            System.out.println("///////////////////////////////////");

            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}
