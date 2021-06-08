package org.example.exception;

public class BidderNotInitializedException extends IllegalStateException {
    public BidderNotInitializedException() {
        super("The bidder is not initialized!");
    }
}
