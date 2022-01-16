package internal.Assignment1.UserInterface;

import internal.Assignment1.card.CardStacks;
import internal.Assignment1.card.ColoredUnoCard;
import internal.Assignment1.card.NumberUnoCard;
import internal.Assignment1.game.UnoGame;
import internal.Assignment1.game.Player;
import internal.Assignment1.card.UnoCard;
import internal.Assignment1.card.NumberUnoCard.Number;
import internal.Assignment1.card.ColoredUnoCard.Color;
import internal.Assignment1.card.UnoCard.Type;

import java.util.ArrayList;
import java.util.Scanner;


public class TerminalUI {

    /**
     * Helper function that other functions can use for asking for intput
     */
    public static void askUser(String message, String[] options) {

        while (true) {

            Scanner reader = new Scanner(System.in);
            System.out.println(message);
            String input = reader.nextLine();

            for (int i = 0; i < options.length; i++) {

                if (options[i] == input) {

                    break;

                }
            }
        }
    }

    /**
     * Used for starting the game and asking for the number of players
     */

    public static void startGameWithPlayers() {

        String output = "Hello, welcome to Nikhil's Uno Game. Please enter S to Start the game: ";
        String[] possibleInput = {"S"};
        askUser(output, possibleInput);

        while (true) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Please enter the number of players that will play this game which is between" +
                    " 2 to 9:  ");
            int numPlayers = reader.nextInt();

            if (numPlayers >= 2 && numPlayers <= 9) {
                break;
            }
        }
    }

    /**
     * Used to tell if a player wants to Pick or Draw a card
     */

    public static void pickorDraw() {

        String output = "Please type in P if you want to Pick a card and have your turned skipped. Please enter D if you want to remove" +
                "a card: ";
        String[] possibleInput = {"S", "D"};
        askUser(output, possibleInput);
    }

    /**
     * Used to tell if player wants to show or hide their cards
     */

    public static void showOrHide() {

        String toOutput  = "Please enter whether you want to show or hide your cards. To show cards, type S," +
                "to hide cards, type H: ";
        String[] possibleInputs = {"S", "H"};
        askUser(toOutput, possibleInputs);

    }

    /**
     * Asks for a player to give their color choice for a card
     */

    public static void choseColor() {

        String toOutput = "You have dropped a wild card. Please decide which color you would like. Type " +
                "B for Blue, Y for Yellow, G for Green, and R for Red: ";
        String [] possibleInputs = {"R", "B", "Y", "G"};
        askUser(toOutput, possibleInputs);

    }

    /**
    * Shows the game state by showing the player's cards, the top most pile card, the number of cards stack,
    * the deck, and the pile cards
    */
    public static void showGameState() {

        ArrayList<UnoCard> p1_cards = new ArrayList<UnoCard>();
        ArrayList<UnoCard> p2_cards = new ArrayList<UnoCard>();

        p1_cards.add(new NumberUnoCard(Type.Number, Color.Red, Number.seven));
        p1_cards.add(new ColoredUnoCard(Type.Skip, Color.Green));
        p2_cards.add(new ColoredUnoCard(Type.DrawTwo, Color.Blue));
        
        Player drawer = new Player("John", p1_cards);
        Player player2 = new Player("Andy", p2_cards);

        Player[] players = {drawer, player2};

        ArrayList<UnoCard> deck = new ArrayList<UnoCard>();
        ArrayList<UnoCard> pile = new ArrayList<UnoCard>();

        pile.add(new NumberUnoCard(Type.Number, Color.Blue, Number.nine));
        pile.add(new NumberUnoCard(Type.Number, Color.Yellow, Number.eight));
        pile.add(new NumberUnoCard(Type.Number, Color.Red, Number.six));

        deck.add(new NumberUnoCard(Type.Number, Color.Green, Number.two));

        CardStacks deckAndPile = new CardStacks(deck, pile);
        
        UnoGame g = new UnoGame(players, deckAndPile);

        System.out.println("The top most pile card is: ");
        printCard(g.getPileAndDeck().getTopPileCard());

        System.out.println("The player " + players[0].getName() + " has the following cards: " );
        for (int i = 0; i < p1_cards.size(); i++) {

            printCard(p1_cards.get(i));
        }

        System.out.println("The deck cards are: ");
        for (int i = 0; i < deck.size(); i++) {
            printCard(deck.get(i));
        }

        System.out.println("There are currently zero cards being stacked");
    }

    /**
     * Formats the string for the cards
     */
    public static void printCard(UnoCard card) {

        if (card instanceof NumberUnoCard) {
            System.out.println("UnoCard of type " + Type.Number + " with color " + ((NumberUnoCard) card).getColor() +
                    " and value " + ((NumberUnoCard) card).getValue());
        } else if (card instanceof ColoredUnoCard) {
            System.out.println("UnoCard of type " + card.getType() + " with color " + ((ColoredUnoCard) card).getColor());
        } else {
            System.out.println("UnoCard of type " + Type.Number + " with type " + card.getType());
        }
        
    }

    /**
    * Prompts to tell that a player has won the game
    */
    public static void finishedGame() {
        System.out.println("A player has lost all their card. Hence, the game is over!");
    }


    public static void main(String[] args) {
        startGameWithPlayers();
        pickorDraw();
        showOrHide();
        showGameState();
        choseColor();
        finishedGame();
    }
    
}

