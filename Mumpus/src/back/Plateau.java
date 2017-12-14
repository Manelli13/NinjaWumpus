package back;
import java.util.ArrayList;

public class Plateau {
	private ArrayList<Case> cases;
	private int size;
	private Joueur agent;
	private ArrayList<Case> cheminDijkstra;
	public Plateau (int size){
		this.size=size;
		this.cases=new ArrayList();
		this.cheminDijkstra=new ArrayList<Case>();
		this.generateCase();
		this.placerMur();
		this.agent= new Joueur(size-1, 0);
		try {
			ArrayList<Case> soluce = this.resolveMumpus(findFirstCase());
		} catch (Exception e){
			
		}
		
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
		int wPosX = findWumpus().getPosX();
		int wPosY = findWumpus().getPosY();
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
		double generateWumpus = 0;
		int coefPuit=0;
		int coefWumpus=0;
		int coefTresor=0;
		
		int nbTresor=1;
		int nbWumpus=1;
		for(int i=0; i<size; i++){
			for(int j=0; j<size; j++){
				
				generatePuit=Math.random()*(1-0)+coefPuit;
				generateTresor=Math.random()*(1-0)+coefTresor;
				generateWumpus=Math.random()*(1-0)+coefWumpus;
					
				if(nbPuit>0 &&generatePuit<0.5){
					cases.add(new Case( i,j,false,false,false,false, true/*puit*/,false/*tresor*/,false/*wumpus*/));
					nbPuit--;
					generatePuit=0;
					coefPuit=0;
				}
				else if(nbTresor>0&&generateTresor<0.30){
					cases.add(new Case( i,j,false,false,false,false, false/*puit*/,true/*tresor*/,false/*wumpus*/));
					generateTresor=0;
					nbTresor--;
					coefTresor=0;
				}
				else if(nbWumpus > 0 && generateWumpus < 0.30){
					cases.add(new Case( i,j,false,false,false,false, false/*puit*/,false/*tresor*/,true/*wumpus*/));
					generateWumpus=0;
					nbWumpus--;
					coefWumpus=0;
				}
				else{
					
					coefPuit-=0.10;
					coefWumpus-=0.10;
					coefTresor-=0.10;
					cases.add(new Case( i,j,false,false,false,false, false/*puit*/,false/*tresor*/,false/*wumpus*/));
				}
				
			}
		}
	}
	
	
	public Case findWumpus(){
		
		Case cwumpus = new Case();
		for(Case c : cases){
			if(c.isWumpus() == true)
				cwumpus = c;
		}

		return cwumpus;
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
	
	
	public ArrayList<Case> resolveMumpus(Case c) {
		System.out.print("("+c.getPosX()+";"+c.getPosY()+"); ");
		
		if(c.isTresor()) {
			return this.cheminDijkstra;
		}else{
		
		Case[] voisin = generateVoisin(c);
		
			if (voisin[0] != null && !voisin[0].isPuit() && !voisin[0].isWumpus()) {
				this.cheminDijkstra.add(c);
				return resolveMumpus(voisin[0]);
			}else if (voisin[1] != null &&  !voisin[1].isPuit()&& !voisin[1].isWumpus()){
				this.cheminDijkstra.add(c);
				return resolveMumpus(voisin[1]);
			}else if (voisin[2] != null && !voisin[2].isPuit()&& !voisin[2].isWumpus()){
				this.cheminDijkstra.add(c);
				return resolveMumpus(voisin[2]);
			}else{
				this.cheminDijkstra.add(c);
				return resolveMumpus(voisin[3]);
			}
		}
	}
	
	public Case findFirstCase() {
		Case firstCase = new Case();
		for(Case c : cases) {
			if(c.getPosX() == this.size-1 && c.getPosY()==0) {
				firstCase = c;
			}
			
		}
		return firstCase;
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

}