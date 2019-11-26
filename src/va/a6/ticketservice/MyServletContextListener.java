package va.a6.ticketservice;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    public static ServletContext ctx;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ctx = servletContextEvent.getServletContext();
        DataSource dataSource;
        try{
            Context initialContext = new InitialContext();
            Context context = (Context) initialContext.lookup("java:comp/env");
            dataSource = (DataSource) context.lookup("jdbc/tickets");
            ctx.setAttribute("dataSource", dataSource);
            System.out.println("Datenbanktest erfolgreich");
        } catch (Exception e){
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
        TicketSale ticketSale = new TicketSale(getTicketStatesFromDB(dataSource));
        ctx.setAttribute("ticketSale", ticketSale);
        ctx.setAttribute("dataSource", dataSource);

    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

    private List<Ticket> getTicketStatesFromDB(DataSource dataSource){
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            List<Ticket> list = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT * FROM ticket");

            while (rs.next()) {
             Ticket ticket = new Ticket(rs.getInt("id"));
             ticket.setState(TicketState.FREE);
             ticket.setTicketOwner(rs.getString("owner"));
             list.add(ticket);
            }
            return list;
        } catch (SQLException e){
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
    }
}
