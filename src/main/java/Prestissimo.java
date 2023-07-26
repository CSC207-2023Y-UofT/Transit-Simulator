import java.util.Optional;

class Prestissimo{
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
            return false;
        }else{
            this.fund -= cost;
        }
    }
}