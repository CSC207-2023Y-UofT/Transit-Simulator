import java.util.Objects;
import java.util.Scanner;

class StudentTicket extends Ticket{
    public static int price;
    public static long expiration;
    public long validity;
    public Station departure;
    public Station destination;

    public StudentTicket(Station dep, Station des){
        Scanner status = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Are you a student? Please enter Yes or No");
        String input = status.nextLine();
        if(Objects.equals(input, "Yes")){
            this.validity = expiration;
            this.departure = dep;
            this.destination = des;
        }else {
            System.out.println("You are not eligible for this ticket");
        }
    }


    public static void setExpiration(long time){
        StudentTicket.expiration = time;
    }

    public static void setPrice(int price){
        StudentTicket.price = price;
    }

    public String getType(){
        return "Student Ticket";
    }
}
