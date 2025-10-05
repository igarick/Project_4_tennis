package com.tennis.controller;

import com.tennis.validator.RequestValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/new-match")
public class NewMatchServlet extends HttpServlet {
    private static final String NEW_MATCH_PATH = "new-match.jsp";
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
        }


        System.out.println(nameFirstPlayer);
        System.out.println(nameSecondPlayer);
    }
}
