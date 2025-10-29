package com.tennis.util.listener;

import com.tennis.dao.MatchDao;
import com.tennis.dao.PlayerDao;
import com.tennis.service.*;
import com.tennis.service.point.PointIncrementRule;
import com.tennis.service.victory.GamesVictory;
import com.tennis.service.victory.PointsVictoryAndAdvantage;
import com.tennis.service.victory.SetsVictory;
import com.tennis.service.victory.TieBreak;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("ongoingMatchesService", new OngoingMatchesService());
        sce.getServletContext().setAttribute("matchScoreService", new MatchScoreService(
                new MatchScoreCalculationService(
                        new PointIncrementRule(),
                        new FinishedMatchesPersistenceService(new MatchDao()),
                        new SetsVictory(),
                        new GamesVictory(),
                        new PointsVictoryAndAdvantage(),
                        new TieBreak()
                )));
        sce.getServletContext().setAttribute("matchService", new MatchService(
                new FinishedMatchesPersistenceService(new MatchDao()),
                new MatchDao()
        ));
        sce.getServletContext().setAttribute("newMatchService", new NewMatchService(
                new PlayerDao()
        ));
    }
}
