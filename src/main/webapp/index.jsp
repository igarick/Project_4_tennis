<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tennis Scoreboard | Home</title>
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
        .button-link {
            display: inline-block;
            margin: 10px 0;
        }
        .button-link button {
            padding: 8px 16px;
            font-size: 14px;
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
    <h1>Welcome to Tennis Scoreboard</h1>
    <p>Manage your tennis matches, record results, and track rankings.</p>

    <div>
        <div class="button-link">
            <a href="new-match">
                <button>Start a new match</button>
            </a>
        </div>
        <div class="button-link">
            <a href="error">
                <button>View match results</button>
            </a>
        </div>
    </div>
</div>

<%--<footer>--%>
<%--    <p>&copy; Tennis Scoreboard, project from--%>
<%--        <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>--%>
<%--    </p>--%>
<%--</footer>--%>

</body>
</html>
