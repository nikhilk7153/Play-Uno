package internal.Assignment1.card;

/**
 * ColoredUnoCard is used for creating all cards that have a color associated to them.
 */
public class ColoredUnoCard extends UnoCard {

    // gives the possible values that the card take on for color
    public enum Color {
        Yellow,
        Green,
        Blue,
        Red
    }

    // specifies the color of the card
    private Color color_;

    /**
    * Constructs an Uno Card given the type and color
    */
    public ColoredUnoCard(Type type, Color color) {

        super(type);
        color_ = color;

    }

     /**
     * Returns the color of the card
     */
    public Color getColor() {

        return color_;

    }

}
