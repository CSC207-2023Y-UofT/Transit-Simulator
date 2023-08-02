package ticket;

import java.util.Optional;

/**
 * The Prestissimo class represents a rechargeable card for buying tickets.
 * Each Prestissimo card has a unique number and a fund balance.
 * Funds can be added to the card, and used for ticket purchases.
 */
public class Prestissimo {

    /**
     * The unique number of the Prestissimo card.
     */
    public int number;

    /**
     * The current fund balance on the Prestissimo card.
     */
    public int fund;

    /**
     * Constructs a new Prestissimo card with the given card number.
     * Initially, the fund balance is set to 0.
     *
     * @param num the number of the Prestissimo card
     */
    public Prestissimo(int num){
        this.number = num;
        this.fund = 0;
    }

    /**
     * Adds the given amount to the fund balance of the Prestissimo card.
     *
     * @param load the amount to be added to the fund balance
     */
    public void loading(int load){
        this.fund += load;
    }

    /**
     * Tries to perform a purchase with the given cost from the fund balance of the Prestissimo card.
     * If the cost is greater than the current fund balance, the purchase is not successful and returns Optional.of(false).
     * If the purchase is successful, the cost is subtracted from the fund balance and returns Optional.empty().
     *
     * @param cost the cost of the purchase
     * @return an Optional containing false if the purchase was not successful, or empty if the purchase was successful
     */
    public Optional<Boolean> purchase(int cost){
        if(cost > this.fund){
            return Optional.of(false);
        }else{
            this.fund -= cost;
        }
        return Optional.empty();
    }

}