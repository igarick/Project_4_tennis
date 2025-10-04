package com.tennis;

import com.tennis.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateRunner {
    public static void main(String[] args) {
//        Player player = new Player(, "Qee");
//        Player player2 = new Player(1, "Qee");

//        Player player1 = Player.builder().name("www").build();
        Player player1 = Player.builder().name("кен").build();

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();

        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
            session.persist(player1);
//            session.persist(player2);

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
