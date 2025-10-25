<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tennis Scoreboard | New Match</title>
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
        .form-container {
            margin-top: 20px;
        }
        label {
            display: block;
            margin-top: 10px;
        }
        input[type="text"] {
            width: 300px;
            padding: 5px;
            margin-top: 5px;
        }
        input[type="submit"] {
            margin-top: 15px;
            padding: 8px 16px;
        }
        .error {
            color: red;
            margin-top: 10px;
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
    <h1>Start new match</h1>

    <div class="form-container">
        <form method="post" action="new-match">
<%--            <% String error = (String) request.getAttribute("error"); %>--%>
<%--            <% if (error != null) { %>--%>
<%--            <p class="error"><%= error %></p>--%>
<%--            <% } %>--%>

            <c:if test="${not empty error}">
                <p class="error">"${error}"</p>
            </c:if>

            <label for="playerOne">Player one</label>
            <input type="text" name="playerOne" placeholder="Name">

            <label for="playerTwo">Player two</label>
            <input type="text" name="playerTwo" placeholder="Name">

            <input type="submit" value="Start">
        </form>
    </div>
</div>

<footer>
    <p>&copy; Tennis Scoreboard, project from
        <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
    </p>
</footer>

</body>
</html>

