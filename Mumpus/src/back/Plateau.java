package back;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Plateau  {
	private ArrayList<Case> cases;
	private int size;
	private Joueur agent;
	private CaseDijkstra caseDijkstra;
	int nbTresor=0, nbPuits =0;
	private ArrayList<Case> cheminDijkstra;
	public Plateau (int size){
		this.size=size;
		this.cases=new ArrayList();
		this.cheminDijkstra=new ArrayList<Case>();
		this.agent= new Joueur(0, size-1);
		this.generateCase();
		this.placerMur();
		this.generateBriseAndOdeur();

		findPuit();
		System.out.println();
		System.out.println("______________________________________________________");
		findTresor();
		System.out.println();
		System.out.println("______________________________________________________");
		System.out.println("Wumpus : ("+findWumpus().getPosX()+";"+findWumpus().getPosY()+")");
		caseDijkstra=this.resolveMumpusIteratif(findFirstCase(),caseTresor());
	}

	public void placerMur(){

		for(Case c : cases){
			if (c.getPosY()==0)
				c.setMurHaut(true);
			if(c.getPosY()==size-1)
				c.setMurBas(true);
			if(c.getPosX()==0)
				c.setMurGauche(true);
			if(c.getPosX()==size-1)
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
		int nbPuit=this.size/2+1;
		int addedPuit=0;
		double generatePuit=0;
		double generateTresor = 0;
		double generateWumpus = 0;
		int coefPuit=0;
		int coefWumpus=0;
		int coefTresor=0;

		Boolean prevPuit = false;

		int nbTresor=0;
		int nbWumpus=0;
		for(int y=0; y<size; y++){
			for(int x=0; x<size; x++){

				generatePuit=Math.random()*(1-0)+coefPuit;
				generateTresor=Math.random()*(1-0)+coefTresor;
				generateWumpus=Math.random()*(1-0)+coefWumpus;

				if(nbPuit>0 &&generatePuit<0.10 && y != size-1 && x != 0 && prevPuit == false ){
					cases.add(new Case( x, y ,false,false,false,false, true/*puit*/,false/*tresor*/,false/*wumpus*/, false/*brise*/, false/*odeur*/));
					this.nbPuits++;
					nbPuit--;
					generatePuit=0;
					prevPuit = true;
					addedPuit++;
					//coefPuit=0;
				}
				else if(nbTresor == 0&&generateTresor<0.05 && y != size-1 && x != 0){
					cases.add(new Case( x, y ,false,false,false,false, false/*puit*/,true/*tresor*/,false/*wumpus*/,false/*brise*/, false/*odeur*/));
					generateTresor=0;
					nbTresor++;
					prevPuit = false;
					//coefTresor=0;
				}
				else if(nbWumpus == 0 && generateWumpus < 0.05 && y != size-1 && x != 0 ){
					cases.add(new Case( x, y ,false,false,false,false, false/*puit*/,false/*tresor*/,true/*wumpus*/,false/*brise*/, false/*odeur*/));
					generateWumpus=0;
					nbWumpus++;
					prevPuit = false;
					//coefWumpus=0;
				}
				else{

					coefPuit-= 1 / (size - y );
					coefWumpus-=1 /( size - y) ;
					coefTresor-=1 / (size - y);
					cases.add(new Case( x,y,false,false,false,false, false/*puit*/,false/*tresor*/,false/*wumpus*/,false/*brise*/, false/*odeur*/));
					prevPuit = false;
				}

			}


		}
		if (nbTresor==0) {
			boolean test=true;
			while(test) {
				double xtemp = Math.random() * (this.size-1);
				double ytemp = Math.random() * (this.size-1);
				int xTresor=(int)xtemp;
				int yTresor=(int)ytemp;
				if(nbWumpus==1) {
					if(xTresor!=this.agent.getPosX()&&yTresor!=this.agent.getPosY()&&xTresor!=findWumpus().getPosX()&&yTresor!=findWumpus().getPosY()&&!this.getCase(xTresor, yTresor).isPuit()) {
						test=false;
						
						this.getCase(xTresor, yTresor).setTresor(true);
					}					
				}
				else {
					if(xTresor!=this.agent.getPosX()&&yTresor!=this.agent.getPosY()&&!this.getCase(xTresor, yTresor).isPuit()) {
						test=false;
						this.getCase(xTresor, yTresor).setTresor(true);
					}		
				}
			}
		}
		if (nbWumpus==0) {
			boolean test=true;
			while(test) {
				double xtemp = Math.random() * (this.size-1);
				double ytemp = Math.random() * (this.size-1);
				int xWumpus=(int)xtemp;
				int yWumpus=(int)ytemp;
				if(xWumpus!=this.agent.getPosX()&&yWumpus!=this.agent.getPosY()&&xWumpus!=caseTresor().getPosX()&&yWumpus!=caseTresor().getPosY()&&!this.getCase(xWumpus, yWumpus).isPuit()) {
					test=false;
					
					this.getCase(xWumpus, yWumpus).setWumpus(true);
				}
			}
		}
		while(nbPuit!=0) {
			double xtemp = Math.random() * (this.size-1);
			double ytemp = Math.random() * (this.size-1);
			int xPuit=(int)xtemp;
			int yPuit=(int)ytemp;
			if(xPuit!=this.agent.getPosX()&&yPuit!=this.agent.getPosY()&&xPuit!=caseTresor().getPosX()&&yPuit!=caseTresor().getPosY()&&xPuit!=findWumpus().getPosX()&&yPuit!=findWumpus().getPosY()) {
				nbPuit--;
				this.getCase(xPuit, yPuit).setPuit(true);
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

	public ArrayList<CaseDijkstra> generateVoisin(CaseDijkstra c) {
		ArrayList<CaseDijkstra> tabVoisin = new ArrayList<CaseDijkstra>();

		for(Case ca : cases) {
			if(ca.getPosX() == c.getPosX()-1 && ca.getPosY() == c.getPosY()){
				tabVoisin.add(new CaseDijkstra(c, ca));
			}
			if(ca.getPosX() == c.getPosX() && ca.getPosY() == c.getPosY()+1){
				tabVoisin.add(new CaseDijkstra(c, ca));	
			}
			if(ca.getPosX() == c.getPosX()+1 && ca.getPosY() == c.getPosY()){
				tabVoisin.add(new CaseDijkstra(c, ca));
			}
			if(ca.getPosX() == c.getPosX() && ca.getPosY() == c.getPosY()-1){
				tabVoisin.add(new CaseDijkstra(c, ca));
			}
		}

		return tabVoisin;
	}

	ArrayList<Case> dejaVu = new ArrayList<Case>();


	//	public ArrayList<Case> resolveMumpus(Case c) {
	//		System.out.print("("+c.getPosX()+";"+c.getPosY()+"); ");
	//		
	//		if(c.isTresor()) {
	//			return this.cheminDijkstra;
	//		}else{
	//		
	//		ArrayList<CaseDijkstra> voisin = generateVoisin(c);
	//		
	//			for(Case cas : voisin) {
	//				if (cas != null && !cas.isPuit() && !cas.isWumpus()) {
	//					if(!dejaVu.contains(cas)) {
	//						dejaVu.add(cas);
	//						this.cheminDijkstra.add(c);
	//						return resolveMumpus(cas);
	//					}
	//				}
	//			}
	//		}
	//		return cheminDijkstra;
	//	}

	public CaseDijkstra resolveMumpusIteratif(Case pion, Case arrivee) {

		ArrayList<CaseDijkstra> casesTestees = new ArrayList<CaseDijkstra>(); //Cases testees contiendra toutes les cases dont on a teste les voisins
		ArrayList<CaseDijkstra> casesATester = new ArrayList<CaseDijkstra>();
		ArrayList<CaseDijkstra> casesVoisines = new ArrayList<CaseDijkstra>();
		CaseDijkstra solutionTrouvee = null;

		casesATester.add(new CaseDijkstra(null,pion));

		while (casesATester.size()!=0 && solutionTrouvee==null) //Tant qu'il reste encore des cases a tester et qu'on a pas trouve de solution
		{
			if (!casesTestees.contains(casesATester.get(0)))
			{
				if (casesATester.get(0).isTresor()) {
					solutionTrouvee = casesATester.get(0);
				}

				else {
					casesVoisines = generateVoisin(casesATester.get(0));
					for(CaseDijkstra c : casesVoisines) {
						if (c != null && !c.isPuit() && !c.isWumpus()) {
							casesATester.add(c);
						}
					}
				}
			}

			casesTestees.add(casesATester.get(0));
			casesATester.remove(0);

		}

		return solutionTrouvee;
	}

	//Retourne la case de depart
	public Case findFirstCase() {
		Case firstCase = new Case();
		for(Case c : cases) {
			if(c.getPosX() == 0 && c.getPosY()== this.size-1) {
				firstCase = c;
			}

		}
		return firstCase;
	}

	//Affiche la case du tr�sor
	public void findTresor() {
		for(Case c : cases) {
			if(c.isTresor()) {
				System.out.print("Tresor : ("+c.getPosX()+";"+c.getPosY()+"); ");
			}
		}
	}

	//Retourne la case du tr�sor
	public Case caseTresor() {
		Case tresor = new Case();
		for(Case c : cases) {
			if(c.isTresor()) {
				tresor = c;
			}
		}
		return tresor;
	}

	public void findPuit() {
		for(Case c : cases) {
			if(c.isPuit()) {
				System.out.print("Puit : ("+c.getPosX()+";"+c.getPosY()+"); ");
			}
		}
	}

	public void generateBriseAndOdeur() {

		for(Case c : cases) {
			Case c1=getCase(c.getPosX()-1, c.getPosY());
			Case c2=getCase(c.getPosX(), c.getPosY()+1);
			Case c3=getCase(c.getPosX()+1, c.getPosY());
			Case c4=getCase(c.getPosX(), c.getPosY()-1);
			if(c.isPuit()) {
				if(c1!=null)
					c1.setBrise(true);
				if(c2!=null)
					c2.setBrise(true);
				if(c3!=null)
					c3.setBrise(true);
				if(c4!=null)
					c4.setBrise(true);
			}else if(c.isWumpus()) {
				if(c1!=null)
					c1.setOdeur(true);
				if(c2!=null)
					c2.setOdeur(true);
				if(c3!=null)
					c3.setOdeur(true);
				if(c4!=null)
					c4.setOdeur(true);
			}
		}
	}





	/*   if(keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN]){
	        p.y -= 5;
	    }

	    if(keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT]){
	        p.x += 5;
	    }

	    if(keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT]){
	        p.x -= 5;
	    }*/



	//_________________________________________GETSET
	public ArrayList<Case> getCases() {
		return cases;
	}
	public Case getCase(int x, int y){
		for(Case c : this.cases){
			if(c.getPosX()==x&&c.getPosY()==y)
				return c;
		}
		return null;
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

	public CaseDijkstra getCaseDijkstra() {
		return caseDijkstra;
	}

	public void setCaseDijkstra(CaseDijkstra caseDijkstra) {
		this.caseDijkstra = caseDijkstra;
	}


}