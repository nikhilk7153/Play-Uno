package test;

import internal.Assignment1.card.CardStacks;

import internal.Assignment1.card.ColoredUnoCard;
import internal.Assignment1.game.Actions;
import internal.Assignment1.game.UnoGame;
import internal.Assignment1.game.Player;
import internal.Assignment1.card.UnoCard;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import internal.Assignment1.card.NumberUnoCard.Number;
import internal.Assignment1.card.NumberUnoCard;
import internal.Assignment1.card.ColoredUnoCard.Color;
import internal.Assignment1.card.UnoCard.Type;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestGameSetup {

    /**
     * An arbitrary game set up to simply test all the player card actions in this code base.
     * This function will return a game that other methods build upon.
     */

    public UnoGame arbitraryGameSetUp() {

        String[] playerNames = {"James", "John", "Mark", "Matt"};

        UnoGame game = new UnoGame(playerNames);

        return game;

    }

    @Test
    /**
     * Purpose of the first test is to see if the game create all cards properly
     */
    public void testInitialCardDeck() {

        CardStacks deckAndPile = new CardStacks();

        ArrayList<UnoCard> deck_ = deckAndPile.getDeck();
        ArrayList<UnoCard> pile_ = deckAndPile.getPile();

        assertEquals("Total number of cards: ",  108, deck_.size() + pile_.size());

    }

    @Test
    /**
     * Test if the players are properly assigned to the game.
     */
    public void testProperPlayerAssignmentOfPlayerName() {

        String[] playerNames = {"James", "John", "Mark", "Matt"};
        UnoGame game = arbitraryGameSetUp();
        Player[] players =  game.getPlayerOrder();

        String[] playerNamesFromList = new String[players.length];

        for (int i = 0; i < players.length; i++) {
            playerNamesFromList[i] = players[i].getName();
        }

        List<String> playerNamesList =  Arrays.asList(playerNamesFromList);

        for (int i = 0; i < players.length; i++) {
            assertTrue("Check if player names are properly contained Players array: " , playerNamesList.contains(playerNames[i]));
        }

    }


    @Test
    /**
     * Test if players get assigned properly with a proper assignment of cards
     */
    public void testProperPlayerAssignmentOfCards() {

        UnoGame g = arbitraryGameSetUp();
        Player[] players = g.getPlayerOrder();

        for (int i = 0; i < players.length; i++) {
            assertEquals("Number of cards per player at the beginning: ",  7,  players[i].getPlayerCards().size());
        }

        CardStacks pileAndDeck =  g.getPileAndDeck();

        ArrayList<UnoCard> pile = pileAndDeck.getPile();
        ArrayList<UnoCard> deck = pileAndDeck.getDeck();

        assertEquals("Number of cards in the deck at the beginning: ",  79, deck.size());
        assertEquals("Number of cards in the pile at the beginning: ",  1, pile.size());
    }

    @Test
    /**
     * test to see that player can successfully remove a card from the deck and get it into his card suite:
     */
    public void canRemoveFromDeck() {

        UnoGame game = arbitraryGameSetUp();
        Player[] players = game.getPlayerOrder();
        Actions pickCard = new Actions(game);

        UnoCard topCard = game.getPileAndDeck().getDeck().get(0);
        UnoCard secondCard = game.getPileAndDeck().getDeck().get(1);

        pickCard.pickUpCard(0, false);

        assertTrue("Check if Player has received the card: ", players[0].getPlayerCards().contains(topCard));
        assertTrue("Check if deck has its only card remaining: ", game.getPileAndDeck().getDeck().get(0).equals(secondCard));
    }

    @Test
    /**
     * check to see if the player can get a card from an initially empty deck properly
     */
    public void removeCardFromEmptyDeck() {

        ArrayList<UnoCard> p1_cards = new ArrayList<UnoCard>();
        ArrayList<UnoCard> p2_cards = new ArrayList<UnoCard>();

        p1_cards.add(new NumberUnoCard(Type.Number, Color.Red, Number.seven));
        p1_cards.add(new ColoredUnoCard(Type.Skip, Color.Green));
        p2_cards.add(new ColoredUnoCard(Type.DrawTwo, Color.Blue));

        Player drawer = new Player("John", p1_cards);
        Player player2 = new Player("Andy", p2_cards);
        Player[] players = {drawer, player2};

        ArrayList<UnoCard> deck = new ArrayList<UnoCard>();
        ArrayList<UnoCard> pile = new ArrayList<UnoCard>();

        pile.add(new NumberUnoCard(Type.Number, Color.Blue, Number.nine));
        pile.add(new NumberUnoCard(Type.Number, Color.Yellow, Number.eight));
        pile.add(new NumberUnoCard(Type.Number, Color.Red, Number.six));

        CardStacks deckAndPile = new CardStacks(deck, pile); // empty deck

        UnoGame g = new UnoGame(players, deckAndPile);
        UnoCard topPileCard = g.getPileAndDeck().getTopPileCard();
        Actions pick = new Actions(g);
        pick.pickUpCard(0, false);

        assertTrue("Check that the pile still has the topmost card: " , pile.contains(topPileCard));
        assertTrue("Check that deck has the other card: ", deck.size() == 1);
        assertTrue("Check that player has one more card : ", players[0].getPlayerCards().size() == 3);
    }

    @Test

    /**
     * Tests whether if a player drops a valid card, then the card should be removed from the player's cards
     * and it should be added to the pile as the top most card. This will also check, then whether
     * the next card can be added or not.
     */
    public void testPlayerCanDropIntoPile() {

        UnoGame g = arbitraryGameSetUp();

        Player[] players = g.getPlayerOrder();
        Actions action = new Actions(g);

        ArrayList<UnoCard> player0Cards =  players[0].getPlayerCards();

        player0Cards.clear();
        NumberUnoCard cardToDrop = new NumberUnoCard(Type.Number, Color.Red, Number.seven);
        player0Cards.add(0, cardToDrop);

        ArrayList<UnoCard> pile = g.getPileAndDeck().getPile();
        NumberUnoCard redFive = new NumberUnoCard(Type.Number, Color.Red, Number.five);
        pile.add(0,redFive);

        action.dropPlayerCard(0, 0);

        assertTrue("Top pile card should be the card that player 0 has dropped ", pile.get(0).equals(cardToDrop));
        assertTrue("Player 0 should no longer have a Uno card that is red with number 6 with him",
                !player0Cards.contains(cardToDrop));

    }

}

