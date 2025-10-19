package com.tennis;

import com.tennis.entity.Player;
import com.tennis.util.SessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

public class HibernateRunner {
    public static void main(String[] args) {
        Player player1 = Player.builder().name("кuuu").build();

        SessionFactory factory = SessionManager.getSessionFactory();


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
        } catch (ConstraintViolationException e) {

//            request.setAttribute("errorCode", 500);
//            request.setAttribute("errorMessage", "Ошибка подключения к базе данных");
//            request.getRequestDispatcher("error.jsp").forward(request, response);

        } finally {
            factory.close();
        }
    }
}
