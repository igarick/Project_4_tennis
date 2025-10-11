package com.tennis.controller;

import com.tennis.dto.PlayerDto;
import com.tennis.model.*;
import com.tennis.service.NewMatchService;
import com.tennis.service.OngoingMatchesService;
import com.tennis.validator.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {
    private static final String NEW_MATCH_PATH = "new-match.jsp";
    private static final String ERROR_PAGE = "error.jsp";
    private static final NewMatchService newMatchService = new NewMatchService();
    private static final OngoingMatchesService ongoingMatchesService = new OngoingMatchesService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(NEW_MATCH_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String parameter1 = req.getParameter("playerOne");
        String parameter2 = req.getParameter("playerTwo");

        Optional<String> errorMessage = RequestValidator.validateParam(parameter1, parameter2);
        if (errorMessage.isPresent()) {
            req.setAttribute("error", errorMessage.get());
            req.getRequestDispatcher(NEW_MATCH_PATH).forward(req, resp);
            return;
        }

        PlayerDto firstDto = new PlayerDto(null, parameter1);
        PlayerDto secondDto = new PlayerDto(null, parameter2);

        Player firstPlayer = newMatchService.get(firstDto);
        Player secondPlayer = newMatchService.get(secondDto);

        UUID uuid = UUID.randomUUID();

        MatchScoreModel matchScoreModel = new MatchScoreModel(
            new Match(
                    null,
                    firstPlayer,
                    secondPlayer,
                    null
            ),
                new Score(0, 0),
                new Score(0, 0),
                new Score(0, 0)
        );

        ongoingMatchesService.setMatches(uuid, matchScoreModel);

        resp.sendRedirect("match-score?uuid=" + uuid);

        /*
        +++ Создаём экземпляр класса, содержащего
        +++ айди игроков
        +++ и текущий счёт,

        и кладём в коллекцию текущих матчей
        (существующую только в памяти приложения, либо в key-value storage).
        Ключом коллекции является UUID, значением - счёт в матче

        Редирект на страницу /match-score?uuid=$match_id
         */

        System.out.println(firstPlayer);
        System.out.println(secondPlayer);

////        Player player1 = Player.builder().name(firstPlayerName).build();
//
//        SessionFactory factory = SessionManager.getSessionFactory();
//        try {
//            Session session = factory.getCurrentSession();
//            session.beginTransaction();
////            session.persist(player1);
//
//            List<Player> players = session.createQuery("from Player where name in (:names)", Player.class)
//                    .setParameter("names", List.of(firstPlayerName, secondPlayerName))
//                    .getResultList();
//
//            List<String> names = players.stream()
//                    .map(Player::getName)
//                    .toList();
//
//            if (!names.isEmpty()) {
//                String message = "Следующие имена заняты: " + String.join(", ", names);
//                session.getTransaction().commit();
//                req.setAttribute("error", message);
//                req.getRequestDispatcher(NEW_MATCH_PATH).forward(req, resp);
//            }
//
//
//                    System.out.println("///////////////////////////////////");
////            Player saved = session.get(Player.class, 1);
////            System.out.println("Saved player: " + saved);
//            System.out.println("///////////////////////////////////");
//
//            session.getTransaction().commit();
//        } finally {
////            factory.close();
//        }
    }
}

