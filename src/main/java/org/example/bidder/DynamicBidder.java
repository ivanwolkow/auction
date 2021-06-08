package org.example.bidder;

import com.google.common.base.Preconditions;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.example.exception.BidderNotInitializedException;

import java.util.Collections;

/**
 * Bidding strategy implementation. A bid estimation is based on the implication that the opponent makes it's bids
 * by percentage increase or constant value increase of our last bid.
 */
public class DynamicBidder implements Bidder {

    /**
     * Number of old bids to hold in memory
     */
    private static final int BIDS_QUEUE_LENGTH = 2;


    /**
     * The cash limit (monetary units)
     **/
    private int cash;

    /**
     * Estimated initial product price
     */
    private int initProductPrice;

    /**
     * Opponent's old bids
     */
    private CircularFifoQueue<Integer> otherBids;

    /**
     * Own old bids
     */
    private CircularFifoQueue<Integer> ownBids;

    /**
     * Whether the bidder has been initialized
     */
    private boolean initialized = false;

    @Override
    public void init(int quantity, int cash) {
        this.cash = cash;
        this.initProductPrice = cash / quantity;
        this.otherBids = new CircularFifoQueue<>(BIDS_QUEUE_LENGTH);
        this.ownBids = new CircularFifoQueue<>(BIDS_QUEUE_LENGTH);
        this.initialized = true;
    }

    @Override
    public int placeBid() {
        checkInitialized();

        if (otherBids.isEmpty()) {
            cash -= initProductPrice;
            return initProductPrice;
        }

        int ownBid = ownBids.get(0);
        int otherBid = otherBids.get(otherBids.maxSize() - 1);
        double otherIncreaseRate = 1.0 * otherBid / ownBid;

        int nextBid = Math.min(cash, (int) Math.ceil(ownBids.get(ownBids.maxSize() - 1) * otherIncreaseRate + 1));
        cash -= nextBid;
        return nextBid;
    }

    @Override
    public void bids(int own, int other) {
        checkInitialized();

        Preconditions.checkArgument(own >= 0, "Own bid cannot be a negative value");
        Preconditions.checkArgument(other >= 0, "Other bid cannot be a negative value");

        if (ownBids.isEmpty()) {
            ownBids.addAll(Collections.nCopies(BIDS_QUEUE_LENGTH, other));
        } else {
            ownBids.add(own);
        }

        if (otherBids.isEmpty()) {
            otherBids.addAll(Collections.nCopies(BIDS_QUEUE_LENGTH, other));
        } else {
            otherBids.add(other);
        }

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
