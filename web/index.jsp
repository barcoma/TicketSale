<%--
  Created by IntelliJ IDEA.
  User: marcobalzer
  Date: 05.11.19
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="va.a6.ticketservice.Ticket" %>
<%@ page import="va.a6.ticketservice.TicketSale" %>
<%@ page import="java.util.List" %>

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
    List<Ticket> ticketList = ticketSale.getAllTickets();
%>
<h1>Ticket Verkauf</h1>
<div class="ticket-container">
    <% for (Ticket ticket : ticketList) { %>
    <div>
        <div class="ticket <%= ticket.getState()%>">
            <%=
            ticket.getId()
            %>
        </div>
        <ul class="dropdown-menu">
            <li>
                <form class="dropdown-element" name="verkauf" action="TicketHandler" method="post">
                    <input class="hidden-input" name="methodToExcecute" value="buy"/>
                    <input class="hidden-input" name="ticketId" value="<%=ticket.getId() %>"/>
                    <button class="dropdown-element" type="submit">Ticket kaufen</button>
                </form>
            </li>
            <li>
                <%
                    if (ticketSale.isReservationPossible()) {
                %>
                <button class="dropdown-element"><a href="Reservierung_eines_Tickets.html">Ticket reservieren</a><br/>
                </button>


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
                <button class="dropdown-element">
                    <a href="Verkauf_eines_reservierten_Tickets.html">Reserviertes Ticket kaufen</a><br/>
                </button>
                <%
                } else {
                %>
                <p><s>Reserviertes Ticket kaufen</s></p>
                <%
                    }
                %>
            </li>
            <li>
                <form class="dropdown-element" name="verkauf" action="TicketHandler" method="post">
                    <input class="hidden-input" name="methodToExcecute" value="cancelTicket"/>
                    <input class="hidden-input" name="ticketId" value="<%=ticket.getId() %>"/>
                    <button class="dropdown-element" type="submit">Ticket storno</button>
                </form>
            </li>
            <li>
                <button class="dropdown-element">
                    <a href="Reservierungen_aufheben.html">Alle Reservierungen aufheben</a><br/>
                </button>
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
            <a href="Verkauf_eines_reservierten_Tickets.html">Reserviertes Ticket kaufen</a><br/>
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
        if (otherSelected) {
            closeAll();
        }
        parent = this;
        var menu = parent.nextSibling;
        while (menu && menu.nodeType !== 1) {
            menu = menu.nextSibling
        }
        if (!menu) return;
        if (menu.style.display !== 'block') {
            menu.style.display = 'block';
            this.style.backgroundColor = "#228C22";
            if (toClose)
                toClose.style.display = "none";
            toClose = menu;
            otherSelected = true;
        } else {
            menu.style.display = 'none';
            toClose = false;
        }

    }

    function closeAll() {
        toClose.style.display = 'none';
        parent.style.backgroundColor = "";
        otherSelected = false;
        toClose = false;
        parent = null;
    }

    window.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll(".ticket").forEach(function (btn) {
            btn.addEventListener("click", toggle, true);
        });
    });

    window.onclick = function (event) {
        if (toClose) {
            closeAll.call(event.target);
        }
    };
</script>

</body>
</html>
