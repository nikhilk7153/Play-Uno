package internal.Assignment1.game;
import internal.Assignment1.card.ColoredUnoCard;
import internal.Assignment1.card.NumberUnoCard;
import internal.Assignment1.card.NumberUnoCard.Number;
import internal.Assignment1.card.UnoCard;
import internal.Assignment1.card.ColoredUnoCard.Color;
import internal.Assignment1.card.UnoCard.Type;

/**
* The following class is used for verifying if the card that is dropped by a player is valid or not,
* by checking it against the topmost card on the pile.
*/

public class Rules {

    /**
    * Function checks if the card is that the player is going to drop is valid or by checking it against the
    * top most card. In the case that the card to be dropped is not at
    */
    public static boolean isValidCard(UnoCard topPileCard, UnoCard playerCard, Color wildColorChoice,
                                      Number wildNumberChoice) {

        return (isWild(topPileCard, playerCard) || hasCommonColor(topPileCard, playerCard, wildColorChoice)
                || hasCommonType(topPileCard, playerCard) || hasCommonNumber(topPileCard, playerCard, wildNumberChoice));
    }

    /**
     * Checks if the top card is a wild card or not
     * @param topPileCard is the top most card in the discard pile
     * @param playerCard is the card that will dropped by the player
     */
    private static boolean isWild(UnoCard topPileCard, UnoCard playerCard) {

        return (playerCard.getType() == Type.Wild || playerCard.getType() == Type.WildDrawFour);
    }

    /**
     * checks if player's card matches with the top pile's card or if the card color matches the wild card's color
     * @param topPileCard is the top card in the pile
     * @param playerCard is the player's card that is to be dropped
     * @param wildColorChoice is the color choice that is specified if the top pile card is a wild card
     */
   private static boolean hasCommonColor (UnoCard topPileCard, UnoCard playerCard, Color wildColorChoice){

        Color playerCardColor = ((ColoredUnoCard) playerCard).getColor();

        if (!(topPileCard instanceof ColoredUnoCard) && wildColorChoice != null) {
            return (playerCardColor == wildColorChoice);
        }

        if ((topPileCard instanceof ColoredUnoCard)){
            Color topPileCardColor = ((ColoredUnoCard) topPileCard).getColor();
            return ((topPileCardColor.equals(playerCardColor)));
       }

       return false;
   }

   /**
    * checks if player's card is of the same type as the user's card
    * @param topPileCard is the top pile card in the discard pile
    * @param playerCard is the card that belongs to the player
    */
   private static boolean hasCommonType (UnoCard topPileCard, UnoCard playerCard){

       return (playerCard.getType() != Type.Number && topPileCard.getType().equals(playerCard.getType()));
   }

   /**
    * checks if the player's number on the card matches the user's number on the card
    * @param topPileCard is the top pile card
    * @param playerCard is the player's card
    * @param numberChoice is the number choice specified by the player
    */
   private static boolean hasCommonNumber (UnoCard topPileCard, UnoCard playerCard, Number numberChoice) {

       if (!(playerCard instanceof NumberUnoCard)) {
           return false;
       }

       Number playerValue = ((NumberUnoCard) playerCard).getValue();

       if (!(topPileCard instanceof NumberUnoCard)) {
           return (numberChoice == playerValue);
       }

       Number topPileCardValue = ((NumberUnoCard) topPileCard).getValue();
       return ((numberChoice == playerValue) || (topPileCardValue == ((NumberUnoCard) playerCard).getValue()));
   }

}
