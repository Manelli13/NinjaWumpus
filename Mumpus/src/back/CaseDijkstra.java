package back;

public class CaseDijkstra extends Case {
	
	private CaseDijkstra pere;

	public CaseDijkstra() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaseDijkstra(CaseDijkstra pere, Case c) {
		super(c.getPosX(), c.getPosY(), c.isMurDroit(), c.isMurGauche(), c.isMurHaut(), c.isMurBas(), c.isPuit(), c.isTresor(), c.isWumpus());
		this.pere=pere;
	}
	
	public String chemin() {
		
		CaseDijkstra c = this;
		String s = "";
		boolean end = false;
		
		while(!end) {
			if(c.getPere()==null) {
				s = "("+c.getPosX()+";"+c.getPosY()+");" + s;
				end = true;
			}else {
				s = "("+c.getPosX()+";"+c.getPosY()+");" + s;
				c = c.getPere();
			}
		}
		
		return s;
	}

	public CaseDijkstra getPere() {
		return pere;
	}

	public void setPere(CaseDijkstra pere) {
		this.pere = pere;
	}
	
}
