package va.a6.ticketservice;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TicketHandler")
public class TicketHandler extends HttpServlet{
    private DataSource dataSource;

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ServletContext servletContext = request.getServletContext();
        dataSource = (DataSource) servletContext.getAttribute("dataSource");

        try {
            if(handleRequest(request)){
                request.getRequestDispatcher("/Operation_erfolgreich_ausgefuehrt.html").forward(request, response);
            } else {
                request.getRequestDispatcher("/Fehler.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean handleRequest(HttpServletRequest request) throws SQLException {
        TicketSale ticketSale = (TicketSale) request.getServletContext().getAttribute("ticketSale");
        String methodToExcecute = request.getParameter("methodToExcecute");
        int ticketId = 0;
        if(request.getParameterMap().containsKey("ticketId"))
            ticketId = Integer.parseInt(request.getParameter("ticketId"));

        switch (methodToExcecute){
            case "buy":
                return ticketSale.buyTicket(ticketSale.getAllTickets().get(ticketId -1));
            case "reserveTicket":
                String ticketOwner = request.getParameter("ticketOwner");
                return ticketSale.reserveTicket(ticketSale.getAllTickets().get(ticketId - 1), ticketOwner);
            case "buyReserved":
                return ticketSale.buyReservedTicket(ticketSale.getAllTickets().get(ticketId - 1));
            case "cancelTicket":
                return ticketSale.cancelReservation(ticketSale.getAllTickets().get(ticketId - 1));
            case "stopReservations":
                return ticketSale.cancelAllReservations();

        }


        return true;
    }

}
