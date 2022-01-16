package test;

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

/**
 * This class used for testing the special rules that have been
 */

public class TestSpecialRules {

    @Test
    /**
     * Tests to make sure that when a wild card is dropped and the number is set, that the following card that gets
     * dropped is a card that the wild card specified.
     */
    public void testWildCardNumber() {

        UnoCard droppedCard = new NumberUnoCard(Type.Number, Color.Red, Number.seven);
        UnoCard pileCard = new UnoCard(Type.Wild);
        assertTrue("Test for wild card ", TestGameRules.addToPlayerAndPile(droppedCard, pileCard,null, Number.seven));

        droppedCard = new NumberUnoCard(Type.Number, Color.Green, Number.four);
        pileCard = new UnoCard(Type.WildDrawFour);
        assertTrue("Test for wild draw four ", TestGameRules.addToPlayerAndPile(droppedCard, pileCard, null, Number.four));

        droppedCard = new NumberUnoCard(Type.Number, null, Number.seven);
        pileCard = new UnoCard(Type.Wild);
        assertFalse("Test for wild draw four ", TestGameRules.addToPlayerAndPile(droppedCard, pileCard, null, Number.two));

    }

    @Test
    /**
     * Tests to make sure that when a wild card is dropped and the number is set, that the following card that gets
     * dropped is a card that the wild card specified.
     */
    public void testWildReverse() {

        UnoGame g = TestGameRules.arbitraryGameSetUp();
        Player[] players = g.getPlayerOrder();

        Player[] copy = Arrays.copyOf(players, 4);
        List<Player> playersList = Arrays.asList(copy);
        Collections.reverse(playersList);
        playersList.toArray(copy);

        Player p2 = g.getPlayerOrder()[2];
        ArrayList<UnoCard> playerCards = p2.getPlayerCards();
        ArrayList<UnoCard> pile = g.getPileAndDeck().getPile();
        UnoCard wildCard = new UnoCard(Type.WildDrawFour);

        UnoCard bluePileCard = new NumberUnoCard(Type.Number, Color.Blue, Number.six);
        playerCards.add(0, wildCard);
        pile.add(0, bluePileCard);

        Actions action = new Actions(g);
        action.dropPlayerCard(2, 0);
        int nextPlayerIdx = action.setReverse(true, g.getPlayerOrder(), 2);

        assertTrue("Following reverse, the index should go to 3:", 3 == nextPlayerIdx);
        assertTrue("The array should be reverse from the original :", Arrays.equals(copy, g.getPlayerOrder()));
    }

}

