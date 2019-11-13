package va.a6.ticketservice;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        TicketSale ticketSale = new TicketSale();
        servletContext.setAttribute("ticketSale", ticketSale);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
