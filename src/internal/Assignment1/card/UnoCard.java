package Assignment1.card;

/**
* The purpose of this class is to extend an abstract card and add on features that would be used for
* building an UnoCard. Hence, this class serves to both create an UnoCard through initializing its
* value, color, and type.
*/

public class UnoCard {

    // Provides all possible types of cards that the player can take on
   public enum Type {
       Wild,
       WildDrawFour,
       DrawTwo,
       Reverse,
       Number,
       Skip
   }

    private Type type_; // specifies the Type for each card

    /**
     * Creates an UnoCard
     * @param type is the Type that the card will be
     */
    public UnoCard(Type type) {
        type_ = type;
    }

    /**
     * Returns the type of the card
     */
    public Type getType() {
        return type_;
    }


}
