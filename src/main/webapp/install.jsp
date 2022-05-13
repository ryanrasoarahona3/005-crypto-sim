<%@ page import="controllers.InstallationController" %>
<%--
  Created by IntelliJ IDEA.
  User: astrozeneka
  Date: 13/05/2022
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% InstallationController.getInstance().install(); %>
<html>
<head>
    <title>Crypto Simulation</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>
      <div class="container">
          <h1>Installation</h1>
          <p>L'installation devrait se faire à partir de cette page</p>
          <p>La configuration de la base de donnée devrait se faire à partir de cette page</p>
          <p>L'installation est terminée</p>
      </div>
</body>
</html>
