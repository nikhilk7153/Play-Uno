package internal.Assignment1.card;

/**
 * NumberUnoCard extends a ColoredUnoCard in providing a number for each of the colored cards
 * that usually have a digit associated with them.
 */

public class NumberUnoCard extends ColoredUnoCard {

    // enumerates all the possible card values that a card can take on
    public enum Number {
        zero,
        one,
        two,
        three,
        four,
        five,
        six,
        seven,
        eight,
        nine
    }

    private Number value_;// return the value of the card

    /**
     * Creates a Uno Card from the information below
     * @param type is the type of the card
     * @param color is the color of the card
     * @param value is the value of the card
     */
    public NumberUnoCard(Type type, Color color, Number value) {

        super(type, color);
        value_ = value;
    }

    /**
     * Returns the value of card
     */
    public Number getValue() {

        return value_;
    }

}
