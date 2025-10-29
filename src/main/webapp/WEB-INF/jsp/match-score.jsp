<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tennis Scoreboard | Match Score</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }

        header, footer {
            background-color: #f0f0f0;
            padding: 10px;
        }

        nav a {
            margin-right: 15px;
            text-decoration: none;
            color: #333;
        }

        .container {
            margin-top: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }

        .score-btn {
            padding: 6px 12px;
        }
    </style>
</head>
<body>

<header>
    <h2>TennisScoreboard</h2>
    <nav>
        <a href="/">Home</a>
        <a href="matches">Matches</a>
    </nav>
</header>

<div class="container">
    <h1>Current match</h1>

    <table>
        <thead>
        <tr>
            <th>Player</th>
            <th>Sets</th>
            <th>Games</th>
            <th>Points</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>${firstPlayerName}</td>
            <td>${setScoreFirstPlayer}</td>
            <td>${gameScoreFirstPlayer}</td>
            <td>${pointScoreFirstPlayer}</td>
            <td>
                <form method="post" action="match-score?uuid=${matchUuid}">
                    <input type="hidden" name="firstPlayerId" value="${firstPlayerId}"/>
                    <input type="hidden" name="tieBreak" value="${isTieBreak}">
                    <button type="submit" class="score-btn">Score</button>
                </form>
            </td>
        </tr>
        <tr>
            <td>${secondPlayerName}</td>
            <td>${setScoreSecondPlayer}</td>
            <td>${gameScoreSecondPlayer}</td>
            <td>${pointScoreSecondPlayer}</td>
            <td>
                <form method="post" action="match-score?uuid=${matchUuid}">
                    <input type="hidden" name="secondPlayerId" value="${secondPlayerId}">
                    <input type="hidden" name="tieBreak" value="${isTieBreak}">
                    <button type="submit" class="score-btn">Score</button>
                </form>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<footer>
    <p>&copy; Tennis Scoreboard, project from
        <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
    </p>
</footer>

</body>
</html>

