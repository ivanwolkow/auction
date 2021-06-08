package org.example.bidder;

import org.example.exception.BidderNotInitializedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DynamicBidderTest {

    private Bidder bidder;

    @BeforeEach
    public void setUp() {
        bidder = new DynamicBidder();
    }

    @Test
    public void bidderShouldBeInitializedBeforeBidsCallTest() {
        assertThrows(BidderNotInitializedException.class, () -> bidder.bids(1, 1));
    }

    @Test
    public void bidderShouldBeInitializedBeforePlaceBidCallTest() {
        assertThrows(BidderNotInitializedException.class, () -> bidder.placeBid());
    }

    @Test
    public void illegalOwnBidOnBidsCallTest() {
        bidder.init(10, 100);
        assertThrows(IllegalArgumentException.class, () -> bidder.bids(-1, 10));
    }

    @Test
    public void illegalOtherBidOnBidsCallTest() {
        bidder.init(10, 100);
        assertThrows(IllegalArgumentException.class, () -> bidder.bids(10, -1));
    }

    @Test
    public void firstBidTest() {
        var quantity = 10;
        var cash = 100;
        var firstBid = cash / quantity;

        bidder.init(quantity, cash);

        assertEquals(firstBid, bidder.placeBid());
    }

    @Test
    public void biddingStrategyTest() {
        var quantity = 10;
        var cash = 100;

        bidder.init(quantity, cash);

        /* Competing with an opponent that just doubles our last bid */
        bidder.bids(10, 10);
        bidder.bids(10, 20);
        bidder.bids(21, 20);
        bidder.bids(43, 42);

        assertEquals(87, bidder.placeBid());
    }
}