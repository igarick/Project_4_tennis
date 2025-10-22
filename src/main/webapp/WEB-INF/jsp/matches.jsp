<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Tennis Scoreboard | Finished Matches</title>
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

        .input-container {
            margin-bottom: 15px;
        }

        .input-filter {
            padding: 6px;
            width: 200px;
        }

        .btn-filter {
            padding: 6px 12px;
            margin-left: 10px;
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

        .pagination {
            margin-top: 20px;
        }

        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            color: #333;
        }

        .current {
            font-weight: bold;
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
    <h1>Matches</h1>

    <div class="input-container">
        <form method="get" action="matches">
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
            <p class="error"><%= error %>
            </p>
            <% } %>
            <input class="input-filter" placeholder="Filter by name" type="text"
                   name="filter_by_player_name" value="${paramFilter}"/>
            <button type="submit" class="btn-filter">Apply</button>
            <div>
                <a href="matches">
                    <button type="button" class="btn-filter">Reset Filter</button>
                </a>
            </div>
        </form>
        <%--    <form method="get" action="match-finished">--%>
        <%--      <input class="input-filter" type="text" name="filter" placeholder="Filter by name" value="${param.filter}" />--%>
        <%--      <button type="submit" class="btn-filter">Apply</button>--%>
        <%--      <a href="match-finished"><button type="button" class="btn-filter">Reset</button></a>--%>
        <%--    </form>--%>
    </div>

    <table>
        <thead>
        <tr>
            <th>Player One</th>
            <th>Player Two</th>
            <th>Winner</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="match" items="${matches}">
            <tr>
                <td>${match.player1.name}</td>
                <td>${match.player2.name}</td>
                <td><span class="winner-name-td">${match.winner.name}</span></td>
            </tr>
        </c:forEach>
        <%-- Здесь ты можешь использовать обычный for или request.getAttribute("finishedMatches") --%>
        </tbody>
    </table>

    <div class="pagination">
        <c:set var="startPage" value="${currentPage - 1}"/>
        <c:set var="endPage" value="${currentPage + 1}"/>

        <c:if test="${startPage < 1}">
            <c:set var="startPage" value="1"/>
        </c:if>

        <c:if test="${endPage > totalPages}">
            <c:set var="endPage" value="${totalPages}"/>
        </c:if>

        <c:if test="${currentPage > 1}">
            <c:choose>
                <c:when test="${not empty paramFilter}">
                    <a class="prev" href="matches?page=${currentPage - 1}&filter_by_player_name=${paramFilter}">&lt;</a>
                </c:when>
                <c:otherwise>
                    <a class="prev" href="matches?page=${currentPage - 1}">&lt;</a>
                </c:otherwise>
            </c:choose>
            <%--          <a class="prev" href="matches?page=${currentPage - 1}&filter_by_player_name=${paramFilter}">&lt;</a>--%>
        </c:if>


        <c:forEach var="i" begin="${startPage}" end="${endPage}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <c:choose>
                        <c:when test="${not empty paramFilter}">
                            <a class="num-page current" href="matches?page=${i}&filter_by_player_name=${paramFilter}">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="num-page current" href="matches?page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                    <%--                    <a class="num-page current" href="matches?page=${i}&filter_by_player_name=${paramFilter}">${i}</a>--%>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${not empty paramFilter}">
                            <a class="num-page" href="matches?page=${i}&filter_by_player_name=${paramFilter}">${i}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="num-page" href="matches?page=${i}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                    <%--                    <a class="num-page" href="matches?page=${i}&filter_by_player_name=${paramFilter}">${i}</a>--%>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${endPage < totalPages}">
            <c:choose>
                <c:when test="${not empty paramFilter}">
                    <a class="next" href="matches?page=${currentPage + 1}&filter_by_player_name=${paramFilter}">&gt;</a>
                </c:when>
                <c:otherwise>
                    <a class="next" href="matches?page=${currentPage + 1}">&gt;</a>
                </c:otherwise>
            </c:choose>
            <%--            <a class="next" href="matches?page=${currentPage + 1}&filter_by_player_name=${paramFilter}">&gt;</a>--%>
        </c:if>

        <%--    <a href="match-finished?page=${currentPage - 1}&filter=${param.filter}">&lt;</a>--%>
        <%--    <a href="match-finished?page=1&filter=${param.filter}" class="${currentPage == 1 ? 'current' : ''}">1</a>--%>
        <%--    <a href="match-finished?page=2&filter=${param.filter}" class="${currentPage == 2 ? 'current' : ''}">2</a>--%>
        <%--    <a href="match-finished?page=3&filter=${param.filter}" class="${currentPage == 3 ? 'current' : ''}">3</a>--%>
        <%--    <a href="match-finished?page=${currentPage + 1}&filter=${param.filter}">&gt;</a>--%>
    </div>
</div>

</body>
</html>
