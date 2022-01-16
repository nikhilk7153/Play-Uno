package internal.Assignment1.game;
import internal.Assignment1.card.NumberUnoCard;
import internal.Assignment1.card.UnoCard;
import internal.Assignment1.card.UnoCard.*;
import java.util.ArrayList;
import internal.Assignment1.card.ColoredUnoCard.Color;

/**
* The purpose of this class is to create a player that can play Uno by initializing the player with their name
* and their cards. The class contains internal information on details that include getting access to the player name
* and their cards along with removing a card and checking if the player has no more cards left. Additionally,
* this class also helps with finding the color of the card that is most common in the player's suite of cards.
*/

public class Player {

    protected String name_; // name of the player
    protected ArrayList<UnoCard> playerCards_; // cards that the player has

    /**
     * Initializes the player by their name and list of Uno Cards given to them
     * @name gives the name of the person
     * @param player_cards are the player cards for the player
     */
    public Player(String name, ArrayList<UnoCard> player_cards) {

        name_ = name;
        playerCards_ = player_cards;
    }

    /**
     * Initializes a player but just their name
     * @name gives the name
     */
    public Player(String name) {

        name_ = name;
    }

    /**
     * Returns the player's name
     */
    public String getName() {

        return name_;
    }

    /**
     * Returns the player's cards
     */
    public ArrayList<UnoCard> getPlayerCards() {

        return playerCards_;
    }

    /**
     * Removes the card from a player's deck based on the index of the card in the list of the player's cards
     * @param cardIdx is the index of the card to be removed
     */
    public void removeCardIdx(int cardIdx) {

        if (cardIdx < 0 || cardIdx >= playerCards_.size()) {
            throw new IllegalArgumentException("Not a valid card index");
        }
        playerCards_.remove(cardIdx);
    }


    /**
     * This function helps with checking if the user has a card that they can play based on the current deck
     * or not by returning true or false
     * @param cardToMatch is the card that is needed to be matched
     * @param wildColorChoice is color choice of the player
     * @param wildNumberChoice is the number choice of the player
     */
    public boolean hasValidCard(UnoCard cardToMatch, Color wildColorChoice, NumberUnoCard.Number wildNumberChoice) {

        for (UnoCard playerCard : playerCards_) {

            if (Rules.isValidCard(cardToMatch, playerCard, wildColorChoice, wildNumberChoice)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether or not the user has that specific card type or not
     * @param typeToMatch is the Type of the card to match
     * Returning -3 indicates that the player does not have the card in question
     */
    public int hasCardType(Type typeToMatch) {

        for (int i = 0; i < playerCards_.size(); i++) {
            UnoCard playerCard = playerCards_.get(i);

            if (playerCard.getType().equals(typeToMatch)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Gets the most common color that the player has
     * @param recentInfo gets the most recent information about the top most card in Pile
     */
    public ArrayList<UnoCard> getValidCards(Actions recentInfo) {

        Color colorFromWild = recentInfo.getColorFromWild();
        NumberUnoCard.Number numFromWild = recentInfo.getNumFromWild();
        UnoCard cardToMatch = recentInfo.getCurrGame().getPileAndDeck().getTopPileCard();

        ArrayList<UnoCard> validCards = new ArrayList<UnoCard>();

        for (UnoCard playerCard : playerCards_) {
            if (Rules.isValidCard(cardToMatch, playerCard, colorFromWild, numFromWild)) {
                validCards.add(playerCard);
            }
        }
        return validCards;
    }

    /**
     * Checks if the top most card is a draw two or draw four card and returns an integer representing
     * the index of the card in the player's suite or -1/-2 if the top card is not a draw card/ player does not have
     * the draw card
     * @param recentInfo is the current game
     */
    public int isTopCardDraw(Actions recentInfo) {

        UnoGame currGame = recentInfo.getCurrGame();
        UnoCard.Type topCardType = currGame.getPileAndDeck().getTopPileCard().getType();

        if (topCardType == UnoCard.Type.DrawTwo || topCardType == UnoCard.Type.WildDrawFour) {
            int cardIdx = hasCardType(topCardType);
            return cardIdx;
        } else {
            return -2;
        }
    }

}

