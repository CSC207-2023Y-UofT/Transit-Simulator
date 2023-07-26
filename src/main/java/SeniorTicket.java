import model.node.Station;

class SeniorTicket extends Ticket {
    private static int minAge;
    public static int price;
    public static long expiration;
    public long validity;
    private int age;
    public Station departure;
    public Station destination;

    public SeniorTicket(int age) {
        if (age >= minAge) {
            this.validity = expiration;
            this.age = age;
            this.departure = start;
            this.destination = end;
        } else {
            System.out.println("You are not eligible for this ticket");
        }
    }

    public static void setMinAge(int age) {
        SeniorTicket.minAge = age;
    }

    public static void setExpiration(long time) {
        SeniorTicket.expiration = time;
    }

    public static void setPrice(int price) {
        SeniorTicket.price = price;
    }

    @Override
    public String getType() {
        return "Senior Ticket";
    }
}
