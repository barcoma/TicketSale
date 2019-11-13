package va.a6.ticketservice;

public class Ticket {
    private TicketState state = TicketState.FREE;
    private String ticketOwner;
    private int id;

    Ticket(int _id){
        this.id = _id;
    }

    public TicketState getState() {
        return state;
    }

    public void setState(TicketState state) {
        this.state = state;
    }

    public String getTicketOwner() {
        return ticketOwner;
    }

    public void setTicketOwner(String ticketOwner) {
        this.ticketOwner = ticketOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
