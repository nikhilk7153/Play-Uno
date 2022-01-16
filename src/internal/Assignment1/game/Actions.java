package internal.Assignment1.game;
import internal.Assignment1.card.NumberUnoCard.Number;
import internal.Assignment1.card.UnoCard;
import internal.Assignment1.card.UnoCard.*;
import internal.Assignment1.card.ColoredUnoCard.*;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

/**
* This class is used for updating the game state of a class after a card has been dropped by a player.
* Specifically, once the user drops a card, this class will automate the game state to update choices
* such as reversing the player order when a reverse card is dropped, skipping the next player's turn, and
* making the adjacent player draw two or four cards depending on the card that is dropped or automatically make the
* adjacent player drop their draw two or four card, if they have one.
*/

public class Actions {

    private static UnoGame currGame_; // game upon which actions are being invoked
    private static Color colorFromWild_; // indicates the color of the card
    private int numToDraw_; // number of cards to draw
    private Number numFromWild_; // gives the number

    /**
     * Constructs the action using the game which is a parameter on which the action is invoked on
     * @param game is the game that you are acting on
     */
    public Actions(UnoGame game) {

        currGame_ = game;
        colorFromWild_ = null;
        numFromWild_ = null;
        numToDraw_ = 0;
    }

     /**
     * Picks up cards for the player
     * @param playerIdx is the index of the player
     * @param fromStack tells whether to draw the stack cards or not
     * @return
     */ 

    public int pickUpCard(int playerIdx, boolean fromStack) {

        Player user = currGame_.getPlayerOrder()[playerIdx];
        if (fromStack) {
            currGame_.pickUpCards(numToDraw_, user);
            numToDraw_ = 0;
        } else {
            currGame_.pickUpCards(1, user);
        }

        return findAction(Type.Number, playerIdx);
    }

    /**
     * Drops cards for the user 
     * @param playerIdx is the index of the player based on the player order sequence
     * @param cardIdx is the index of the card in the player's suite of cards
     */

    public int dropPlayerCard(int playerIdx, int cardIdx) {

        Player user = currGame_.getPlayerOrder()[playerIdx];
        UnoCard playerCard = user.getPlayerCards().get(cardIdx);
        Type userCardType = playerCard.getType();
        UnoCard topPileCard = currGame_.getPileAndDeck().getTopPileCard();

        if (Rules.isValidCard(topPileCard,playerCard, colorFromWild_, numFromWild_)) {
            currGame_.dropCard(user, cardIdx, colorFromWild_, numFromWild_);

        } else {
            return -1;
        }
        return findAction(userCardType, playerIdx);
    }

    /** Based on the card type, find the appropriate action, update the game state,
     * and return the index of the next player that will be playing.
     * @param cardType is the type of the card
     * @param playerIdx is the index of the player by sequence
     */
    private int findAction(Type cardType, int playerIdx) {

        Player[] playersList = currGame_.getPlayerOrder();

        switch (cardType) {
            case Reverse:
                reverse(playersList);
                playerIdx = playersList.length - 1 - playerIdx;
            case Skip:
                playerIdx += 1;
            case WildDrawFour:
                addNumToDraw(4);
                break;
            case DrawTwo:
                addNumToDraw(2);
                break;
        }
        return computeNextIdx(playerIdx, playersList.length);
    }


    /**
    * Checks whether the card that a player chooses to draw on a penalty can be played instead of skipping their turn
     * will return whether the card of the player is droppable or not
     * @param playerIdx is the index of the player that is playing based on the playing order
     */
    public boolean isPenaltyCardDroppable(int playerIdx) {

        Player user = currGame_.getPlayerOrder()[playerIdx];
        int lastIndex = user.getPlayerCards().size() - 1;
        UnoCard drawnCard = user.getPlayerCards().get(lastIndex);
        UnoCard topPileCard = currGame_.getPileAndDeck().getTopPileCard();

        return (Rules.isValidCard(topPileCard, drawnCard, colorFromWild_, numFromWild_));
    }

    /**
     * Sets the color that the user wants other players to drop
     * @param choice is the sequence of players and their turns
     */
    public void chooseColor(Color choice) {

        UnoCard topPileCard = currGame_.getPileAndDeck().getTopPileCard();

        if (topPileCard.getType() == Type.Wild || topPileCard.getType() == UnoCard.Type.WildDrawFour) {
            colorFromWild_ = choice;
        } else {
            colorFromWild_ = null;
        }
    }


    /**
     * Reverse the playing order
     * @param playerOrder is the sequence of players and their turns
     */
    private void reverse(Player[] playerOrder) {

        List<Player> playersList = Arrays.asList(playerOrder);
        Collections.reverse(playersList);
        playersList.toArray(playerOrder);
    }

    /**
     * computes the index of the next player that will be playing in the array
     * returns the next index of the player that will play
     * @param playerIdx is the current index of the player
     * @param arrayLength is the length of the players list
     */
    private int computeNextIdx(int playerIdx, int arrayLength) {

        return (playerIdx + 1) % arrayLength;
    }

    /**
     * Returns the number of cards that have been stacked that the player has to draw
     */
    public int getNumToDraw() {

        return numToDraw_;
    }

    /**
    * Adds to the total number of cards to draw
    * @addToDraw gives the number of cards add to the stack
    */
    public void addNumToDraw(int addToDraw) {

        numToDraw_ += addToDraw;
    }

    /**
     * Returns the color when dropping a wild card
     */
    public Color getColorFromWild() {

        return colorFromWild_;
    }

    /**
     * when dropping a wild card, a player can only choose the number or color choice and this will check that
     * @param choice is the color choice of the user
     */
    public void chooseNumber(Number choice) {

        UnoCard topPileCard = currGame_.getPileAndDeck().getTopPileCard();

        if (topPileCard.getType() == Type.Wild || topPileCard.getType() == UnoCard.Type.WildDrawFour) {
            numFromWild_ = choice;
        } else {
            numFromWild_ = null;
        }
    }

   /**
    * Function decides on whether or not to reverse when player has dropped a wild card
    */
   public int setReverse(boolean shouldReverse, Player[] array, int playerIdx) {

       UnoCard topPileCard =  currGame_.getPileAndDeck().getTopPileCard();

       if ((topPileCard.getType() == Type.Wild || topPileCard.getType() == UnoCard.Type.WildDrawFour) &&
       shouldReverse) {
           reverse(array);
           return computeNextIdx(playerIdx, array.length);
       }
       return -1;
   }

    /**
     * Returns the color chosen by the user when dropping a wild card
     */
    public Number getNumFromWild() {

        return numFromWild_;
    }

    /**
     * Returns the game that the action is being invoked on
     */
    public UnoGame getCurrGame() {

        return currGame_;
    }

}
