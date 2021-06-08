package org.example.bidder;

import org.example.exception.BidderNotInitializedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleBidderTest {

    private Bidder bidder;

    @BeforeEach
    public void setUp() {
        bidder = new SimpleBidder();
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
        var firstBid = (cash / quantity) + 5;

        bidder.init(quantity, cash);

        assertEquals(firstBid, bidder.placeBid());
    }

    @Test
    public void biddingStrategyTest() {
        var quantity = 10;
        var cash = 100;

        bidder.init(quantity, cash);

        /* Competing with an opponent that increases it's last bid by 1 */
        bidder.bids(15, 10);
        bidder.bids(15, 11);
        bidder.bids(16, 12);

        assertEquals(17, bidder.placeBid());
    }
}