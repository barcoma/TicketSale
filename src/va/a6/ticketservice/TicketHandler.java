package va.a6.ticketservice;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/TicketHandler")
public class TicketHandler extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext servletContext = request.getServletContext();

        try {
            if (handleRequest(request)) {
                request.getRequestDispatcher("/Operation_erfolgreich_ausgefuehrt.html").forward(request, response);
            } else {
                request.getRequestDispatcher("/Fehler.jsp").forward(request, response);
            }
        } catch (NumberFormatException | ServletException e) {
            request.getRequestDispatcher("/Fehler.jsp").forward(request, response);
        }
    }

    public boolean handleRequest(HttpServletRequest request) {
        TicketSale ticketSale = (TicketSale) request.getServletContext().getAttribute("ticketSale");
        String methodToExcecute = request.getParameter("methodToExcecute");
        int ticketId = 0;
        String ticketOwner = "";
        if (request.getParameterMap().containsKey("ticketOwner"))
            ticketOwner = request.getParameter("ticketOwner");
        if (request.getParameterMap().containsKey("ticketId"))
            ticketId = Integer.parseInt(request.getParameter("ticketId"));
        switch (methodToExcecute) {
            case "buy":
                return ticketSale.buyTicket(ticketSale.getAllTickets().get(ticketId - 1));
            case "reserveTicket":
                return ticketSale.reserveTicket(ticketSale.getAllTickets().get(ticketId - 1), ticketOwner);
            case "buyReserved":
                return ticketSale.buyReservedTicket(ticketSale.getAllTickets().get(ticketId - 1), ticketOwner);
            case "cancelTicket":
                return ticketSale.cancelReservation(ticketSale.getAllTickets().get(ticketId - 1));
            case "stopReservations":
                return ticketSale.cancelAllReservations();
            default:
                return false;
        }
    }

}
