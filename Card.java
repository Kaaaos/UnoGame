import java.util.Random;
public class Card{
	private Color color;
	private Value value;
	
	
	public Card(Color color, Value value){
		this.color = color;
		this.value = value;
	}
	
	public String toString(){
		return this.color + "-" + this.value;
		
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public Value getValue(){
		return this.value;
	}
	
	public void setNewColor(Color newColor){
		this.color = newColor;
	}
	
	//This method store the enums of the Color into an array. We use the Random class to generate the index of the array that represent the specific enum.
	public Color getRandomColor(){
		Color[] values = Color.values();
		int length = values.length;
		Random random = new Random();
		int randomIndex = random.nextInt(length);
		return values[randomIndex];
	}
	
	
	
	
	
	
	
	
}