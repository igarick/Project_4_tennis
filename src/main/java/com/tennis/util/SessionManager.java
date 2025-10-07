package com.tennis.util;

import com.tennis.exception.ConnectionException;
import com.tennis.exception.ErrorInfo;
import lombok.Getter;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class SessionManager {
    private static final String CONFIG_FILE = "/hibernate.cfg.xml";
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            sessionFactory = configuration
                    .configure(CONFIG_FILE)
                    .buildSessionFactory();
        } catch (Throwable e) {
//            request.setAttribute("errorCode", 500);
//            request.setAttribute("errorMessage", "Ошибка подключения к базе данных");
//            request.getRequestDispatcher("error.jsp").forward(request, response);
            throw new ConnectionException(ErrorInfo.DATABASE_CONNECTION_ERROR, e);
        }
    }
//
//    public static void shutdown() {
//        if (sessionFactory != null) {
//            sessionFactory.close();
//        }
//    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    //    @Getter
//    private static final SessionFactory factory = buildFactory();
//
//    private static SessionFactory buildFactory() {
//        try {
//            return new Configuration().configure(PROPERTY_NAME)
//                    .buildSessionFactory();
//        } catch (HibernateException e) {
//
////            request.setAttribute("errorCode", 500);
////            request.setAttribute("errorMessage", "Ошибка подключения к базе данных");
////            request.getRequestDispatcher("error.jsp").forward(request, response);
//
//
//            throw new RuntimeException(e);
//            // to do
//        }
//    }

}
