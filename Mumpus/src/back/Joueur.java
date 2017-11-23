package back;

public class Joueur {
	int posX;
	int posY;
	
	public Joueur(int posX, int posY) {
		super();
		this.posX = posX;
		this.posY = posY;
	}
	public boolean capteurMort(){
		return true;
	}
	public boolean capteurPuit(){
		return true;
	}
	
	public boolean capteurWumpus(){
		return true;
	}
	
	public boolean capteurMur(){
		return true;
	}
	public boolean capteurOr(){
		return true;
	}
	public boolean capteurCriWumpus(){
		return true;
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
}
