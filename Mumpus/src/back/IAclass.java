package back;

import java.util.ArrayList;

import view.PanneauPlateau;

public class IAclass {

	private static ArrayList<Case> casesVues;
	private ArrayList<Case> caseOdorante;
	private int riskCase[];
	private Plateau plat;
	private PanneauPlateau p;


	public IAclass(Plateau plat, PanneauPlateau p) {
		this.caseOdorante = new ArrayList<Case>();
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

		this.riskCase[0] = evaluateRisk(plat.getCase(x+1, y));
		this.riskCase[1] = evaluateRisk(plat.getCase(x-1, y));
		this.riskCase[2] = evaluateRisk(plat.getCase(x, y+1));
		this.riskCase[3] = evaluateRisk(plat.getCase(x, y-1));

		int min1,min2,min;
		min1 = Math.min(this.riskCase[0], this.riskCase[1]);
		min2 = Math.min(this.riskCase[2], this.riskCase[3]);
		min = Math.min(min1, min2);

		if(currentCase.isOdeur()) {
			Case ca = findWumpus(currentCase);
			shotWumpus(ca);
		}
		
		//deplacerAgent(a.getX()/a.getWidth(), a.getY()/a.getHeight());
		if(this.riskCase[0] == min) {
			x++;
		}
		else if(this.riskCase[1] == min) {
			x=x-1;
		}
		else if(this.riskCase[2] == min) {
			y++;
		}
		else if(this.riskCase[3] == min) {
			y=y-1;
		}
		System.out.println("After : X="+x+"|| Y="+y);
		p.deplacerAgent(x, y);
		return false;

	}



	private void shotWumpus(Case findWumpus) {
		if(findWumpus != null) {
			plat.getAgent().tirer();
		}
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
//			Case case1 =plat.getCase(c.getPosX()+1, c.getPosY());
//			Case case2 =plat.getCase(c.getPosX()-1, c.getPosY());
//			Case case3 =plat.getCase(c.getPosX(), c.getPosY()+1);
//			Case case4 =plat.getCase(c.getPosX(), c.getPosY()-1);
			if (casesVues.contains(c)) {
				if(c.isBrise() || c.isOdeur())
					risk++;
				else
					risk+=10000;
			}
			else {
				risk=0;
			}
//			if(casesVues.contains(case1)) {
//				if(case1.isBrise() || case1.isOdeur())
//					risk++;
//			}
//			if (casesVues.contains(case2)) {
//				if(case2.isBrise() || case2.isOdeur())
//					risk++;
//			}
//			if(casesVues.contains(case3)) {
//				if(case3.isBrise() || case3.isOdeur())
//					risk++;
//			}
//			if(casesVues.contains(case4)) {
//				if(case4.isBrise() || case4.isOdeur())
//					risk++;
//			}

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
