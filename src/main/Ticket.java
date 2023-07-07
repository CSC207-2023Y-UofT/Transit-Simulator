class Ticket{
    public long expiration;
    public Ticket{}
}

class childTicket extends Ticket{
    public static int maxAge;
    public static int price;
    public static long expiration;
    private int age;
    public childTicket(int age){
        if(age <= maxAge){
            this.expiration =
        }
    }
}