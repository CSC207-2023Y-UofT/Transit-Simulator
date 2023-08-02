package ticket;

/**
 * AbstractNormalTicket is an abstract implementation of the Ticket class.
 * It represents a ticket that has a type and a price.
 * This class can be extended to create different types of normal tickets.
 *
 * @see Ticket
 */
public abstract class AbstractNormalTicket extends Ticket {

    /**
     * The ID representing the type of the ticket.
     */
    private String typeId;

    /**
     * The price of the ticket.
     */
    private double price;

    /**
     * Constructs a new AbstractNormalTicket object with the given typeId, price and ticket lifetime.
     * The expiration time is calculated based on the provided ticket lifetime.
     *
     * @param typeId         the ID of the type of the ticket
     * @param price          the price of the ticket
     * @param ticketLifetime the lifetime of the ticket in milliseconds
     */
    public AbstractNormalTicket(String typeId, double price, long ticketLifetime) {
        super(System.currentTimeMillis() + ticketLifetime);
        this.typeId = typeId;
        this.price = price;
    }

    /**
     * Returns the ID of the type of the ticket.
     *
     * @return the ID of the type of the ticket
     */
    @Override
    public String getTypeId() {
        return typeId;
    }

    /**
     * Returns the price of the ticket.
     *
     * @return the price of the ticket
     */
    @Override
    public double getPrice() {
        return price;
    }

}
