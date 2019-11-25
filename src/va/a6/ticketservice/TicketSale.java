package va.a6.ticketservice;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TicketSale {

    private Ticket[] allTickets = new Ticket[100];
    private boolean reservationPossible = true;


    public TicketSale(){

    }

    public  Ticket[] getAllTickets(){
        return allTickets;
    }

    public boolean isReservationPossible(){
        return reservationPossible;
    }

    public synchronized boolean setReservationStatus(boolean _reservationPossible){
        reservationPossible = _reservationPossible;
        return true;
    }

    public synchronized  boolean buyTicket(int id, DataSource dataSource) throws TicketSaleException, SQLException {
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            return true;
        } catch (SQLException e){
            System.out.println("Fehler" + e);
            throw new RuntimeException();
        }
//        if (ticket.getState() == TicketState.FREE)
//            ticket.setState(TicketState.SOLD);
//        else if(ticket.getState() == TicketState.RESERVED || ticket.getState() == TicketState.SOLD )
//            throw new TicketSaleException("Ticket bereits reserviert.");
//        return true;

    }

    public synchronized boolean reserveTicket(Ticket ticket, String ticketOwner) throws TicketSaleException{
        if(reservationPossible) {
            if (ticket.getState() == TicketState.FREE) {
                ticket.setState(TicketState.RESERVED);
                ticket.setTicketOwner(ticketOwner);
            }
            return true;
        }
        throw new TicketSaleException("edit error handling");
    }

    public synchronized boolean cancelReservation(Ticket ticket) throws TicketSaleException{
        if (ticket.getState() == TicketState.RESERVED || ticket.getState() == TicketState.SOLD) {
            ticket.setState(TicketState.FREE);
            ticket.setTicketOwner("");
        }
        return true;
    }

    public synchronized boolean cancelAllReservations(){
        for(int i = 0; i < allTickets.length; i++ ){
            if(allTickets[i].getState() == TicketState.RESERVED)
                allTickets[i].setState(TicketState.FREE);
        }
        setReservationStatus(false);
        return true;
    }

    public synchronized boolean buyReservedTicket(Ticket ticket){
        if(ticket.getState() == TicketState.RESERVED){
            ticket.setState(TicketState.SOLD);
        }
        return true;
    }


}
