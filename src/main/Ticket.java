import java.util.Scanner;

abstract class Ticket{
    public long expiration;
    public abstract int price;

}

class ChildTicket extends Ticket{
    private static int maxAge;
    public static int price;
    public static long expiration;
    public int validity;
    private int age;

    public ChildTicket(int age){
        if(age <= maxAge){
            this.validity = this.expiration;
            this.age = age;
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
    public adultTicket(){
        this.validity = this.expiration;
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

    public SeniorTicket(int age){
        if(age >= minAge){
            this.validity = this.expiration;
            this.age = age;
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

    public StudentTicket(){
        Scanner status = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Are you a student? Please enter Yes or No");
        if(status = "Yes"){
            this.validity = this.expiration;
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