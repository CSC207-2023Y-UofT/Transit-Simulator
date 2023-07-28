import stats.TicketSaleStat;

import java.util.Scanner;
import java.time.Duration;

abstract class Ticket implements TicketInterface {
    public static long expiration;
    public int price;
    public Station departure;
    public Station destination;

    public abstract String getType();



    public void ticketSelling(Station dep, Station des){
    Scanner tick = new Scanner(System.in);
    System.out.println("Please choose the ticket you want to purchase:\n 1. Adult\n 2. Senior\n 3.Child\n 4.Student");
    String input3 = tick.nextLine();
    switch (input3) {
        case "0":
            // wait for the statistics and station class to add the new ticket into it
            break;
        case "1":
            AdultTicket ad = new AdultTicket(dep, des);
            TicketSaleStat adult = new TicketSaleStat(ad);

            break;
        case "2": {
            System.out.println("Please enter your age");
            Scanner age = new Scanner(System.in);
            int age1 = age.nextInt();
            SeniorTicket se = new SeniorTicket(age1, dep, des);
            TicketSaleStat senior = new TicketSaleStat(se);

            break;
        }
        case "3": {
            System.out.println("Please enter your age");
            Scanner age = new Scanner(System.in);
            int age1 = age.nextInt();
            ChildTicket ch = new ChildTicket(age1, dep, des);
            TicketSaleStat senior = new TicketSaleStat(ch);

            break;
        }
        case "4":
            StudentTicket st = new StudentTicket(dep, des);
            TicketSaleStat student = new TicketSaleStat(st);

            break;
        default:
            System.out.println("Please enter the valid ticket");
            break;
    }
    Scanner Prest = new Scanner(System.in);
    int num = Prest.nextInt();
    System.out.println("Please enter your Prestissimo card number. Enter 0 if not applicable");
    if(num == 0){

        }
    }
}
