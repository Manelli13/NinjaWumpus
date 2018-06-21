package back;

import java.util.ArrayList;

import view.PanneauPlateau;

public class IAclass {

	private static ArrayList<Case> casesVues;
	private ArrayList<Case> caseOdorante;
	private int riskCase[];
	private Plateau plat;
	private PanneauPlateau p;
	private ArrayList<Case> caseHumide;
	private ArrayList<Case> puitsPotentiel;


	public IAclass(Plateau plat, PanneauPlateau p) {
		puitsPotentiel = new ArrayList<Case>();
		this.caseOdorante = new ArrayList<Case>();
		this.caseHumide = new ArrayList<Case>();
		this.plat = plat;
		this.p=p;
		IAclass.casesVues=new ArrayList<Case>();
	}

	public boolean IA() {
		int x =this.plat.getAgent().getPosX();
		int y =this.plat.getAgent().getPosY();
		System.out.println("Before : X="+x+"|| Y="+y);
		this.riskCase = new int[4];

		Case currentCase = plat.getCase(x, y);

		if(currentCase.isTresor()) {
			System.out.println("Vous avez gagné");
			return true;

		}
		if(currentCase.isWumpus() || currentCase.isPuit()) {
			System.out.println("Vous etes mort dévoré d'une noyade");
			return false;

		}

		if (!casesVues.contains(currentCase))
			casesVues.add(currentCase);
		
		if (currentCase.isBrise()) {
			caseHumide.add(currentCase);
		}
		if(currentCase.isOdeur()) {
			this.caseOdorante.add(currentCase);
			Case ca = findWumpus(currentCase);
			shotWumpus(ca);
		}
		if (!currentCase.isBrise()&&!currentCase.isOdeur()) {
			this.riskCase[0] = evaluateRisk(plat.getCase(x+1, y));
			this.riskCase[1] = evaluateRisk(plat.getCase(x-1, y));
			this.riskCase[2] = evaluateRisk(plat.getCase(x, y+1));
			this.riskCase[3] = evaluateRisk(plat.getCase(x, y-1));
			
			int min1,min2,min;
			min1 = Math.min(this.riskCase[0], this.riskCase[1]);
			min2 = Math.min(this.riskCase[2], this.riskCase[3]);
			min = Math.min(min1, min2);
			
			ArrayList<Case> leastRiskyCase = new ArrayList();
			//deplacerAgent(a.getX()/a.getWidth(), a.getY()/a.getHeight());
			if(this.riskCase[0] == min) {
				leastRiskyCase.add(this.plat.getCase(x+1, y));
				//			x++;
			}
			if(this.riskCase[1] == min) {
				leastRiskyCase.add(this.plat.getCase(x-1, y));
				//			x=x-1;
			}
			if(this.riskCase[2] == min) {
				leastRiskyCase.add(this.plat.getCase(x, y+1));
				//			y++;
			}
			if(this.riskCase[3] == min) {
				leastRiskyCase.add(this.plat.getCase(x, y-1));
				//			y=y-1;
			}
			
			double rand = (Math.random() * (leastRiskyCase.size()));
//		int randIndex = (int)((Math.random() * (leastRiskyCase.size() - 1)));
			int randIndex= (int)rand;
			//		System.out.println("After : X="+x+"|| Y="+y);
			//		p.deplacerAgent(x, y);
			p.deplacerAgent(leastRiskyCase.get(randIndex).getPosX(),leastRiskyCase.get(randIndex).getPosY());
			
		}
		else {
			ArrayList<Case> leastRiskyCase = new ArrayList();
			if(casesVues.contains(plat.getCase(x+1, y))) {
				leastRiskyCase.add(plat.getCase(x+1, y));
			}
			if(casesVues.contains(plat.getCase(x-1, y))) {
				leastRiskyCase.add(plat.getCase(x-1, y));
			}
			if(casesVues.contains(plat.getCase(x, y+1))) {
				leastRiskyCase.add(plat.getCase(x, y+1));
			}
			if(casesVues.contains(plat.getCase(x, y-1))) {
				leastRiskyCase.add(plat.getCase(x, y-1));
			}
			double rand = (Math.random() * (leastRiskyCase.size()));
			int randIndex= (int)rand;
			p.deplacerAgent(leastRiskyCase.get(randIndex).getPosX(),leastRiskyCase.get(randIndex).getPosY());
			
		}
		return false;

	}



	private void shotWumpus(Case findWumpus) {
		if(findWumpus != null) {
			plat.getAgent().tirer();
			System.out.println("Tirer");
		}
	}

	private Case findPuit(Case currentCase) {
		for(Case c : puitsPotentiel) {
			//TODO
		}
		return null;
	}

	private Case findWumpus(Case currentCase) {

		this.caseOdorante.add(currentCase);
		Case ca = new Case();

		switch(caseOdorante.size()) {
		case 2:
			if((caseOdorante.get(0).getPosX() > caseOdorante.get(1).getPosX() && caseOdorante.get(0).getPosX() == caseOdorante.get(1).getPosX() - 2) 
					|| (caseOdorante.get(1).getPosX() > caseOdorante.get(0).getPosX() && caseOdorante.get(1).getPosX() == caseOdorante.get(0).getPosX() - 2)) {

				int x = (caseOdorante.get(0).getPosX() + caseOdorante.get(1).getPosX()) / 2;
				ca.setPosX(x);
				ca.setPosY(caseOdorante.get(0).getPosY());
			}

			if((caseOdorante.get(0).getPosY() > caseOdorante.get(1).getPosY() && caseOdorante.get(0).getPosY() == caseOdorante.get(1).getPosY() - 2) 
					|| (caseOdorante.get(1).getPosY() > caseOdorante.get(0).getPosY() && caseOdorante.get(1).getPosY() == caseOdorante.get(0).getPosY() - 2)) {
				int y = (caseOdorante.get(0).getPosY() + caseOdorante.get(1).getPosY()) / 2;
				ca.setPosX(caseOdorante.get(0).getPosX());
				ca.setPosY(y);
			}
			break;

		case 3:
			int x = (caseOdorante.get(0).getPosX() + caseOdorante.get(1).getPosX() + caseOdorante.get(2).getPosX()) / 3;
			int y = (caseOdorante.get(0).getPosY() + caseOdorante.get(1).getPosY() + caseOdorante.get(2).getPosY()) / 3;
			ca.setPosX(x);
			ca.setPosY(y);
			break;

		default:
			return null;
		}
		return ca;

	}



	public int evaluateRisk(Case c) {
		int risk =0;
		if (c!=null) {
			if (casesVues.contains(c)) {
				if(c.isBrise() || c.isOdeur())
					risk+=2;
				else
					risk++;
			}
			else {
				risk=0;
			}
			return risk;
		}
		else
			return 10000;
	}




	public ArrayList<Case> getCasesVues() {
		return casesVues;
	}
	public void setCasesVues(ArrayList<Case> casesVues) {
		this.casesVues = casesVues;
	}
	public int[] getRiskCase() {
		return riskCase;
	}
	public void setRiskCase(int[] riskCase) {
		this.riskCase = riskCase;
	}
	public Plateau getPlat() {
		return plat;
	}
	public void setPlat(Plateau plat) {
		this.plat = plat;
	}
}
