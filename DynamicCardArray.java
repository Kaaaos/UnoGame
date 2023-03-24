import java.util.Random;
public class DynamicCardArray{
	private Card[] card;
	private int next;

	public DynamicCardArray(int numberOfCards){
		this.card = new Card[numberOfCards];
	}
	
	public Card getCard(int i){
		if(i >= this.next || i < 0){
			throw new IllegalArgumentException("Must choose a valid card");
		}
		
		return this.card[i];
	}
	
	//This method will add the card into the deck.
	public void add(Card c){
	if(this.next >= this.card.length){
		   doubleSize();
	}
    this.card[this.next] = c;
	this.next++;
	}
	
	//This is a method to remove a specific card at a specific position
	public void removeIndex(int i){
		if(i >= this.next || i < 0){
			throw new IllegalArgumentException("Must choose a card that exists");
		} 
		
		for(int j = i; j < this.next-1; j++) {
			this.card[j] = this.card[j + 1];	
		}
		this.card[this.next-1] = null;
		this.next--;
	}
	
	//Using the removeIndex method, this method will search through the deck and check for the index of the card
	//If we find it, we call removeIndex and remove the card at the index found.
	public void removeCard(Card c){
		int index = -1;
		for(int i = 0 ; i < this.card.length; i++){
			if(this.card[i] == c){
				index = i;
			}
		}

		if(index >= 0){
			this.removeIndex(index);
		}
	}
	
	//This method will remove the last card of a pile.
	public void removeCardFromTop(){
		this.card[this.next-1] = null;
		this.next--;
	}
	
	//Double the size of the deck if not enough space.
	public void doubleSize(){
	  Card[] bigger = new Card[this.card.length*2];
	  for(int i = 0; i < this.card.length; i++){
		  bigger[i]= this.card[i];
	  }
	  this.card = bigger;
	}
	  
	
	public String toString(){
    String builder = "";
    for(int i = 0; i < this.card.length ; i++){
      builder+= " " + this.card[i];
    }
    return builder;
	}
  
	//Return the length of a pile of cards.
	public int length(){
	  return this.next;
	}
	
	
	//This method store the enums of the Color into an array. We use the random class to generate the index of the array that represent the specific enum.
	public Color getRandomColor(){
		Color[] values = Color.values();
		int length = values.length;
		Random random = new Random();
		int randomIndex = random.nextInt(length);
		
		return values[randomIndex];
	}

	//This method basically create the main deck for the game. The method uses nested for each loop to iterate through all the possibles enums for the values and colors. 
	//I am using indexes so my loop can stop at certain enums. This allows me to have a set number of cards and so I can limitate my number of wild cards so they won't appear as often.
	public void createMainDeck(){
			Value wildFour_Index = Value.Wild_Four;
			for(Color c : Color.values()){
				for(Value v : Value.values()){
					if(v == wildFour_Index){
						add(new Card(null,v));
						break;
					}
					else
					add(new Card(c,v));
					add(new Card(c,v));
				}
			}
			Value wild_Index = Value.Wild;
			for(Value v : Value.values()){
				if(v == wild_Index){
					add(new Card(null,v));
					add(new Card(null,v));
				}
			}
			Value value_Index  = Value.Two;	
			for(Color c : Color.values()){
				for(Value v : Value.values()){
					if(v == value_Index){
						break;
					}
					add(new Card(c,v));
				}
			}
	}
	
	//This method swaps two random cards.
	public void	swap(){
		Random random = new Random();
		int index1 = random.nextInt(this.card.length);
		int index2 = random.nextInt(this.card.length);
		
		Card temp = this.card[index1];
		this.card[index1] = this.card[index2];
		this.card[index2] = temp;
	}
	
	//Using the swap method, this method will simulate shuffling for our deck using a loop that will call the swap method a certain amount of time.
	public void shuffle(){
		for(int i = 0 ; i < this.card.length ; i++){
			swap();
		}
	}
}




						