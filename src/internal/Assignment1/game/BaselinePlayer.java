package internal.Assignment1.game;
import internal.Assignment1.card.ColoredUnoCard;
import internal.Assignment1.card.UnoCard;
import java.util.*;
import internal.Assignment1.card.UnoCard.*;
import java.util.ArrayList;
import internal.Assignment1.card.ColoredUnoCard.Color;
import internal.Assignment1.card.NumberUnoCard.Number;


public class BaselineAIPlayer extends Player {

    /**
     * Initializes the player by their name and list of Uno Cards given to them
     * @param name is the name for the plaeyr
     * @param player_cards are the player cards for the player
     */
    public BaselineAIPlayer(String name, ArrayList<UnoCard> player_cards) {

        super(name, player_cards);
    }

    /**
     * The name of the AIPlayer
     * @param name is the name for the AI player
     */
    public BaselineAIPlayer(String name) {

        super(name);
    }



    /**
     * The function implements baseline AI by going through the users cards and randomly choosing one of them
     * @param recentInfo provides the function with the most recent information about the card
     */
    public int dropRandomCardIdx(Actions recentInfo) {

        int cardIdx = isTopCardDraw(recentInfo);

        if (cardIdx > -2) {
            return cardIdx;
        }

        ArrayList<UnoCard> validCards = getValidCards(recentInfo);
        Random rand = new Random();
        cardIdx = rand.nextInt(validCards.size());
        return cardIdx;
    }

    /**
     * Implements strategic AI by dropping the card whose color the user has the most of
     * @param recentInfo returns the most recent information about that card that has been dropped
     * -1 indicates that the user must draw all the cards in num stacks
     * -3 indicates that the user has no valid card to drop
     *  A returned value greater than 0 indicates the index of the card that will be dropped
     */
    public int dropBestCardIdx(Actions recentInfo) {

        if (isTopCardDraw(recentInfo) != -2) {
            return isTopCardDraw(recentInfo);
        }

        ArrayList<UnoCard> validCards = getValidCards(recentInfo);
        Color bestColor = mostCommonColor();

        for (int i = 0; i < validCards.size(); i++) {

            UnoCard card = validCards.get(i);

            if (card instanceof ColoredUnoCard && ((ColoredUnoCard) card).getColor() == bestColor) {
                return i;
            }
        }

        if (hasCardType(Type.Wild) != -1) {
            return hasCardType(Type.Wild);
        } else if (hasCardType(Type.WildDrawFour) != -1) {
            return hasCardType(Type.WildDrawFour);
        }

        return -3;
    }

    /**
     * The following function helps with getting the most common color that a user has
     */
    public Color mostCommonColor() {

        HashMap<Color, Integer> countColor = new HashMap<Color, Integer>();

        for (Color color : Color.values()) {
            countColor.put(color, 0);
        }

        for (UnoCard playerCard : playerCards_) {
            if (playerCard instanceof ColoredUnoCard) {
                switch (((ColoredUnoCard) playerCard).getColor()) {
                    case Green:
                        countColor.replace(Color.Green, countColor.get(Color.Green) + 1);
                    case Blue:
                        countColor.replace(Color.Blue, countColor.get(Color.Blue) + 1);
                    case Yellow:
                        countColor.replace(Color.Yellow, countColor.get(Color.Yellow) + 1);
                    case Red:
                        countColor.replace(Color.Red, countColor.get(Color.Red) + 1);
                }
            }
        }
        return Color.Yellow;
    }

}


