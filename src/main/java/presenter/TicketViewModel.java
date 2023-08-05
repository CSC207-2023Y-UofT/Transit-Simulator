package presenter;

public class TicketViewModel {
    private int numAdultTickets = 0;
    private int numChildTickets = 0;
    private int numSeniorTickets = 0;
    private int numStudentTickets = 0;

    public int getNumAdultTickets() {
        return numAdultTickets;
    }

    public int getNumChildTickets() {
        return numChildTickets;
    }

    public int getNumSeniorTickets() {
        return numSeniorTickets;
    }

    public int getNumStudentTickets() {
        return numStudentTickets;
    }

    public void setNumAdultTickets(int numAdultTickets) {
        this.numAdultTickets = numAdultTickets;
    }

    public void setNumChildTickets(int numChildTickets) {
        this.numChildTickets = numChildTickets;
    }

    public void setNumSeniorTickets(int numSeniorTickets) {
        this.numSeniorTickets = numSeniorTickets;
    }

    public void setNumStudentTickets(int numStudentTickets) {
        this.numStudentTickets = numStudentTickets;
    }
}
