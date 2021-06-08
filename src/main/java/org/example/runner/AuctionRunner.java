package org.example.runner;

import org.example.bidder.Bidder;

public interface AuctionRunner {

    /**
     * Create an auction with two bidding strategies competing
     *
     * @param bidder1 First bidding strategy
     * @param bidder2 Second bidding strategy
     */
    void run(Bidder bidder1, Bidder bidder2);
}
