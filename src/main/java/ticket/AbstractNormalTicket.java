package ticket;

public abstract class AbstractNormalTicket extends Ticket {
    private String typeId;
    private double price;

    public AbstractNormalTicket(String typeId, double price, long ticketLifetime) {
        super(System.currentTimeMillis() + ticketLifetime);
        this.typeId = typeId;
        this.price = price;
    }

    @Override
    public String getTypeId() {
        return typeId;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
