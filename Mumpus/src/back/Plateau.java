package back;
import java.util.ArrayList;

public class Plateau {
	private ArrayList<Case> cases;
	private int size;
	private Wumpus wumpus;
	private Joueur agent;
	public Plateau (int size){
		this.size=size;
		this.cases=new ArrayList();
		this.generateCase();
		this.placerMur();
		this.agent= new Joueur(size-1, 0);
		this.wumpus= new Wumpus((int)Math.random()*(size-0),(int)Math.random()*(size-0));
	}
	
	public void placerMur(){
		
		for(Case c : cases){
			if (c.getPosX()==0)
				c.setMurHaut(true);
			if(c.getPosX()==size-1)
				c.setMurBas(true);
			if(c.getPosY()==0)
				c.setMurGauche(true);
			if(c.getPosY()==size-1)
				c.setMurDroit(true);
		}
	}
	public void deplacerAgent(int newPosX, int newPosY){
		int wPosX = this.wumpus.getPosX();
		int wPosY = this.wumpus.getPosY();
		Case c = this.getCase(newPosX, newPosY);
		if(wPosX==newPosX&&wPosY==newPosY)
			this.agent.capteurMort();
		if((wPosX==(newPosX+1)||wPosX==(newPosX-1)) &&(wPosY==(newPosY+1)||wPosY==(newPosY-1)))
			this.agent.capteurWumpus();
		if(c.isPuit())
			this.agent.capteurMort();
		if((c.getPosX()==(newPosX+1)||c.getPosX()==(newPosX-1)) &&(c.getPosY()==(newPosY+1)||c.getPosY()==(newPosY-1)))
			this.agent.capteurPuit();
		if(c.isMurBas())
			this.agent.capteurMur();
		if(c.isMurDroit())
			this.agent.capteurMur();
		if(c.isMurGauche())
			this.agent.capteurMur();
		if(c.isMurHaut())
			this.agent.capteurMur();
		this.agent.setPosX(newPosX);
		this.agent.setPosY(newPosY);
	}
	
	
	public void generateCase(){
		int nbPuit=this.size-1;
		double generatePuit=0;
		double generateTresor = 0;
		int nbTresor=1;
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				generatePuit=generatePuit+Math.random()*(1-0);
				if(nbPuit>0&&generatePuit<0.5){
					cases.add(new Case( i,j,false,false,false,false, true/*puit*/,false/*tresor*/));
					nbPuit--;
					generatePuit=0;
				}
				else if(nbTresor>0&&generateTresor<0.30){
					cases.add(new Case( i,j,false,false,false,false, false/*puit*/,true/*tresor*/));
					generateTresor=0;
					nbTresor--;
				}
				else{
					generatePuit-=0.10;
					generateTresor-=0.10;
					cases.add(new Case( i,j,false,false,false,false, false/*puit*/,false/*tresor*/));
				}
					
				
			}
		}
	}
	
	
	
	
	
	public Case[] generateVoisin(Case c) {
		Case [] tabVoisin = new Case[4];
		
		for(Case ca : cases) {
			if(ca.getPosX() == c.getPosX()-1 && ca.getPosY() == c.getPosY()){
				tabVoisin[0]=ca;
			}
			if(ca.getPosX() == c.getPosX() && ca.getPosY() == c.getPosY()+1){
				tabVoisin[1]=ca;	
			}
			if(ca.getPosX() == c.getPosX()+1 && ca.getPosY() == c.getPosY()){
				tabVoisin[2]=ca;
			}
			if(ca.getPosX() == c.getPosX() && ca.getPosY() == c.getPosY()-1){
				tabVoisin[3]=ca;
			}

		}
		
		return tabVoisin;
	}
	
	//_________________________________________GETSET
	public ArrayList<Case> getCases() {
		return cases;
	}
	public Case getCase(int x, int y){
		for(Case c : this.cases){
			if(c.getPosX()==x&&c.getPosY()==y)
				return c;
		}
		return this.cases.get(0);
	}
	public void setCases(ArrayList<Case> cases) {
		this.cases = cases;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public Joueur getAgent(){
		return this.agent;
	}
	public Wumpus getWumpus() {
		return wumpus;
	}
	public void setWumpus(Wumpus wumpus) {
		this.wumpus = wumpus;
	}
}