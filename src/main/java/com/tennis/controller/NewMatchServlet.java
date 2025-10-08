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

import java.io.IOException;
import java.util.List;
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

        Optional<String> errorMessage = RequestValidator.validateParam(nameFirstPlayer, nameSecondPlayer);
        if (errorMessage.isPresent()) {
            req.setAttribute("error", errorMessage.get());
            req.getRequestDispatcher(NEW_MATCH_PATH).forward(req, resp);
            return;
        }

//        Player player1 = Player.builder().name(nameFirstPlayer).build();

        SessionFactory factory = SessionManager.getSessionFactory();
        try {
            Session session = factory.getCurrentSession();
            session.beginTransaction();
//            session.persist(player1);

            List<Player> players = session.createQuery("from Player where name in (:names)", Player.class)
                    .setParameter("names", List.of(nameFirstPlayer, nameSecondPlayer))
                    .getResultList();

            List<String> names = players.stream()
                    .map(Player::getName)
                    .toList();

            if (!names.isEmpty()) {
                String message = "Следующие имена заняты: " + String.join(", ", names);
                session.getTransaction().commit();
                req.setAttribute("error", message);
                req.getRequestDispatcher(NEW_MATCH_PATH).forward(req, resp);
            }


                    System.out.println("///////////////////////////////////");
//            Player saved = session.get(Player.class, 1);
//            System.out.println("Saved player: " + saved);
            System.out.println("///////////////////////////////////");

            session.getTransaction().commit();
        } finally {
//            factory.close();
        }
    }
}

