package org.example.bidder;

import com.google.common.base.Preconditions;
import org.example.exception.BidderNotInitializedException;

/**
 * Simple bidding strategy implementation.
 * The bid is calculated as follows: an opponent's previous bid + extra bid (which is a constant fraction)
 **/
public class SimpleBidder implements Bidder {

    /**
     * The constant fraction that is summed with an opponent's previous bid
     **/
    private static final int EXTRA_BID = 5;


    /**
     * The opponent's previous bid
     **/
    private int opponentsBid;

    /**
     * The cash limit (monetary units)
     **/
    private int cash;

    /**
     * Whether the bidder has been initialized
     */
    private boolean initialized = false;

    @Override
    public void init(int quantity, int cash) {
        this.opponentsBid = cash / quantity;
        this.cash = cash;
        this.initialized = true;
    }

    @Override
    public int placeBid() {
        checkInitialized();

        int bid = Math.min(cash, opponentsBid + EXTRA_BID);
        cash -= bid;
        return bid;
    }

    @Override
    public void bids(int own, int other) {
        checkInitialized();

        Preconditions.checkArgument(own >= 0, "Own bid cannot be a negative value");
        Preconditions.checkArgument(other >= 0, "Other bid cannot be a negative value");

        this.opponentsBid = other;
    }

    /**
     * Checks if the bidder is initialized
     */
    private void checkInitialized() {
        if (!initialized) {
            throw new BidderNotInitializedException();
        }
    }

}
