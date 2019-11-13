package va.a6.ticketservice;

public class TicketSaleException extends RuntimeException {
    public TicketSaleException(String errorMessage){
        super(errorMessage);
    }
}
