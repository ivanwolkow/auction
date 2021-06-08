package org.example.runner;

import com.google.common.base.Preconditions;
import org.example.bidder.Bidder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuctionRunnerImpl implements AuctionRunner {

    private static final Logger logger = LoggerFactory.getLogger(AuctionRunnerImpl.class);

    /**
     * Initial product quantity
     */
    private final int initQuantity;
    /**
     * Cash limit for each party
     */
    private final int initCash;

    public AuctionRunnerImpl(int initQuantity, int initCash) {
        this.initQuantity = initQuantity;
        this.initCash = initCash;
    }

    public void run(Bidder bidder1, Bidder bidder2) {
        logger.info("Starting auction, product quantity: {}, cash: {}", initQuantity, initCash);
        logger.info("Bidder 1 = {}\tBidder 2 = {}\n",
                Preconditions.checkNotNull(bidder1, "Bidder 1 is null").getClass().getSimpleName(),
                Preconditions.checkNotNull(bidder2, "Bidder 2 is null").getClass().getSimpleName());

        var roundCounter = 0;
        var productQuantityRemained = initQuantity;

        var bidder1Quantity = 0;
        var bidder2Quantity = 0;

        var bidder1Cash = initCash;
        var bidder2Cash = initCash;

        bidder1.init(initQuantity, initCash);
        bidder2.init(initQuantity, initCash);

        while (productQuantityRemained > 1) {
            productQuantityRemained -= 2;
            roundCounter++;

            int bid1 = bidder1.placeBid();
            int bid2 = bidder2.placeBid();

            Preconditions.checkArgument(bid1 >= 0, "Bidder 1: A bid cannot be a negative value");
            Preconditions.checkArgument(bid2 >= 0, "Bidder 2: A bid cannot be a negative value");
            Preconditions.checkArgument(bid1 <= bidder1Cash, "Bidder 1: Not enough cash");
            Preconditions.checkArgument(bid2 <= bidder2Cash, "Bidder 2: Not enough cash");

            bidder1Cash -= bid1;
            bidder2Cash -= bid2;

            if (bid1 > bid2) {
                bidder1Quantity += 2;
            } else if (bid2 > bid1) {
                bidder2Quantity += 2;
            } else {
                bidder1Quantity++;
                bidder2Quantity++;
            }

            bidder1.bids(bid1, bid2);
            bidder2.bids(bid2, bid1);

            logger.info("Round {} result: BID1={}\tBID2={}", roundCounter, bid1, bid2);
            logger.info("Bidder 1 state: QU={} MU={}",
                    bidder1Quantity,
                    bidder1Cash);
            logger.info("Bidder 2 state: QU={} MU={}\n",
                    bidder2Quantity,
                    bidder2Cash);
        }

        logger.info("Auction ended!");
    }
}
