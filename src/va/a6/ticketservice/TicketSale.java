package va.a6.ticketservice;

import java.util.List;

public class TicketSale {

    private TicketSaleDBHandler ticketSaleDBHandler = new TicketSaleDBHandler();
    private List<Ticket> allTickets;
    private boolean reservationPossible;

    public TicketSale(List<Ticket> initialList, boolean _reservationPossible){
        this.allTickets = initialList;
        this.reservationPossible = _reservationPossible;
    }

    public  List<Ticket> getAllTickets(){
        return allTickets;
    }

    public boolean isReservationPossible(){
        return reservationPossible;
    }

    public synchronized boolean setReservationStatus(boolean _reservationPossible){
        reservationPossible = _reservationPossible;
        ticketSaleDBHandler.updateOptionsTable(_reservationPossible);
        return true;
    }

    public synchronized  boolean buyTicket(Ticket ticket){
        if (checkTicketState(TicketState.FREE, ticket)) {
            ticket.setState(TicketState.SOLD);
            ticketSaleDBHandler.updateTicketTable(ticket);
            return true;
        }
        throw new TicketSaleException(ticket.getState());
    }

    public synchronized boolean reserveTicket(Ticket ticket, String ticketOwner){
        if(reservationPossible) {
            if (checkTicketState(TicketState.FREE, ticket)) {
                ticket.setState(TicketState.RESERVED);
                ticket.setTicketOwner(ticketOwner);
                ticketSaleDBHandler.updateTicketTable(ticket);
                return true;
            }
            throw new TicketSaleException(ticket.getState());
        }
        throw new TicketSaleException(ticket.getState());
    }

    public synchronized boolean cancelReservation(Ticket ticket){
        if (checkTicketState(TicketState.RESERVED, ticket)) {
            ticket.setState(TicketState.FREE);
            ticket.setTicketOwner("");
            ticketSaleDBHandler.updateTicketTable(ticket);
            return true;
        }
        throw new TicketSaleException(ticket.getState());
    }

    public synchronized boolean cancelAllReservations(){
            for (Ticket ticket : allTickets) {
                if (checkTicketState(TicketState.RESERVED, ticket))
                    ticket.setState(TicketState.FREE);
            }
            setReservationStatus(false);
            return true;
    }

    public synchronized boolean buyReservedTicket(Ticket ticket, String ticketOwner){
        if( checkTicketState(TicketState.RESERVED, ticket) && ticket.getTicketOwner().equals(ticketOwner)){
            ticket.setState(TicketState.SOLD);
            ticketSaleDBHandler.updateTicketTable(ticket);
            return true;
        }
      throw new TicketSaleException(ticket.getState());
    }

    private synchronized boolean checkTicketState(TicketState ticketState, Ticket ticket) {
        return ticketSaleDBHandler.getTicketFromDB(ticket.getId()).equals(ticketState);
    }


}
