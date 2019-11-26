package va.a6.ticketservice;

import java.util.List;

public class TicketSale {

    private DatabaseUpdate databaseUpdate = new DatabaseUpdate();
    private List<Ticket> allTickets;
    private boolean reservationPossible = true;


    public TicketSale(List<Ticket> initialList){
        allTickets = initialList;
    }

    public  List<Ticket> getAllTickets(){
        return allTickets;
    }

    public boolean isReservationPossible(){
        return reservationPossible;
    }

    public synchronized boolean setReservationStatus(boolean _reservationPossible){
        reservationPossible = _reservationPossible;
        return true;
    }

    public synchronized  boolean buyTicket(Ticket ticket){

        if (ticket.getState() == TicketState.FREE) {
            ticket.setState(TicketState.SOLD);
            databaseUpdate.updateDB(ticket);
            return true;
        }
        throw new TicketSaleException(ticket.getState());
    }

    public synchronized boolean reserveTicket(Ticket ticket, String ticketOwner){
        if(reservationPossible) {
            if (ticket.getState() == TicketState.FREE) {
                ticket.setState(TicketState.RESERVED);
                ticket.setTicketOwner(ticketOwner);
                databaseUpdate.updateDB(ticket);
                return true;
            }
            throw new TicketSaleException(ticket.getState());
        }
        throw new TicketSaleException(ticket.getState());
    }

    public synchronized boolean cancelReservation(Ticket ticket){
        if (ticket.getState() == TicketState.RESERVED || ticket.getState() == TicketState.SOLD) {
            ticket.setState(TicketState.FREE);
            ticket.setTicketOwner("");
            databaseUpdate.updateDB(ticket);
            return true;
        }
        throw new TicketSaleException(ticket.getState());
    }

    public synchronized boolean cancelAllReservations(){
        for (Ticket allTicket : allTickets) {
            if (allTicket.getState() == TicketState.RESERVED)
                allTicket.setState(TicketState.FREE);
        }
        setReservationStatus(false);
        return true;
    }

    public synchronized boolean buyReservedTicket(Ticket ticket){
        if(ticket.getState() == TicketState.RESERVED){
            ticket.setState(TicketState.SOLD);
            databaseUpdate.updateDB(ticket);
            return true;
        }
      throw new TicketSaleException(ticket.getState());
    }


}
