package com.tennis.util;

import com.tennis.exception.ConnectionException;
import com.tennis.exception.ErrorInfo;
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
            throw new ConnectionException(ErrorInfo.DATABASE_CONNECTION_ERROR, e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
