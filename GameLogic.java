import java.util.Scanner;
public class GameLogic{ 
	private final int NUMBER_PLAYER_CARDS= 7;
	private final int NUMBER_DECK_CARDS = 102;
	private final int DRAW_FOUR = 4;
	private DynamicCardArray player1;
	private DynamicCardArray player2;
	private DynamicCardArray mainDeck;
	private DynamicCardArray discardDeck;
	private Scanner reader = new Scanner(System.in);

	public GameLogic(){
		this.player1 = new DynamicCardArray(NUMBER_PLAYER_CARDS);
		this.player2 = new DynamicCardArray(NUMBER_PLAYER_CARDS);
		this.mainDeck = new DynamicCardArray(NUMBER_DECK_CARDS);
		this.discardDeck = new DynamicCardArray(NUMBER_DECK_CARDS);
	}

	//This method will create a full game between two players.It will create the deck and shuffle it using methods from the DynamicCardArray. It will also create two player decks and the discard deck.
	//The winningGame variable starts at false in the beginning. The method will loop over playerTurn with the two players and check at the end of each player's turn the winning condition.
	//If the winningGame is set to true, the player will win and this will end the game.
	public void gameTurn(){
		System.out.println("The game is starting : Shuffling the cards and dealing them to each players");
		this.mainDeck.createMainDeck();
		this.mainDeck.shuffle();
		createPlayersDeck();
		createDiscardDeck();
		
		boolean winningGame = false;
		System.out.println("Beginning the game");
		System.out.println("****************");
		
		while(winningGame == false){
			
		System.out.println("Player 1 turn");
		System.out.println("Your hand :" + this.player1);
		playerTurn(this.player1);
		System.out.println("****************");
		
		if(winningHand(this.player1) == true){
			System.out.println("Player 1 won the game!");
			break;
		}
		if(checkIfMainDeckEmpty() == true){
			System.out.println("The main deck is empty : The game is a tie");
			break;
		}
		
		System.out.println("Player 2 turn");
		System.out.println("Your hand :" + this.player2);
		playerTurn(this.player2);
		System.out.println("****************");
		
		if(winningHand(this.player2) == true){
			System.out.println("Player 2 won the game!");
			break;
		}
		
		if(checkIfMainDeckEmpty() == true){
			System.out.println("The main deck is empty : The game is a tie");
			break;
		}
		}		
	}
	
	//This method is a single turn for a player. It will check if the card played match the card of the discard deck and play the card depending on the type of card played(e.g draw two) and remove it from the hand.
	public void playerTurn(DynamicCardArray player){
		Card lastCardFromDiscard = getLastCardFromDiscard();
		System.out.println("Discard pile : " + lastCardFromDiscard);
		System.out.println("Choose the card that you wish to play(the position) : ");
		boolean validCard = true;
		
		while(validCard){
			
		int cardToPlay = reader.nextInt();
		if(cardToPlay == -1){ //If a user type -1, he will draw a card from the main deck. It will then end his turn.
		System.out.println("Drawing a card");
			drawCard(player);
			return;
		}
		Card cardPlayed = player.getCard(cardToPlay);
		Color discardCardColor = lastCardFromDiscard.getColor();
		Value discardCardValue = lastCardFromDiscard.getValue();
		Value cardPlayedValue = cardPlayed.getValue();
		Color cardPlayedColor = cardPlayed.getColor();
	
		if(cardPlayedValue == Value.DrawTwo){
			drawTwo(player);
			removeAddHandDiscard(player,cardPlayed);
			break;
		}
		else if(cardPlayedValue== Value.Wild){
			wildCardEffect(cardPlayed);
			removeAddHandDiscard(player,cardPlayed);
			break;
		}
		else if(cardPlayedValue == Value.Wild_Four){
			wildCardEffect(cardPlayed);
			drawFour(player);
			removeAddHandDiscard(player,cardPlayed);
			break;
		}
		else if(cardPlayedValue == discardCardValue || cardPlayedColor == discardCardColor){
			removeAddHandDiscard(player,cardPlayed);
			break;
		}
		else 
			System.out.println("Choose a valid card please");
			continue;
		}
	}
	
	//This method adds the card played into the discard pile and removes it from the player hand.
	public void removeAddHandDiscard(DynamicCardArray player, Card cardPlayed){
		this.discardDeck.add(cardPlayed);
		player.removeCard(cardPlayed);
	}
	//This method will create the deck for the two players.
	public void createPlayersDeck(){	
		while(this.player1.length() < NUMBER_PLAYER_CARDS){
			drawCard(this.player1);
		}
		
		while(this.player2.length() < NUMBER_PLAYER_CARDS){
			drawCard(this.player2);
		}
	}

	//This method will create the discard pile deck.
	public void createDiscardDeck(){
		int lastCardMainDeck = this.mainDeck.length()-1;
		Card cardToAdd = this.mainDeck.getCard(lastCardMainDeck);
		
		//If the card is a Wild card, it will pick a random color so that the game can still proceed.
		if(cardToAdd.getValue() == Value.Wild || cardToAdd.getValue() == Value.Wild_Four){
			Color randomColor = cardToAdd.getRandomColor();
			cardToAdd.setNewColor(randomColor);
		}
		this.discardDeck.add(cardToAdd);
		this.mainDeck.removeCardFromTop();
	}
	
	//Get the card at the last position of the discard deck.
	public Card getLastCardFromDiscard(){
		int discardDeckLength = this.discardDeck.length()-1; 
		Card lastCardFromDiscard = this.discardDeck.getCard(discardDeckLength);
		return lastCardFromDiscard;
	}
	
	//This method will take as input a player. This will add a card to that player.
	public void drawCard(DynamicCardArray player){
		int lastCardDeck = this.mainDeck.length()-1;
		Card cardToAdd = this.mainDeck.getCard(lastCardDeck);
		player.add(cardToAdd);
		this.mainDeck.removeCardFromTop();
	}
	
	//This method will take as input a player. It will basically apply the effect of a draw two card. It will identity which player played this card and add two cards to the other player.
	public void drawTwo(DynamicCardArray player){
		if(player.equals(this.player1)){
			drawCard(this.player2);
			drawCard(this.player2);	
		}
		if(player.equals(this.player2)){
			drawCard(this.player1);
			drawCard(this.player1);
		}
	}
	
	//This method will do like the drawTwo method but will add four cards instead.
	public void drawFour(DynamicCardArray player){
		if(player.equals(this.player1)){
			int counter = 0;
			while(counter < DRAW_FOUR){
				drawCard(this.player2);
				counter++;
			}
		}
		if(player.equals(this.player2)){
			int counter = 0;
			while(counter < DRAW_FOUR){
				drawCard(this.player1);
				counter++;
			}
		}
	}
	
	//This method will take as input a card. This will basically apply the effect when a wild card is used. It will prompt the user to enter a color of their choice. 
	//If it's not a color that exist in the game, it will prompt the user to enter another value until it's a correct one.
	//To make the program case insensitive, we use the .equalsIgnoreCase method. This behave exactly like .equals but will ignore the case.
	public void wildCardEffect(Card cardPlayed){
	System.out.println("Please choose the new color that you want to apply on the discard pile");
	String newColorCard = reader.next();
	
	while( !newColorCard.equalsIgnoreCase("Red") && !newColorCard.equalsIgnoreCase("Blue") && !newColorCard.equalsIgnoreCase("Green") && !newColorCard.equalsIgnoreCase("Yellow")){
		System.out.println("Please enter a valid color that exist within the game");
		newColorCard = reader.next();
	}
		if(newColorCard.equalsIgnoreCase("Red")){
			cardPlayed.setNewColor(Color.Red);
		}
		if(newColorCard.equalsIgnoreCase("Blue")){
			cardPlayed.setNewColor(Color.Blue);
		}
		if(newColorCard.equalsIgnoreCase("Green")){
			cardPlayed.setNewColor(Color.Green);
		}
		if(newColorCard.equalsIgnoreCase("Yellow")){
			cardPlayed.setNewColor(Color.Yellow);
		}
	}

	//This method will simply check if the player meets the winning condition. If he has 0 cards in his hand, the method will return true, otherwise it'll return false.
	public boolean winningHand(DynamicCardArray player){
		if(player.length() == 0){
			return true;
		}
		else 
			return false;
	}
	
	//This method checks if the main deck is empty.
	public boolean checkIfMainDeckEmpty(){
		if(this.mainDeck.length() == 0){
			return true;
		}
		else
			return false;
	}
		
}
	
	
	
	
	
	
	

