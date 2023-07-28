package ticket;

public class ChildTicket extends Ticket {
    private static int maxAge;
    public static int price;
    public static long expiration;
    public long validity;
    public Station departure;
    public Station destination;

    public ChildTicket(int age, Station start, Station end){
        if(age <= maxAge){
            this.validity = expiration;
            this.departure = start;
            this.destination = end;
        }else{
            System.out.println("You are not eligible for this ticket");
        }
    }

    public static void setMaxAge(int age){ChildTicket.maxAge = age;
    }

    public static void setExpiration(long time){
        ChildTicket.expiration = time;
    }

    public static void setPrice(int price){
        ChildTicket.price = price;
    }


    public String getType() {
        return "Child";
    }
}

