import java.util.Scanner;
import java.time.Duration;

abstract class Ticket implements TicketInterface {
    public static long expiration;
    public int price;
    public Station departure;
    public Station destination;

    public abstract String getType();
}

public void ticketSelling(){
    Scanner depart = new Scanner(System.in);
    System.out.println("Please enter your departure station");
    Scanner dest = new Scanner(System.in);
    System.out.println("Please enter your destination staion");
    Scanner tick = new Scanner(System.in);
    System.out.println("Please choose the ticket you want to purchase:\n 1. Adult\n 2. Senior\n 3.Child\n 4.Student");
    String input1 = depart.nextLine();
    String input2 = dest.nextLine();
    String input3 = tick.nextLine();
    if(input3.equals("0")){
        // wait for the statistics and station class to add the new ticket into it
        }else if(input3.equals("1")){

        }else if(input3.equals("2")){

        }else if(input3.equals("3")){

        }else if(input3.equals("4")){

        }else{
        System.out.println("Please enter the valid ticket");
        }
    Scanner Prest = new Scanner(System.in);
    int num = Prest.nextInt();
    System.out.println("Please enter your Prestissimo card number. Enter 0 if not applicable");
    if(num == 0){

        }
    }
}
