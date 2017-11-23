package back;

public class Wumpus {
	private int posX;
	private int posY;
	
	public Wumpus(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	
	
	public boolean diffuserOdeur(int posX, int posY){
		return this.posX==posX && this.posY==posY;
	}
}
