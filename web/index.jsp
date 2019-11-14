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
      <div>
      <div class="ticket <%= ticketSale.getAllTickets()[i].getState()%>">
        <%=
          ticketSale.getAllTickets()[i].getId()
        %>
      </div>
          <ul class="dropdown-menu">
              <li>
                  <form name="verkauf" action="TicketHandler" method="post">
                      <input class="hidden-input" name="methodToExcecute" value="buy"/>
                      <input class="hidden-input" name="ticketId" value="<%=ticketSale.getAllTickets()[i].getId() %>"/>
                      <button type="submit">Ticket kaufen</button>
                  </form>
              </li>
              <li>
                  <%
                      if (ticketSale.isReservationPossible()) {
                  %>
                  <a href="Reservierung_eines_Tickets.html">Ticket reservieren</a><br/>
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
                  <form name="verkauf" action="TicketHandler" method="post">
                      <input class="hidden-input" name="methodToExcecute" value="cancelTicket"/>
                      <input class="hidden-input" name="ticketId" value="<%=ticketSale.getAllTickets()[i].getId() %>"/>
                      <button type="submit">Ticket storno</button>
                  </form>
              </li>
              <li>
                  <a href="Reservierungen_aufheben.html">Alle Reservierungen aufheben</a>
              </li>
          </ul>
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

  <script>
      //toggle dropdown menu open/close
      var toClose = false;
      var otherSelected = false;
      var parent;

      function toggle(e) {
          e.stopPropagation();
          if(otherSelected){
              closeAll();
          }
          var btn=this;
          parent = this;
          var menu = btn.nextSibling;
          while(menu && menu.nodeType !== 1) {
              menu = menu.nextSibling
          }
          if(!menu) return;
          if (menu.style.display !== 'block') {
              menu.style.display = 'block';
              this.style.backgroundColor = "blue";
              if(toClose) toClose.style.display="none";
              toClose  = menu;
              otherSelected = true;
          }  else {
              menu.style.display = 'none';
              toClose=false;
          }

      }
      function closeAll() {
          toClose.style.display='none';
          parent.style.backgroundColor = "";
          otherSelected = false;
      }

      window.addEventListener("DOMContentLoaded",function(){
          document.querySelectorAll(".ticket").forEach(function(btn){
              btn.addEventListener("click",toggle,true);
          });
      });

      window.onclick=function(event){
          if (toClose){
              closeAll.call(event.target);
          }
      };

      /* Old code

      // select Button - now by ID - but I need unknown number of buttons - from REST API - and the code working for all of them
      var btn = document.getElementById("dropBtn1");

      // select Dropdown menu - next to the button - to be sure it will open the right menu no matter which button will be pressed
      var menu = btn.nextSibling;
      while(menu && menu.nodeType != 1) {
          menu = menu.nextSibling
      }

      //toggle dropdown menu open/close
      btn.addEventListener("click", function() {
        if (menu.style.display == 'none') {
          menu.style.display = 'block';
        }
        else {
          menu.style.display = 'none';
        }
      });
      */
  </script>

  </body>
</html>
