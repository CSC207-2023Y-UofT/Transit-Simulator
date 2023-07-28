package ticket;

import java.util.Optional;

public class Prestissimo extends Ticket {
    public int number;
    public int fund;

    public Prestissimo(int num){
        this.number = num;
        this.fund = 0;
    }

    public void loading(int load){
        this.fund += load;
    }

    public Optional<Boolean> purchase(int cost){
        if(cost > this.fund){
            return Optional.of(false);
        }else{
            this.fund -= cost;
        }
        return Optional.empty();
    }

    @Override
    public String getType() {
        return "Presstissimo";
    }
}