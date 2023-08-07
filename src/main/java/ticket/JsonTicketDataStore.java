package ticket;

import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implements a data store for tickets using JSON format for storage.
 * Each ticket is stored as a separate JSON file in a specified directory.
 */
public class JsonTicketDataStore implements TicketDataStore {

    /**
     * Directory where all ticket JSON files will be stored.
     */
    private final File directory;

    /**
     * Creates a new JSON data store instance with a specified directory for ticket storage.
     *
     * @param directory The directory where ticket files are stored.
     */
    public JsonTicketDataStore(File directory) {
        this.directory = directory;
        directory.mkdirs();
    }

    /**
     * Determines the file corresponding to a ticket ID.
     *
     * @param id The ticket ID.
     * @return The corresponding file object.
     */
    public File getFile(int id) {
        return new File(directory, id + ".json");
    }

    /**
     * Reads a ticket from a specified file.
     *
     * @param file The file where the ticket data is stored.
     * @return The ticket if reading was successful, or an empty optional if not.
     */
    private Optional<Ticket> read(File file) {
        if (!file.exists()) return Optional.empty();
        try {
            String text = Files.readString(file.toPath());
            JSONObject json = new JSONObject(text);
            TicketType type = json.getEnum(TicketType.class, "type");
            int id = json.getInt("id");
            Ticket ticket = new Ticket(id, type);
            ticket.setActivated(json.getBoolean("activated"));
            ticket.setExpiry(json.getLong("expiry"));
            return Optional.of(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Writes a ticket to a specified file in JSON format.
     *
     * @param file The file where the ticket data should be stored.
     * @param ticket The ticket object to be written.
     */
    public void write(File file, Ticket ticket) {
        try {
            JSONObject json = new JSONObject();
            json.put("type", ticket.getType());
            json.put("id", ticket.getId());
            json.put("activated", ticket.isActivated());
            json.put("expiry", ticket.getExpiry());
            Files.writeString(file.toPath(), json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Ticket> getTicket(int id) {
        return read(getFile(id));
    }

    @Override
    public void saveTicket(Ticket ticket) {
        write(getFile(ticket.getId()), ticket);
    }

    @Override
    public void removeTicket(int id) {
        getFile(id).delete();
    }

    @Override
    public List<Ticket> getTickets() {
        File[] files = directory.listFiles();
        if (files == null) return new ArrayList<>();
        List<Ticket> tickets = new ArrayList<>();
        for (File file : files) {
            Optional<Ticket> ticket = read(file);
            ticket.ifPresent(tickets::add);
        }
        return tickets;
    }

    @Override
    public void cleanExpiredTickets() {
        for (Ticket ticket : getTickets()) {
            if (ticket.getExpiry() < System.currentTimeMillis()) {
                removeTicket(ticket.getId());
            } else if (System.currentTimeMillis() - ticket.getCreatedAt() > 1000 * 60 * 60 * 24 * 7) {
                removeTicket(ticket.getId());
            }
        }
    }
}
