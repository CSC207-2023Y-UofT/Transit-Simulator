import java.util.Scanner;
import java.time.Duration;

abstract class Ticket{
    public long expiration;
    public abstract int price;
    public Station departure;
    public Station destination;

}

class ChildTicket extends Ticket{
    private static int maxAge;
    public static int price;
    public static long expiration;
    public int validity;
    private int age;
    public Station departure;
    public Station destination;

    public ChildTicket(int age, Station start, Station end){
        if(age <= maxAge){
            this.validity = this.expiration;
            this.age = age;
            this.departure = start;
            this.destination = end;
        }else{
            System.out.println("You are not eligible for this ticket");
            break;
        }
    }

    public static void setMaxAge(int age){
        ChildTicket.maxAge = age;
    }

    public static void setExpiration(long time){
        ChildTicket.expiration = time;
    }

    public static void setPrice(int price){
        ChildTicket.price = price;
    }
}

class AdultTicket extends Ticket{
    public long validity;
    public static int price;
    public Station departure;
    public Station destination;
    public adultTicket(Station start, Station end){
        this.validity = this.expiration;
        this.departure = start;
        this.destination = end;
    }

    public static void setPrice(int price) {
        AdultTicketTicket.price = price;
    }

    public static void setExpiration(long time){
        AdultTicket.expiration = time;
    }
}

class SeniorTicket extends Ticket{
    private static int minAge;
    public static int price;
    public static long expiration;
    public int validity;
    private int age;
    public Station departure;
    public Station destination;

    public SeniorTicket(int age){
        if(age >= minAge){
            this.validity = this.expiration;
            this.age = age;
            this.departure = start;
            this.destination = end;
        }else {
            System.out.println("You are not eligible for this ticket");
            break;
        }
    }
    public static void setMinAge(int age){
        SeniorTicket.minAge = age;
    }

    public static void setExpiration(long time){
        SeniorTicket.expiration = time;
    }

    public static void setPrice(int price){
        SeniorTicket.price = price;
    }
}

class StudentTicket extends Ticket{
    public static int price;
    public static long expiration;
    public int validity;
    public Station departure;
    public Station destination;

    public StudentTicket(){
        Scanner status = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Are you a student? Please enter Yes or No");
        if(status = "Yes"){
            this.validity = this.expiration;
            this.departure = start;
            this.destination = end;
        }else {
            System.out.println("You are not eligible for this ticket");
            break;
        }
    }


    public static void setExpiration(long time){
        StudentTicket.expiration = time;
    }

    public static void setPrice(int price){
        StudentTicket.price = price;
    }
}