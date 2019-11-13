<%--
  Created by IntelliJ IDEA.
  User: marcobalzer
  Date: 05.11.19
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="va.a6.ticketservice.TicketSale" %>
<%@ page import="va.a6.ticketservice.Ticket" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>TicketSale</title>
      <link rel="stylesheet" href="styles/main.css">
  </head>
  <body>
  <%
    ServletContext sc = request.getServletContext();
    TicketSale ticketSale = (TicketSale) sc.getAttribute("ticketSale");
  %>
  <h1>Ticket Verkauf</h1>
  <div class="ticket-container">
      <%
      for(int i = 0; i < ticketSale.getAllTickets().length; i++){
      %>
      <div class="ticket <%= ticketSale.getAllTickets()[i].getState()%>">
        <%=
          ticketSale.getAllTickets()[i].getId()
        %>
      </div>
    <%}%>
  </div>

  <%
      if (ticketSale.isReservationPossible()) {
  %>
  <p>Reservierungen können noch angenommen werden</p>
  <%
  } else {
  %>
  <p>Reservierungen nicht mehr möglich</p>
  <%
      }
  %>

 <nav>
     <ul>
         <li>
             <a href="Verkauf_eines_freien_Tickets.html">Ticket kaufen</a>
         </li>
         <li>
             <%
                 if (ticketSale.isReservationPossible()) {
             %>
             <a href="Reservierung_eines_Tickets.html">Ticket reservieren</a><br />
             <%
             } else {
             %>
             <p><s>Ticket reservieren</s></p>
             <%
                 }
             %>
         </li>
         <li>

             <%
                 if (ticketSale.isReservationPossible()) {
             %>
             <a href="Verkauf_eines_reservierten_Tickets.html">Reserviertes Ticket kaufen</a><br />
             <%
             } else {
             %>
             <p><s>Reserviertes Ticket kaufen</s></p>
             <%
                 }
             %>
         </li>
         <li>
             <a href="Stornierung_eines_Tickets.html">Ticket stornieren</a>
         </li>
         <li>
             <a href="Reservierungen_aufheben.html">Alle Reservierungen aufheben</a>
         </li>
     </ul>
 </nav>
  </body>
</html>
