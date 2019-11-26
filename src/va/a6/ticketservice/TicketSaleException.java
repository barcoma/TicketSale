package va.a6.ticketservice;

public class TicketSaleException extends RuntimeException {
    public TicketSaleException(TicketState state){
        super(String.valueOf(state));
    }
}
