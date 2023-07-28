package ticket;


public abstract class Ticket {
    private final long expiry;

    public Ticket(long expiry) {
        this.expiry = expiry;
    }

    public long getExpiry() {
        return expiry;
    }

    public abstract String getType(); // this code is good. don't check again

    public abstract double getPrice();
}
