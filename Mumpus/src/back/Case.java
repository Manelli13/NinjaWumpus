package back;

public class Case {
	private int posX;
	private int posY;
	private boolean murDroit;
	private boolean murGauche;
	private boolean murHaut;
	private boolean murBas;
	private boolean puit;
	private boolean tresor;
	
	
	public Case(int posX, int posY, boolean murDroit, boolean murGauche, boolean murHaut, boolean murBas, boolean puit, boolean tresor) {
		this.posX = posX;
		this.posY = posY;
		this.murDroit = murDroit;
		this.murGauche = murGauche;
		this.murHaut = murHaut;
		this.murBas = murBas;
		this.puit = puit;
		this.tresor = tresor;
	}

	public boolean diffuserBrise(){
		return puit;
	}
	
	public boolean diffuserLueur(){
		return tresor;
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

	public boolean isMurDroit() {
		return murDroit;
	}

	public void setMurDroit(boolean murDroit) {
		this.murDroit = murDroit;
	}

	public boolean isMurGauche() {
		return murGauche;
	}

	public void setMurGauche(boolean murGauche) {
		this.murGauche = murGauche;
	}

	public boolean isMurHaut() {
		return murHaut;
	}

	public void setMurHaut(boolean murHaut) {
		this.murHaut = murHaut;
	}

	public boolean isMurBas() {
		return murBas;
	}

	public void setMurBas(boolean murBas) {
		this.murBas = murBas;
	}

	public boolean isPuit() {
		return puit;
	}

	public void setPuit(boolean puit) {
		this.puit = puit;
	}

	public boolean isTresor() {
		return tresor;
	}

	public void setTresor(boolean tresor) {
		this.tresor = tresor;
	}
	
}
