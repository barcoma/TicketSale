package va.a6.ticketservice;

public class TicketSaleException extends RuntimeException {
    public TicketState ticketState;

    public TicketSaleException(TicketState _ticketState) {
        this.ticketState = _ticketState;
    }

    public String showCorrectError() {
        String ticketMessage = "Das ausgewhählte Ticket ist bereits ";
        switch (this.ticketState) {
            case FREE:
                return ticketMessage + "frei";
            case SOLD:
                return ticketMessage + "verkauft";
            case RESERVED:
                return ticketMessage + "reserviert";
            default:
                return "error";
        }
    }

    @Override
    public String getMessage() {
        return showCorrectError();
    }
}
