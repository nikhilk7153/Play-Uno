                                             # fa21-cs242-assignment-1.1 

Upon building from last week, this week involved building the view and implementing to extra variations to the game of UNO. 
Essentially, the view for this game involved creating the user interface where the user can see their cards, can enter which 
card they want to select or drop, and also see the top most card. There is also an option for the player to enter in 
information about the round. From the backend point of view, some major changes that occurred are that the process of dropping a card is
no longer automated - the user will be able to do this himself upon verifying that it is a valid move. This also means
that when a wild card is set, a player now has the option to choose the card themselves as opposed to having the most
common color be chosen for them. The two special variations that were added to this game are that if an individual drops
a wild card, the individual will have the option to choose a color instead of having to choose a color. Additionally, 
if a user drops a wild card, they will also have the option reverse the turn of play if they choose to do so. 
As of now, the only way to see how the game works is through seeing the test cases which prove that the back logic works.
Additionally, one can also try out each of the user interface applications inside the Interfaces.java file, although 
this will not trigger a game and allow one to play it. From here, further implementations will be added to include a
proper graphical user interface that allows the user to see their cards on a screen as opposed to a terminal. 

