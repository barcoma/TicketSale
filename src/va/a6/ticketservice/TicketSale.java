package va.a6.ticketservice;

public class TicketSale {

    private Ticket[] allTickets = new Ticket[100];
    private boolean reservationPossible = true;

    public TicketSale(){

        for(int i = 0; i < allTickets.length; i++ ){
            this.allTickets[i]= new Ticket(i+1);
        }

    }

    public Ticket[] getAllTickets(){
        return allTickets;
    }

    public void setReservationStatus(boolean _reservationPossible){
        this.reservationPossible = _reservationPossible;
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

    public boolean cancelAllReservations(){
        for(int i = 0; i < allTickets.length; i++ ){
            allTickets[i]= new Ticket(i+1);
            if(allTickets[i].getState() == TicketState.RESERVED)
                allTickets[i].setState(TicketState.FREE);
        }
        return true;
    }

    public boolean buyReservedTicket(Ticket ticket){
        if(ticket.getState() == TicketState.RESERVED){
            ticket.setState(TicketState.SOLD);
        }
        return true;
    }


}
