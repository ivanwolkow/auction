# Trading bot
This app consists of:
* two trading bot algorithm implementations
* auction engine which is able to create a playground environment with two trading bots competing with each other.  

## Problem description
A product x QU (quantity units) will be auctioned under 2 parties. The parties have each y MU (monetary units)
for auction. They offer then simultaneously an arbitrary number of its MU on the first 2 QU of the product.
After that, the bids will be visible to both. The 2 QU of the product is awarded to who has offered the most MU;
if both bid the same, then both get 1 QU. Both bidders must pay their amount - including the defeated. A bid of
0 MU is allowed. Bidding on each 2 QU is repeated until the supply of x QU is fully auctioned. Each bidder aims
to get a larger amount than its competitor.  

## Win condition 
In an auction wins the trading bot that is able to get more QU than the other. With a tie, the bot that retains
more MU wins.

## Build and run
Build fat jar and run it:
```bash
./mvnw clean install
java -jar target/auction-1.0-SNAPSHOT-jar-with-dependencies.jar
```

After following the instruction above, you'll see the program output demonstrating two strategies competing with each other over a few rounds. After each round the bids of two parties are printed out. Also the QU (quantity units) and MU (monetary units) for each party are shown.
