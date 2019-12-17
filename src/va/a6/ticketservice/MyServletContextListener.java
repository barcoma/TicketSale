package va.a6.ticketservice;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    public static ServletContext ctx;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ctx = servletContextEvent.getServletContext();
        DataSource dataSource;
        try {
            Context initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/tickets");
            ctx.setAttribute("dataSource", dataSource);
            System.out.println("Datenbanktest erfolgreich");
        } catch (Exception e) {
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
        TicketSale ticketSale = new TicketSale(getTicketStatesFromDB(dataSource), getOptionsFromDB(dataSource));
        ctx.setAttribute("ticketSale", ticketSale);
        ctx.setAttribute("dataSource", dataSource);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private List<Ticket> getTicketStatesFromDB(DataSource dataSource) {
        String sql = "SELECT * FROM ticket";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Ticket> list = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Ticket ticket = new Ticket(rs.getInt("id"));
                ticket.setState(TicketState.valueOf(rs.getString("state")));
                ticket.setTicketOwner(rs.getString("owner"));
                list.add(ticket);
            }
            return list;
        } catch (SQLException e) {
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
    }

    private boolean getOptionsFromDB(DataSource dataSource) {
        String sql = "SELECT reservationsPossible FROM options";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            List<Ticket> list = new ArrayList<>();
            ResultSet rs = preparedStatement.executeQuery();
            boolean reservationsPossible = true;
            while (rs.next()) {
                reservationsPossible = rs.getBoolean("reservationsPossible");
            }
            return reservationsPossible;
        } catch (SQLException e) {
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
    }
}
