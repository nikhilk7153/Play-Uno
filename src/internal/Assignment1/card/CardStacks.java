package internal.Assignment1.card;
import java.util.ArrayList;
import java.util.Collections;
import internal.Assignment1.card.UnoCard.Type;
import internal.Assignment1.card.ColoredUnoCard.Color;


/**
* CardStacks is used to maintain the Deck and Pile for a game of UNO. The card stack can be properly initialized
* with all 108 cards with the correct number of each type or the user can customize and create a deck and pile of
* their own choice. This class include removing a card from the deck along with adding cards to the pile and re-filling
* the deck from the pile to the deck, when necessary.
*/

public class CardStacks {

    private ArrayList<UnoCard> drawPile_; // this is the Deck that the player draw from
    private ArrayList<UnoCard> discardPile_; // this is the Pile that the players are dropping cards into

    /**
     * Initializes the Deck and Pile for the game with all 108 cards
     */
    public CardStacks() {

        discardPile_ = new ArrayList<UnoCard>();
        drawPile_ = new ArrayList<UnoCard>();
        initializeDeckAndPile();
    }

    /**
     * Used for creating a deck and pile of UnoCards that is customized by the user
     * @param deck will be the designated deck for the game
     * @param pile will be the designated pile for the game
     */
    public CardStacks(ArrayList<UnoCard> deck, ArrayList<UnoCard> pile) {

        drawPile_ = deck;
        discardPile_ = pile;
    }

    /**
    * Initializes the 108 UNO cards that are used to play UNO and shuffles the cards
    * before removing the top most card on the deck and placing it into pile
    */
    private void initializeDeckAndPile() {

       UnoCard wildCard = new UnoCard(Type.Wild);
       UnoCard wildDrawFour = new UnoCard(Type.WildDrawFour);

       createDuplicateCards(wildCard, 4);
       createDuplicateCards(wildDrawFour, 4);

        for (Color color : Color.values()) {
            initializeColoredCards(color);
        }

        shuffleDeck();
        deckToPile();
    }

    /**
     * Shuffles the deck
     */
    private void shuffleDeck() {
        Collections.shuffle(drawPile_);
    }

    /**
     * Removes the first card from the deck and places it into the pile and makes sure that the front card is not a
     * wild card.
    */
    private void deckToPile() {

        UnoCard FrontCard = drawPile_.get(0);

        while (FrontCard.getType() == Type.Wild || FrontCard.getType() == Type.WildDrawFour) {
            drawPile_.add(FrontCard);
            drawPile_.remove(0);
            FrontCard = drawPile_.get(0);
        }

        drawPile_.remove(0);
        addToPile(FrontCard);
    }

     /**
     * This functions helps with initializing the colored cards which are the cards with numbers 0-9 along with
     * the skip, reverse, and draw two cards.
     * @color is the color of the card
     */
    private void initializeColoredCards(Color color) {

        UnoCard zero_card = new NumberUnoCard(Type.Number, color, NumberUnoCard.Number.zero);
        drawPile_.add(zero_card);
        NumberUnoCard.Number[] numbers = new NumberUnoCard.Number[] {NumberUnoCard.Number.one, NumberUnoCard.Number.two, NumberUnoCard.Number.three, NumberUnoCard.Number.four, NumberUnoCard.Number.five, NumberUnoCard.Number.six,
                                         NumberUnoCard.Number.seven, NumberUnoCard.Number.eight, NumberUnoCard.Number.nine};

        for (int i = 0; i < numbers.length; i++) {
            createDuplicateCards(new NumberUnoCard(Type.Number, color, numbers[i]), 2);
        }

        createDuplicateCards(new ColoredUnoCard(Type.Reverse, color), 2);
        createDuplicateCards(new ColoredUnoCard(Type.DrawTwo, color),  2);
        createDuplicateCards(new ColoredUnoCard(Type.Skip, color), 2);
    }

     /**
     * Performs the action of creating multiple copies of a single card
     * @param cardToCopy specifies the card to be copied
     * @param num_duplicates specifies the number of duplicates to be created
     */
    private void createDuplicateCards(UnoCard cardToCopy, int num_duplicates) {

        for (int i = 0; i < num_duplicates; i++) {
            drawPile_.add(cardToCopy);
        }
    }

    /**
     * Removes card from the deck and places it into the pile
     */
    public UnoCard removeFromDeck() {

        if (drawPile_.isEmpty()) {
            refillDeck();
        }

        UnoCard front_card = drawPile_.get(0);
        drawPile_.remove(0);
        return front_card;
    }

    /**
     * Takes all the cards from the pile and then places them into the deck
     */
    private void refillDeck() {

        UnoCard topPileCard = getTopPileCard();
        discardPile_.remove(0);

       while (!discardPile_.isEmpty()) {
            UnoCard pile_card = discardPile_.get(0);
            discardPile_.remove(0);
            drawPile_.add(pile_card);
        }

       discardPile_.add(topPileCard);
       shuffleDeck();
    }

    /**
     * returns the top most card form the pile
     */
    public UnoCard getTopPileCard() {

        return discardPile_.get(0);
    }

    /**
     * Adds card to the front of the pile
     */
    public void addToPile(UnoCard card) {

        discardPile_.add(0, card);
    }

    /**
     * Returns the Deck of cards to draw from
     */
    public ArrayList<UnoCard> getDeck() {

        return drawPile_;
    }

    /**
     * Returns the deck of cards that are inside the pile
     */
    public ArrayList<UnoCard> getPile() {

        return discardPile_;
    }
    
}

