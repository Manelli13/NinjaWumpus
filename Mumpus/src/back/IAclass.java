package back;

import java.util.ArrayList;

public class IAclass {

	private static ArrayList<Case> casesVues;
	private ArrayList<Case> caseOdorante;
	private int riskCase[];
	private Plateau plat;
	
	
	
	public IAclass(ArrayList<Case> caseOdorante, Plateau plat) {
		this.caseOdorante = new ArrayList<Case>();
		this.plat = plat;
	}

	public boolean IA(int x, int y) {
		
		this.riskCase = new int[4];
		
		Case currentCase = plat.getCase(x, y);
		
		if(currentCase.isTresor()) {
			return true;
			
		}
		if(currentCase.isWumpus() || currentCase.isPuit()) {
			return false;
		}
		
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
		
		if(this.riskCase[0] == min) {
			plat.deplacerAgent(x+1, y);
		}
		if(this.riskCase[1] == min) {
			plat.deplacerAgent(x-1, y);
		}
		if(this.riskCase[2] == min) {
			plat.deplacerAgent(x, y+1);
		}
		if(this.riskCase[3] == min) {
			plat.deplacerAgent(x, y-1);
		}
		
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
		
		
		return 0;
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
