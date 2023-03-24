import java.util.Scanner;
public class UnoApplication{
	
	public static void main(String[] args){
		GameLogic unoGame  = new GameLogic();
		Scanner reader = new Scanner(System.in);
		
		boolean startGame = true;
		
		System.out.println("Welcome to Uno!");
		System.out.println("Before you begin to play, here are the rules of the game : ");
		System.out.println("*********************************************************");
		System.out.println("1. To play a card, you need to type the position of the card. The first card in your hand starts at position 0");
		System.out.println(" ");
		System.out.println("2. If you cannot play a card, you will need to draw a card from the main deck. To do that, you simply need to type -1 when you are prompted to play a card.");
		System.out.println(" ");
		System.out.println("*********************************************************");
		
		
		while(startGame) {
		unoGame.gameTurn();
		System.out.println("Do you want to play again? (type 'y' for yes or 'n' for no)");
		String restartGame = reader.next();
		if(restartGame.equals("n")){
			System.out.println("Thank you for playing");
			break;
		}
		
		else
			continue;
		}
	}
	

}
