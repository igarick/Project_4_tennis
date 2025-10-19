<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
  </style>
</head>
<body>

<header>
  <h2>TennisScoreboard</h2>
  <nav>
    <a href="/">Home</a>
    <a href="WEB-INF/jsp/matches.jsp">Matches</a>
  </nav>
</header>

<div class="container">
  <h1>Finished Matches</h1>

  <div class="input-container">
    <form method="get" action="match-finished">
      <input class="input-filter" type="text" name="filter" placeholder="Filter by name" value="${param.filter}" />
      <button type="submit" class="btn-filter">Apply</button>
      <a href="match-finished"><button type="button" class="btn-filter">Reset</button></a>
    </form>
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
    <%-- Здесь ты можешь использовать обычный for или request.getAttribute("finishedMatches") --%>
    </tbody>
  </table>

  <div class="pagination">
    <a href="match-finished?page=${currentPage - 1}&filter=${param.filter}">&lt;</a>
    <a href="match-finished?page=1&filter=${param.filter}" class="${currentPage == 1 ? 'current' : ''}">1</a>
    <a href="match-finished?page=2&filter=${param.filter}" class="${currentPage == 2 ? 'current' : ''}">2</a>
    <a href="match-finished?page=3&filter=${param.filter}" class="${currentPage == 3 ? 'current' : ''}">3</a>
    <a href="match-finished?page=${currentPage + 1}&filter=${param.filter}">&gt;</a>
  </div>
</div>

</body>
</html>


<%--Что нужно на сервере--%>
<%--Передать currentPage как request.setAttribute("currentPage", pageNumber)--%>

<%--Передать finishedMatches как список--%>

<%--Обработать filter и page в контроллере--%>