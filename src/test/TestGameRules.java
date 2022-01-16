package test;
import internal.Assignment1.card.CardStacks;
import internal.Assignment1.card.ColoredUnoCard;
import internal.Assignment1.card.NumberUnoCard;
import internal.Assignment1.game.UnoGame;
import internal.Assignment1.game.Player;
import internal.Assignment1.game.Actions;
import internal.Assignment1.card.UnoCard;
import internal.Assignment1.card.NumberUnoCard.Number;
import internal.Assignment1.card.ColoredUnoCard.Color;
import internal.Assignment1.card.UnoCard.Type;
import org.junit.Test;
import java.util.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestGameRules {

    /**
     * Function provides an arbitrary game setup for other functions to use
     */
    public static UnoGame arbitraryGameSetUp() {

        String[] playerNames = {"James", "John", "Mark", "Matt"};
        UnoGame game = new UnoGame(playerNames);
        return game;
    }

    /**
     * Function provides a game where player have no cards
     */
    public static UnoGame emptyGameSetUp() {

        ArrayList<UnoCard> p0_cards = new ArrayList<UnoCard>();
        ArrayList<UnoCard> p1_cards = new ArrayList<UnoCard>();
        ArrayList<UnoCard> p2_cards = new ArrayList<UnoCard>();
        ArrayList<UnoCard> p3_cards = new ArrayList<UnoCard>();
        Player[] players = new Player[4];
        players[0] = new Player("James", p0_cards);
        players[1] = new Player("John", p1_cards);
        players[2] = new Player("Mark", p2_cards);
        players[3] = new Player("Mark", p3_cards);

        return new UnoGame(players, new CardStacks());
    }

    /**
     * Helper function which checks if a card is valid to be dropped or not by checking it against a card that is
     * added to the pile
     * @param droppedCard is the card that the user drops
     * @param addToPile is the card that the most recent card on the pile
     * @param colorPreference is number that the use must match if there is a wild card
     * @param numPreference is number that the use must match if there is a wild card
     */
    public static boolean addToPlayerAndPile(UnoCard droppedCard, UnoCard addToPile, Color colorPreference,
                                             Number numPreference) {

        UnoGame g = arbitraryGameSetUp();
        Player[] players = g.getPlayerOrder();

        ArrayList<UnoCard> playerCards_0 = players[0].getPlayerCards();
        playerCards_0.clear();
        playerCards_0.add(0, droppedCard);

        ArrayList<UnoCard> discardPile = g.getPileAndDeck().getPile();
        discardPile.add(0, addToPile);
        Actions action = new Actions(g);

        action.chooseColor(colorPreference);
        action.chooseNumber(numPreference); // in the case that the pile card dropped is a wild the color gets set
        action.dropPlayerCard(0, 0);

        return ((discardPile.get(0)).equals(droppedCard) && !playerCards_0.contains(droppedCard));
    }


    @Test
    /**
     * Tests whether if a player drops a valid card, then the card should be removed from the player's cards
     * and it should be added to the pile as the top most card
     */
    public void testCheckPlayerWon() {

        UnoGame g = arbitraryGameSetUp();
        Player[] players = g.getPlayerOrder();

        NumberUnoCard cardToDrop = new NumberUnoCard(Type.Number, Color.Red, Number.seven);
        ArrayList<UnoCard> player1Cards = g.getPlayerOrder()[1].getPlayerCards();
        player1Cards.clear();
        player1Cards.add(cardToDrop);

        ArrayList<UnoCard> player0cards = g.getPlayerOrder()[0].getPlayerCards();
        NumberUnoCard player0card = new NumberUnoCard(Type.Number, Color.Red, Number.four);
        player0cards.add(0, (UnoCard) player0card);

        ArrayList<UnoCard> pile = g.getPileAndDeck().getPile();
        NumberUnoCard pileCard = new NumberUnoCard(Type.Number, Color.Red, Number.three);
        pile.add(0, (UnoCard) pileCard);

        Actions action = new Actions(g);

        action.dropPlayerCard(1, 0);
        assertTrue("Player 2 should have all it's card finished: " , g.hasPlayerWon(1));
        action.dropPlayerCard(0, 0);
        assertTrue("Player 1 should not have all it's card finished as it has more cards to drop: " ,
                !g.hasPlayerWon(0));
    }


    @Test
    /**
     * Checks whether player card that gets dropped is valid when both the pile and dropped card are of the same color
     * and all cards for this scenario will be of different numbers or types from each other
     */
    public void testValidCardForSameColor() {

        UnoCard droppedCard = new NumberUnoCard(Type.Number,Color.Blue ,Number.seven);
        UnoCard pileCard =  new NumberUnoCard(Type.Number,Color.Blue ,Number.zero);
        assertTrue("Test for same color (Blue) ", addToPlayerAndPile(droppedCard, pileCard, null, null));

        droppedCard = new NumberUnoCard(Type.Number,Color.Red ,Number.seven);
        pileCard = new NumberUnoCard(Type.Number,Color.Blue ,Number.zero);
        assertFalse("Test for different color (Red) ", addToPlayerAndPile(droppedCard, pileCard, null, null));
    }

    @Test
    /**
     * Test if the card can be dropped when the cards are of different color but of the same value or same type
     */

    public void testValidCardForDifferentColor() {

        UnoCard droppedCard = new NumberUnoCard(Type.Number, Color.Blue, Number.one);
        UnoCard pileCard = new NumberUnoCard(Type.Number, Color.Red, Number.one);

        assertTrue("Test for same value but different color ", addToPlayerAndPile(droppedCard, pileCard,
                null, null));

        droppedCard = new ColoredUnoCard(Type.DrawTwo, Color.Red);
        pileCard = new ColoredUnoCard(Type.DrawTwo, Color.Blue);

        assertTrue("Test for same type but different color ", addToPlayerAndPile(droppedCard, pileCard,
                null, null));
    }

    @Test
    /**
     * Test if wild card or wild plus four can be dropped in any scenario
     */
    public void testValidCardForWild() {

        UnoCard droppedCard = new UnoCard(Type.Wild);
        UnoCard pileCard = new NumberUnoCard(Type.Number, Color.Green, Number.six);
        assertTrue("Test for wild card ", addToPlayerAndPile(droppedCard, pileCard, Color.Green, null));

        droppedCard = new UnoCard(Type.WildDrawFour);
        pileCard = new NumberUnoCard(Type.Number, Color.Red, Number.one);
        assertTrue("Test for wild draw four ", addToPlayerAndPile(droppedCard, pileCard, Color.Red, null));
    }

    @Test
    /**
     * Test if the most recent card is a wild card with the color specified, then the user's card should be able
     * to match it, if the card is of the correct color.
     */

    public void testWildCardColor() {

        // user will add a wild card to the card deck
        UnoCard droppedCard = new NumberUnoCard(Type.Number, Color.Red, Number.three);
        UnoCard pileCard = new UnoCard(Type.Wild);

        assertTrue("Test for wild card ", addToPlayerAndPile(droppedCard, pileCard, Color.Red, null));

        droppedCard = new ColoredUnoCard(Type.Skip, Color.Green);
        pileCard = new UnoCard(Type.WildDrawFour);

        assertTrue("Test for wild draw four ", addToPlayerAndPile(droppedCard, pileCard, Color.Green, null));

        droppedCard = new NumberUnoCard(Type.Number, Color.Blue, Number.seven);
        pileCard = new UnoCard(Type.Wild);

        assertFalse("Test for wild draw four ", addToPlayerAndPile(droppedCard, pileCard, Color.Red, null));

    }


    @Test
    /**
     * Test to see that when a player ends up picking a card from deck, if the card is valid, they will
     * drop that card down. Note that because the player will not be dropping any cards manually, the process
     * of dropping a card if the player chooses to pick a card will be done automatically. In the case of a wild
     * card, the program will automatically set the color to be that of the most common card that the player has.
     */
    public void testPlayerDropsValidCardOnPenalty() {

        UnoGame g = emptyGameSetUp();

        ArrayList<UnoCard> discardDeck = g.getPileAndDeck().getDeck();
        ArrayList<UnoCard> discardPile = g.getPileAndDeck().getPile();

        UnoCard topOfDeck = new UnoCard(Type.Wild);
        discardDeck.add(0, topOfDeck);
        discardPile.add(0, new NumberUnoCard(Type.Number, Color.Yellow, Number.one));

        UnoCard topPileCard = g.getPileAndDeck().getTopPileCard();
        Player currPlayer = g.getPlayerOrder()[0];
        ArrayList<UnoCard> playerCards = currPlayer.getPlayerCards();

        assertFalse("Current player should not have a valid card" , currPlayer.hasValidCard(topPileCard, null, null));
        if (!currPlayer.hasValidCard(topPileCard, null,null)) {

            Actions action = new Actions(g);
            action.pickUpCard(0, false);
            assertTrue("Player should be able to drop the picked card ", action.isPenaltyCardDroppable(0));

            Actions drop = new Actions(g);
            int lastIdx = g.getPlayerOrder()[0].getPlayerCards().size() - 1;
            drop.dropPlayerCard(0, lastIdx);
            drop.chooseColor(Color.Green);

            assertTrue("Player should not have top of deck card and it should be at top of pile :",
                    !playerCards.contains(topOfDeck) && discardPile.get(0).equals(topOfDeck));
            assertTrue("Color should be set to green: ", action.getColorFromWild().equals(Color.Green));

        }
    }

    @Test
    /**
     * Tests whether the skip cards works by verifying that the next player index will equal to 2 more than
     * the player index which dropped the skip card from the mov
     */
    public void testSkipCard() {

        UnoGame g = arbitraryGameSetUp();
        Actions action = new Actions(g);

        Player currPlayer = g.getPlayerOrder()[3];
        ArrayList<UnoCard> playerCards = currPlayer.getPlayerCards();
        ArrayList<UnoCard> pile = g.getPileAndDeck().getPile();
        UnoCard skipCard = new ColoredUnoCard(Type.Skip, Color.Red);
        UnoCard redPileCard = new NumberUnoCard(Type.Number, Color.Red, Number.six);

        playerCards.add(0, skipCard);
        pile.add(0, redPileCard);

        int nextPlayerIdx =  action.dropPlayerCard(3,  0);
        assertTrue("Player 0 should get skipped following Player 3's drop of a skip card :",nextPlayerIdx == 1);
    }

    @Test
    /**
     * Tests whether the skip cards works by checking if a copy of the reversed array is equal to reverse()
     * on the original array. Also checks that the correct next player is being approached.
     */
    public void testReverse() {

        UnoGame g = arbitraryGameSetUp();
        Player[] players = g.getPlayerOrder();

        Player[] copy = Arrays.copyOf(players, 4);
        List<Player> playersList = Arrays.asList(copy);
        Collections.reverse(playersList);
        playersList.toArray(copy);

        Player p2 = g.getPlayerOrder()[2];
        ArrayList<UnoCard> playerCards = p2.getPlayerCards();
        ArrayList<UnoCard> pile = g.getPileAndDeck().getPile();
        UnoCard reverseCard = new ColoredUnoCard(Type.Reverse, Color.Blue);
        UnoCard bluePileCard = new NumberUnoCard(Type.Number, Color.Blue, Number.six);

        playerCards.add(0, reverseCard);
        pile.add(0, bluePileCard);

        Actions action = new Actions(g);
        int nextPlayerIdx =  action.dropPlayerCard(2, 0);

        assertTrue("Following reverse, the index should go to 3:", 3 == nextPlayerIdx);
        assertTrue("The array should be reverse from the original :", Arrays.equals(copy, g.getPlayerOrder()));
    }

    @Test
    /**
     * Tests to ensure that a draw two card works and that if adjacent players have draw two cards then, they should
     * all properly stack among each other and make the first adjacent player without a draw to draw those many cards.
     */
    public void testDrawTwoWithStacking() {

        UnoGame g = emptyGameSetUp();
        int old_size = (g.getPlayerOrder()[2]).getPlayerCards().size();

        ArrayList<UnoCard> p3_cards = g.getPlayerOrder()[3].getPlayerCards();
        ArrayList<UnoCard> p0_cards = g.getPlayerOrder()[0].getPlayerCards();
        ArrayList<UnoCard> p1_cards = g.getPlayerOrder()[1].getPlayerCards();

        UnoCard drawTwo = new ColoredUnoCard(Type.DrawTwo, Color.Green);
        p3_cards.add(0, drawTwo);
        p0_cards.add(0, drawTwo);
        p1_cards.add(0, drawTwo);

        ArrayList pile = g.getPileAndDeck().getPile();
        UnoCard greenPileCard = new NumberUnoCard(Type.Number, Color.Green, Number.six);

        pile.add(0, greenPileCard);
        Actions action = new Actions(g);
        int playerIdx = 3;

        while (g.getPlayerOrder()[playerIdx].hasCardType(Type.DrawTwo) != -2) {
            playerIdx =  action.dropPlayerCard(playerIdx, 0);
        }

        int nextPlayerIdx = action.pickUpCard(playerIdx, true);
        int new_size = (g.getPlayerOrder()[playerIdx]).getPlayerCards().size();

        assertTrue("Next index should be 3: ", nextPlayerIdx == 3);
        assertTrue("Test to see that player 2 (index 1) gets 6 cards added to its deck: ",
                (new_size - old_size) == 6);
    }


    @Test
    /**
     * Tests to ensure that the wildDrawFour card is functioning properly. This is done through checking that
     * when players with consecutive draw four cards will all successfully place them. Additionally, because
     * there is no user input for this assignment, the process for dropping a wild plus four card will be automated
     * if an adjacent player has one. The color that the user will declare will also be automated entirely as well.
     */
    public void testWildDrawFourWithStacking() {

        UnoGame g = arbitraryGameSetUp();
        int old_size = (g.getPlayerOrder()[1]).getPlayerCards().size();

        ArrayList<UnoCard> p3_cards = g.getPlayerOrder()[3].getPlayerCards();
        ArrayList<UnoCard> p0_cards = g.getPlayerOrder()[0].getPlayerCards();

        UnoCard drawFourCard = new ColoredUnoCard(Type.WildDrawFour, Color.Green);

        p3_cards.add(0, drawFourCard);
        p0_cards.add(0, drawFourCard);

        Actions action = new Actions(g);
        int playerIdx = 3;

        while (g.getPlayerOrder()[playerIdx].hasCardType(Type.WildDrawFour) != -2) {
            playerIdx =  action.dropPlayerCard(playerIdx, 0);
            action.chooseColor(Color.Red);
        }

        assertTrue("Color choice should be Red: ", action.getColorFromWild().equals(Color.Red));
        int nextPlayerIdx = action.pickUpCard(playerIdx, true);
        int new_size = (g.getPlayerOrder()[playerIdx]).getPlayerCards().size();

        assertTrue("Test to see that player 2 (index 1) gets 8 cards added to its deck: ",
                (new_size - old_size) == 8);
        assertTrue("Next player index should be player 2 (index 1): ", nextPlayerIdx == 2);
    }

    @Test
    /**
     * Tests to ensure that when dropping a numerical card, the next play to play is the adjacent player to the
     * original player in a clockwise direction.
     */
    public void testNumberCardMoveToNextPlayerIdx() {

        UnoGame g = arbitraryGameSetUp();
        Player[] player = g.getPlayerOrder();
        player[0].getPlayerCards().add(0, new NumberUnoCard (Type.Number, Color.Green, Number.two));

        Actions action = new Actions(g);
        action.getCurrGame().getPileAndDeck().getPile().add(0,new NumberUnoCard(Type.Number, Color.Green, Number.three));
        int nextPlayerIdx = action.dropPlayerCard(0, 0);
        System.out.println(nextPlayerIdx);
        assertTrue("Test to see if the next index goes back to player 2 (index 1): ",
                nextPlayerIdx == 1);
    }

    @Test
    /**
     * Tests to ensure that when dropping a "Wild" card, the next play to play is the adjacent player to the
     * original player in a clockwise direction.
     */
    public void testWildCardMoveToNextPlayerIdx() {

        UnoGame g = arbitraryGameSetUp();
        Player[] player = g.getPlayerOrder();
        player[0].getPlayerCards().add(0, new UnoCard (Type.Wild));

        Actions action = new Actions(g);
        int nextPlayerIdx =  action.dropPlayerCard(0, 0);
        action.chooseColor(Color.Red);

        assertTrue("Test to see if the next index goes back to player 2 (index 1): ",
                nextPlayerIdx == 1);
    }
    
}

