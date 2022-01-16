package internal.Assignment1.game;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import internal.Assignment1.card.CardStacks;
import internal.Assignment1.card.NumberUnoCard.Number;
import internal.Assignment1.card.UnoCard;
import internal.Assignment1.card.ColoredUnoCard.*;

/**
* This class accomplishes the following purposes: initialize a game, let a player drop a card, along with
* dropping cards into the pile and drawing cards from the deck. If a wild card is dropped a player can also indicate
* their preference .
*/

public class UnoGame {

    private Player[] players_; // gives order of the players that are playing
    private CardStacks pileAndDeck_; // maintains the order of the pile and deck for this class

    /**
     * @param player_names returns the names of the players and creates a Uno Game by giving
     * 7 cards to each player and creating the pile and deck.
     */
    public UnoGame(String[] player_names) {

        pileAndDeck_ = new CardStacks();
        players_ = new Player[player_names.length];
        initializePlayers(player_names, 0);
    }

    /**
     * @param player_names are the names of all of the players
     * @param aiPlayerCount are the total number of AI players
     */
    public UnoGame(String[] player_names, int aiPlayerCount) {

        pileAndDeck_ = new CardStacks();
        String[] allPlayerNames = new String[aiPlayerCount + player_names.length];
        int aiPlayerIdx = 1;

        for (int i = 0; i < allPlayerNames.length; i++) {

            if (i < aiPlayerCount) {
                String name = "AI Player " + aiPlayerIdx;
                allPlayerNames[i] = name;
                aiPlayerIdx++;
            } else {
                int index = i - aiPlayerCount;
                allPlayerNames[i] = player_names[index];
            }
        }
        players_ = new Player[allPlayerNames.length];
        initializePlayers(allPlayerNames, aiPlayerCount);
    }

    /**
     * Generates the player cards by giving 7 to each player
     * @param listOfPlayers is the players who are playing this game
     * @param pileAndDeck is the deck and pile being used for this game
     */
    public UnoGame(Player[] listOfPlayers, CardStacks pileAndDeck) {

        players_ = listOfPlayers;
        pileAndDeck_ = pileAndDeck;
    }

    /**
     * Generates the player cards by giving 7 to each player
     * @param player_names is the players who are playing this game
     * @param numAIPlayers the number of players who are playing
     */
    private void initializePlayers(String[] player_names, int numAIPlayers) {

        for (int i = 0; i < player_names.length; i++) {
            ArrayList<UnoCard> playerCards =  generatePlayerCards();
            if (i < numAIPlayers) {
                AIPlayer addedAIPlayer = new AIPlayer(player_names[i]);
                players_[i] = addedAIPlayer;
            } else {
                Player added_player = new Player(player_names[i], playerCards);
                players_[i] = added_player;
            }
        }
        shufflePlayerOrder();
    }

    /**
     * Generates the player cards by giving 7 to each
     */
    private ArrayList<UnoCard> generatePlayerCards() {

        ArrayList<UnoCard> playerCards = new ArrayList<UnoCard>();

        for (int j = 0; j < 7; j++) {
            UnoCard drawnCard = pileAndDeck_.removeFromDeck();
            playerCards.add(drawnCard);
        }
        return playerCards;
    }

    /**
     * Shuffles the player order
     */
    private void shufflePlayerOrder() {

        List<Player> playerList = Arrays.asList(players_);
        Collections.shuffle(playerList);
        playerList.toArray(players_);
    }


    /**
     * Picks up cards from the deck and add it to the user's card suite
     * @user is the current user
     * @param num_pickup is the number of cards to pick up
     * @param user the user that is playing
     */
    public void pickUpCards(int num_pickup, Player user) {

        for (int i = 0; i < num_pickup; i++) {
            UnoCard topDeckCard = pileAndDeck_.removeFromDeck();
            user.getPlayerCards().add(topDeckCard);
        }
    }

    /**
     * Helper function for dropping a card based on the index of the card in the list of cards
     * removes the card from the player and adds to pile if the dropped card is valid
     * @user is the current user
     * @param cardIdx is the index of the card
     * @param colorFromWild is the color that the user chooses on
     * @param numFromWild is the number that the user chooses  on
     */
    public void dropCard(Player user, int cardIdx, Color colorFromWild, Number numFromWild) {

        UnoCard players_choice = user.getPlayerCards().get(cardIdx);
        UnoCard topPileCard = pileAndDeck_.getTopPileCard();

        if (Rules.isValidCard(topPileCard, players_choice, colorFromWild, numFromWild)) {
            user.removeCardIdx(cardIdx);
            pileAndDeck_.addToPile(players_choice);
        }
    }

    /**
     * Returns the player order
     */
    public Player[] getPlayerOrder() {

        return players_;
    }

    /**
     * Returns the deck and pile of the game
     */
    public CardStacks getPileAndDeck() {

        return pileAndDeck_;
    }

    /**
     * Checks whether a player has won a game or not
     * @param playerIdx returns the index of the player
     */
    public boolean hasPlayerWon(int playerIdx) {

        return players_[playerIdx].getPlayerCards().isEmpty();
    }

}
