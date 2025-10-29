<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Ошибка</title>
  <style>
    body {
      font-family: 'Poppins', sans-serif;
      background-color: #f9f9f9;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      height: 100vh;
      color: #333;
    }
    .error-code {
      font-size: 64px;
      font-weight: 500;
      margin-bottom: 10px;
    }
    .error-message {
      font-size: 20px;
      font-weight: 300;
    }
  </style>
</head>
<body>
<div class="error-code">
  ${errorCode}>
</div>
<div class="error-message">
  ${errorMessage}
</div>
</body>
</html>
