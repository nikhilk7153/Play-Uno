package internal.Assignment1.game;

import internal.Assignment1.card.ColoredUnoCard;
import internal.Assignment1.card.UnoCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StrategicAIPlayer extends Player {

    /**
     * Initializes the player by their name and list of Uno Cards given to them
     * @param name is the name for the plaeyr
     * @param player_cards are the player cards for the player
     */
    public StrategicAIPlayer(String name, ArrayList<UnoCard> player_cards) {

        super(name, player_cards);
    }

    /**
     * The name of the AIPlayer
     * @param name is the name for the AI player
     */
    public StrategicAIPlayer(String name) {

        super(name);
    }

    /**
     * Returns the most common color after having a hash map of the count for each color
     * @param countColor is a hashMap which gives the count of each color
     */
    private ColoredUnoCard.Color argMaxColor(HashMap<ColoredUnoCard.Color, Integer> countColor) {

        int max = Integer.MIN_VALUE;
        ColoredUnoCard.Color bestColor = null;

        for (Map.Entry<ColoredUnoCard.Color, Integer> entry : countColor.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                bestColor = entry.getKey();
            }
        }

        if (max == 0) {
            bestColor = ColoredUnoCard.Color.Yellow;
        }

        return bestColor;
    }

}
