//package ticket;
//
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.List;
//import java.util.Optional;
//
//public class JsonTicketDataStore implements TicketDataStore {
//
//    private final File directory;
//
//    public JsonTicketDataStore(File directory) {
//        this.directory = directory;
//    }
//
//    public File getFile(int id) {
//        return new File(directory, id + ".json");
//    }
//
//    private Optional<Ticket> read(File file) {
//        if (!file.exists()) return Optional.empty();
//        try {
//            String text = Files.readString(file.toPath());
//
//        } catch (IOException e) {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public Optional<Ticket> getTicket(int id) {
//
//    }
//
//    @Override
//    public void saveTicket(Ticket ticket) {
//
//    }
//
//    @Override
//    public void removeTicket(int id) {
//
//    }
//
//    @Override
//    public List<Ticket> getTickets() {
//        return null;
//    }
//
//    @Override
//    public void cleanExpiredTickets() {
//
//    }
//}
