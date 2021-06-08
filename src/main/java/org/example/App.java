package org.example;

import org.example.bidder.DynamicBidder;
import org.example.bidder.SimpleBidder;
import org.example.runner.AuctionRunner;
import org.example.runner.AuctionRunnerImpl;

/**
 * Main class of the application
 */
public class App {

    /**
     * Product QU
     */
    private static final int INITIAL_QUANTITY = 20;

    /**
     * Cash limit (MU) for each bidder
     */
    private static final int INITIAL_CASH = 1000;


    public static void main(String[] args) {
        AuctionRunner runner = new AuctionRunnerImpl(INITIAL_QUANTITY, INITIAL_CASH);
        runner.run(new SimpleBidder(), new DynamicBidder());
    }
}
