package va.a6.ticketservice;

import java.util.ArrayList;

public class TicketSale {
    private boolean reservationPossible = true;

    public Ticket[] ticketList = new Ticket[100];

    public TicketSale(){

        for(int i = 0; i < ticketList.length; i++ ){
            ticketList[i]= new Ticket(i+1);
        }

    }

    public boolean buyTicket(Ticket ticket) throws TicketSaleException {
        if (ticket.getState() == TicketState.FREE)
            ticket.setState(TicketState.SOLD);
        else if(ticket.getState() == TicketState.RESERVED)
            throw new TicketSaleException("Ticket bereits reserviert.");
        else if(ticket.getState() == TicketState.SOLD)
            throw new TicketSaleException("Ticket bereits verkauft.");
        return true;
    }

    public boolean reserveTicket(Ticket ticket, String ticketOwner) throws TicketSaleException{
        if(reservationPossible) {
            if (ticket.getState() == TicketState.FREE) {
                ticket.setState(TicketState.RESERVED);
                ticket.setTicketOwner(ticketOwner);
            }
            return true;
        }
        throw new TicketSaleException("edit error handling");
    }

    public boolean cancelReservation(Ticket ticket) throws TicketSaleException{
        if(ticket.getState() == TicketState.RESERVED){
            ticket.setState(TicketState.FREE);
            ticket.setTicketOwner("");
        }
        return true;
    }


}
