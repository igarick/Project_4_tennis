<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>Tennis Scoreboard | Match Finished</title>
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
      margin-top: 30px;
      text-align: center;
    }

    .winner-banner {
      background-color: #e0ffe0;
      border: 1px solid #8bc34a;
      padding: 20px;
      font-size: 22px;
      font-weight: bold;
      color: #2e7d32;
      margin-bottom: 30px;
    }

    table {
      margin: 0 auto;
      border-collapse: collapse;
      width: 60%;
    }

    th, td {
      border: 1px solid #ccc;
      padding: 10px;
    }

    th {
      background-color: #f9f9f9;
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
  <div class="winner-banner">
    🎉 Winner: <%= request.getAttribute("winnerName") %>
  </div>

  <table>
    <thead>
    <tr>
      <th>Player</th>
      <th>Sets</th>
      <th>Games</th>
      <th>Points</th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td><%= request.getAttribute("firstPlayerName") %></td>
      <td><%= request.getAttribute("setScoreFirstPlayer") %></td>
      <td><%= request.getAttribute("gameScoreFirstPlayer") %></td>
      <td><%= request.getAttribute("pointScoreFirstPlayer") %></td>
    </tr>
    <tr>
      <td><%= request.getAttribute("secondPlayerName") %></td>
      <td><%= request.getAttribute("setScoreSecondPlayer") %></td>
      <td><%= request.getAttribute("gameScoreSecondPlayer") %></td>
      <td><%= request.getAttribute("pointScoreSecondPlayer") %></td>
    </tr>
    </tbody>
  </table>
</div>

</body>
</html>

