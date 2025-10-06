package com.tennis.controller;

import com.tennis.model.Player;
import com.tennis.util.SessionManager;
import com.tennis.validator.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private static final String NEW_MATCH_PATH = "new-match.jsp";
    private static final String ERROR_PAGE = "error.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(NEW_MATCH_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameFirstPlayer = req.getParameter("playerOne");
        String nameSecondPlayer = req.getParameter("playerTwo");

//        Optional<String> errorMessage = RequestValidator.validateParam(nameFirstPlayer, nameSecondPlayer);
//        if (errorMessage.isPresent()) {
//            req.setAttribute("error", errorMessage.get());
//            req.getRequestDispatcher(NEW_MATCH_PATH).forward(req, resp);
//            return;
//        }


//        Player player1 = Player.builder().name("кuuu").build();

//        Player player1 = Player.builder().name(nameFirstPlayer).build();
//        Player player2 = Player.builder().name(nameSecondPlayer).build();

        SessionFactory factory = SessionManager.getFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
//            session.persist(player1);


            Player name = session.createQuery("from Player where name = :name", Player.class)
                    .setParameter("name", nameFirstPlayer)
                    .getSingleResult();

            if(name != null) {
                req.setAttribute("errorCode", 500);
                req.setAttribute("errorMessage", "Ошибка подключения к базе данных");
                req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);
                return;
            }

            System.out.println("///////////////////////////////////");
//            Player saved = session.get(Player.class, 1);
//            System.out.println("Saved player: " + saved);
            System.out.println("///////////////////////////////////");

            session.getTransaction().commit();
        } catch (ConstraintViolationException e) {

            req.setAttribute("errorCode", 500);
            req.setAttribute("errorMessage", "Ошибка подключения к базе данных");
            req.getRequestDispatcher(ERROR_PAGE).forward(req, resp);

        } finally {
            factory.close();
        }
    }
}

