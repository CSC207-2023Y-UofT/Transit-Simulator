package ticket;

import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class facilitates the storage and retrieval of ticket data in JSON format.
 */
public class JsonTicketDataStore implements TicketDataStore {

    /**
     * The directory where the ticket data files are stored.
     */
    private final File directory;

    public JsonTicketDataStore(File directory) {
        this.directory = directory;
        directory.mkdirs();
    }

    /**
     * Returns a file instance pointing to the data file for the given id.
     * @param id The id of the data.
     * @return A File instance.
     */
    public File getFile(int id) {
        return new File(directory, id + ".json");
    }

    /**
     * Read a ticket from a file.
     * @param file The file to read from.
     * @return An optional ticket.
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
            ticket.setCreatedAt(json.optLong("createdAt", 0));
            return Optional.of(ticket);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Write a ticket to a file.
     * @param file The file to write to.
     * @param ticket The ticket to write.
     */
    public void write(File file, Ticket ticket) {
        try {
            JSONObject json = new JSONObject();
            json.put("type", ticket.getType());
            json.put("id", ticket.getId());
            json.put("activated", ticket.isActivated());
            json.put("expiry", ticket.getExpiry());
            json.put("createdAt", ticket.getCreatedAt());
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
            if (ticket.getExpiry() != -1 && ticket.getExpiry() < System.currentTimeMillis()) {
                removeTicket(ticket.getId());
            } else if (System.currentTimeMillis() - ticket.getCreatedAt() > 1000 * 60 * 60 * 24) {
                removeTicket(ticket.getId());
            }
        }
    }
}
