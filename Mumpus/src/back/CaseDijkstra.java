package back;

import javax.swing.ImageIcon;

import Model.CombinedIcon;
import view.Fenetre;
import view.PanneauPlateau;

public class CaseDijkstra extends Case {
	
	private CaseDijkstra pere;
	
	private String fleche_gauche = "\u2190";
	private String fleche_droite = "\u2192";
	private String fleche_bas = "\u2193";
	private String fleche_haut = "\u2191";
	private String depart = "¤";
	
	public CaseDijkstra() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CaseDijkstra(CaseDijkstra pere, Case c) {
		super(c.getPosX(), c.getPosY(), c.isMurDroit(), c.isMurGauche(), c.isMurHaut(), c.isMurBas(), c.isPuit(), c.isTresor(), c.isWumpus());
		this.pere=pere;
	}
	
	public String chemin(PanneauPlateau pan, Fenetre plat) {
		
		CaseDijkstra c = this;
		String s = "";
		String sym = "";
		boolean end = false;
		
		//Pour les symboles dans la console, mettre UTF-8 dans les réglages d'Eclipse
		while(!end) {
			if(c.getPere()==null) {
				sym = convertToArrows(null, c) +" | "+ sym;
				s = "("+c.getPosX()+";"+c.getPosY()+");" + s;
				end = true;
			}else {
				sym = convertToArrows(c.getPere(), c) +" | "+ sym;
				s = "("+c.getPosX()+";"+c.getPosY()+");" + s;
				CombinedIcon ci = new CombinedIcon();
				ci = pan.Skin(c.getPosX(), c.getPosY());
				ImageIcon img = new ImageIcon("CheminDijkstra.png");
				ci.add(img, plat.getWidth() / pan.getButtons().length - 3, plat.getHeight() / pan.getButtons().length - 3);
				pan.setSkinAfterMovement(ci, c.getPosX(),c.getPosY());
				c = c.getPere();
			}
		}
		
		return s + "\n" + sym;
	}
	
	public String convertToArrows(CaseDijkstra pere, CaseDijkstra fils) {
		String fleche = " ";
		if(pere == null) {
			fleche = depart;
		}else {
			if(pere.getPosX() > fils.getPosX()) {
				fleche =  fleche_haut;
			}
			else if(pere.getPosX() < fils.getPosX()) {
				fleche =  fleche_bas;
			}
			else if(pere.getPosY() > fils.getPosY()) {
				fleche =  fleche_gauche;
			}
			else if(pere.getPosY() < fils.getPosY()) {
				fleche =  fleche_droite;
			}
		}
		return fleche;
	}

	public CaseDijkstra getPere() {
		return pere;
	}

	public void setPere(CaseDijkstra pere) {
		this.pere = pere;
	}
	
}
