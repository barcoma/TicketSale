package va.a6.ticketservice;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/TicketHandler")
public class TicketHandler extends HttpServlet{
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        if(handleRequest(request)){
            request.getRequestDispatcher("/Operation_erfolgreich_ausgefuehrt.html").forward(request, response);
        } else {
            request.getRequestDispatcher("/Fehler.jsp").forward(request, response);
        }
    }

    public boolean handleRequest(HttpServletRequest request){
        return true;
    }
}
