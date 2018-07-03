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
	private Case lastPosition;
	public static int win=0;
	public static int loose=0;
	public static int game=0;

	private int iteration;

	public IAclass(Plateau plat, PanneauPlateau p) {
		IAclass.game++;
		puitsPotentiel = new ArrayList<Case>();
		this.caseOdorante = new ArrayList<Case>();
		this.caseHumide = new ArrayList<Case>();
		this.plat = plat;
		this.p=p;
		this .iteration=0;
		IAclass.casesVues=new ArrayList<Case>();
	}

	public boolean brain() {
		if(iteration<20)
			return IA2();
		else
			return IA();
	}

	public boolean IA2() {
		this.iteration++;
		int x =this.plat.getAgent().getPosX();
		int y =this.plat.getAgent().getPosY();
		System.out.println("IA2");
		this.riskCase = new int[4];

		Case currentCase = plat.getCase(x, y);

		if(currentCase.isTresor()) {
			System.out.println("Vous avez gagn�");
			IAclass.win++;
			System.out.println("% Win :"+ (double)IAclass.win/IAclass.game);
			System.out.println("% loose :"+IAclass.loose/IAclass.game);
			System.out.println("nbGame :"+IAclass.game);
			return true;

		}
		if(currentCase.isWumpus() || currentCase.isPuit()) {
			System.out.println("Vous etes mort d�vor� d'une noyade");
			IAclass.loose--;
			System.out.println("% Win :"+ (double)IAclass.win/IAclass.game);
			System.out.println("% loose :"+IAclass.loose/IAclass.game);
			System.out.println("nbGame :"+IAclass.game);
			return true;

		}

		if (!casesVues.contains(currentCase))
			casesVues.add(currentCase);

		if (currentCase.isBrise()) {
			caseHumide.add(currentCase);
			findPuit(currentCase);
		}
		if(currentCase.isOdeur()) {
			this.caseOdorante.add(currentCase);
			Case ca = findWumpus(currentCase);
			shotWumpus(ca);
		}
		if (!currentCase.isBrise()&&!currentCase.isOdeur()) {
			this.riskCase[0] = evaluateRisk2(plat.getCase(x+1, y));
			this.riskCase[1] = evaluateRisk2(plat.getCase(x-1, y));
			this.riskCase[2] = evaluateRisk2(plat.getCase(x, y+1));
			this.riskCase[3] = evaluateRisk2(plat.getCase(x, y-1));

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
	public boolean IA() {
		int x =this.plat.getAgent().getPosX();
		int y =this.plat.getAgent().getPosY();
		System.out.println("IA");
		this.riskCase = new int[4];

		Case currentCase = plat.getCase(x, y);

		if(currentCase.isTresor()) {
			System.out.println("Vous avez gagn�");
			return true;
		}
		if(currentCase.isWumpus() || currentCase.isPuit()) {
			System.out.println("Vous etes mort d�vor� d'une noyade");
			return true;
		}

		if (!casesVues.contains(currentCase))
			casesVues.add(currentCase);

		if (currentCase.isBrise()) {
			caseHumide.add(currentCase);
			findPuit(currentCase);
		}

		if(currentCase.isOdeur()) {
			this.caseOdorante.add(currentCase);
			Case ca = findWumpus(currentCase);
			shotWumpus(ca);
		}
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
			if(this.plat.getCase(x+1, y)!=null)
				leastRiskyCase.add(this.plat.getCase(x+1, y));
			//			x++;
		}
		if(this.riskCase[1] == min) {
			if(this.plat.getCase(x-1, y)!=null)
				leastRiskyCase.add(this.plat.getCase(x-1, y));
			//			x=x-1;
		}
		if(this.riskCase[2] == min) {
			if(this.plat.getCase(x, y+1)!=null)
				leastRiskyCase.add(this.plat.getCase(x, y+1));
			//			y++;
		}
		if(this.riskCase[3] == min) {
			if(this.plat.getCase(x, y-1)!=null)
				leastRiskyCase.add(this.plat.getCase(x, y-1));
			//			y=y-1;
		}

		double rand = (Math.random() * (leastRiskyCase.size()));
		//		int randIndex = (int)((Math.random() * (leastRiskyCase.size() - 1)));
		int randIndex= (int)rand;
		//		System.out.println("After : X="+x+"|| Y="+y);
		//		p.deplacerAgent(x, y);
		lastPosition= currentCase;
		p.deplacerAgent(leastRiskyCase.get(randIndex).getPosX(),leastRiskyCase.get(randIndex).getPosY());

		return false;

	}

	private void checkAndAddPuit(Case currentCase, Case case1, Case case2) {
		if (case1!= null && case2!=null && !caseHumide.contains(case1) && !caseHumide.contains(case2)) {
			if(case1.isBrise() && case2.isBrise())
				puitsPotentiel.add(plat.getCase((currentCase.getPosX()+case1.getPosX()+case2.getPosX())/3, (currentCase.getPosY()+case1.getPosY()+case2.getPosY())/3));
		}
	}


	private void findPuit(Case currentCase) {
		Case case1=plat.getCase(currentCase.getPosX()+1, currentCase.getPosY()-1);
		Case case2=plat.getCase(currentCase.getPosX()+1, currentCase.getPosY()+1);
		Case case3=plat.getCase(currentCase.getPosX()-1, currentCase.getPosY()+1);
		Case case4=plat.getCase(currentCase.getPosX()-1, currentCase.getPosY()-1);
		checkAndAddPuit(currentCase, case1,  case2);
		checkAndAddPuit(currentCase, case1,  case3);
		checkAndAddPuit(currentCase, case2,  case4);
		checkAndAddPuit(currentCase, case4,  case3);
	}

	private void shotWumpus(Case findWumpus) {
		if(findWumpus != null) {
			plat.getAgent().tirer();
			if(findWumpus.isWumpus()) {
				findWumpus.setWumpus(false);
				Case adjacente1 = plat.getCase(findWumpus.getPosX()+1, findWumpus.getPosY());
				Case adjacente2 = plat.getCase(findWumpus.getPosX()-1, findWumpus.getPosY());
				Case adjacente3 = plat.getCase(findWumpus.getPosX(), findWumpus.getPosY()+1);
				Case adjacente4 = plat.getCase(findWumpus.getPosX(), findWumpus.getPosY()-1);
				if(adjacente1!=null) {
					adjacente1.setOdeur((false));
				}
				if(adjacente2!=null) {
					adjacente2.setOdeur((false));
				}
				if(adjacente3!=null) {
					adjacente3.setOdeur((false));
				}
				if(adjacente4!=null) {
					adjacente4.setOdeur((false));
				}

			}

			System.out.println("Un cri terrible retentit");
			this.p.killWumpus(findWumpus);
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



	public int evaluateRisk1(Case c) {
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
	public int evaluateRisk2(Case c) {
		int risk =0;
		if(puitsPotentiel.contains(c))
			risk+=10000;
		if (c!=null) {
			if (casesVues.contains(c)) {
				risk++;
				if(c.isBrise()) {
					Case adjacente1 = this.plat.getCase(c.getPosX()+1, c.getPosY());
					Case adjacente2 = this.plat.getCase(c.getPosX()-1, c.getPosY());
					Case adjacente3 = this.plat.getCase(c.getPosX(), c.getPosY()+1);
					Case adjacente4 = this.plat.getCase(c.getPosX(), c.getPosY()-1);
					if (puitsPotentiel.contains(adjacente1))
						risk++;
					if (puitsPotentiel.contains(adjacente2))
						risk++;
					if (puitsPotentiel.contains(adjacente3))
						risk++;
					if (puitsPotentiel.contains(adjacente4))
						risk++;
				}
				if (c.isOdeur())
					risk+=2;

			}
			else {
				risk=0;
			}
			return risk;
		}
		else
			return 10000;
	}
	public int evaluateRisk(Case c) {
		int risk =0;
		if(puitsPotentiel.contains(c))
			return 10000;
		if (c!=null) {
			if(c.equals(lastPosition))
				return 10000;
			if (casesVues.contains(c)) {
				risk++;
				if(c.isBrise()) {
					Case adjacente1 = this.plat.getCase(c.getPosX()+1, c.getPosY());
					Case adjacente2 = this.plat.getCase(c.getPosX()-1, c.getPosY());
					Case adjacente3 = this.plat.getCase(c.getPosX(), c.getPosY()+1);
					Case adjacente4 = this.plat.getCase(c.getPosX(), c.getPosY()-1);
					if (puitsPotentiel.contains(adjacente1))
						risk++;
					if (puitsPotentiel.contains(adjacente2))
						risk++;
					if (puitsPotentiel.contains(adjacente3))
						risk++;
					if (puitsPotentiel.contains(adjacente4))
						risk++;
				}
				if (c.isOdeur())
					risk++;
				return risk;
			}
			else {
				Case adjacente1 = this.plat.getCase(c.getPosX()+1, c.getPosY());
				Case adjacente2 = this.plat.getCase(c.getPosX()-1, c.getPosY());
				Case adjacente3 = this.plat.getCase(c.getPosX(), c.getPosY()+1);
				Case adjacente4 = this.plat.getCase(c.getPosX(), c.getPosY()-1);
				if (casesVues.contains(adjacente1)) {
					if(casesVues.get(casesVues.indexOf(adjacente1)).isBrise()||casesVues.get(casesVues.indexOf(adjacente1)).isOdeur())
						risk+=2;
					else
						risk++;					
				}
				if (casesVues.contains(adjacente2)) {
					if(casesVues.get(casesVues.indexOf(adjacente2)).isBrise()||casesVues.get(casesVues.indexOf(adjacente2)).isOdeur())
						risk+=2;
					else
						risk++;		
				}
				if (casesVues.contains(adjacente3)) {
					if(casesVues.get(casesVues.indexOf(adjacente3)).isBrise()||casesVues.get(casesVues.indexOf(adjacente3)).isOdeur())
						risk+=2;
					else
						risk++;					
				}
				if (casesVues.contains(adjacente4)) {
					if(casesVues.get(casesVues.indexOf(adjacente4)).isBrise()||casesVues.get(casesVues.indexOf(adjacente4)).isOdeur())
						risk+=2;
					else
						risk++;					
				}
				if (puitsPotentiel.contains(adjacente1))
					risk++;
				if (puitsPotentiel.contains(adjacente2))
					risk++;
				if (puitsPotentiel.contains(adjacente3))
					risk++;
				if (puitsPotentiel.contains(adjacente4))
					risk++;
				return risk;
			}

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
