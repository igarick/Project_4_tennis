package com.tennis.util;

import lombok.Getter;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@Getter
public class SessionManager {
    private static final String PROPERTY_NAME = "hibernate.cfg.xml";

    @Getter
    private static final SessionFactory factory = buildFactory();

    private static SessionFactory buildFactory() {
        try {
            return new Configuration().configure(PROPERTY_NAME)
                    .buildSessionFactory();
        } catch (HibernateException e) {

//            request.setAttribute("errorCode", 500);
//            request.setAttribute("errorMessage", "Ошибка подключения к базе данных");
//            request.getRequestDispatcher("error.jsp").forward(request, response);


            throw new RuntimeException(e);
            // to do
        }
    }

}
