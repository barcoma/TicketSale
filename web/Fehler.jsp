<%@ page isErrorPage="true" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fehler</title>
</head>
<body>
<h3>Leider ist ein Fehler aufgetreten.</h3>
<p>Die Operation konnte nicht ausgeführt werden</p>
<%--    <p>Ursache: <%= ((TicketException)exception).showCorrectError() %></p>--%>
<p>Ursache: <%= exception.getMessage() %>
</p>
<a href="index.jsp">Zurück zur Startseite</a>
</body>
</html>