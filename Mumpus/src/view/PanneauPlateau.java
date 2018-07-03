package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.util.Scanner;
import javax.swing.JPanel;

import Model.CombinedIcon;
import back.*;

public class PanneauPlateau extends JPanel {

	private JButton buttons[][]; // Cases du plateau
	private Fenetre plat; // Fenetre correspondant au plateau
	private Joueur Joueur; // Pion a deplacer
	private Plateau p; // Plateau de jeu
	private IAclass Ia;
	// private PanneauMission pan;

	// Constructeurs____________________________________________________________________

	// Constructeur de la fenetre affichant le plateau
	public PanneauPlateau(Plateau p, Fenetre plat) {
		this.plat = plat;
		this.p = p;
		this.buttons = new JButton[this.p.getSize()][this.p.getSize()];
		this.setSize(400,400);
		this.setLayout(new GridLayout(this.p.getSize(), this.p.getSize()));
		this.setIa(new IAclass(this.p, this));
		// Generation des cases du plateau
		CombinedIcon ci = new CombinedIcon();
		for (int i = 0; i < this.p.getSize(); i++) {
			for (int j = 0; j < this.p.getSize(); j++) {
				this.buttons[i][j] = new JButton(/* i+","+j */);
				ci = Skin(i, j);
				this.buttons[i][j].setIcon(ci);
				this.add(this.buttons[i][j]);
				this.buttons[i][j].addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						JButton a = (JButton) e.getSource();
						//System.out.println(a.getX()/a.getWidth());
						//deplacerAgent(a.getX()/a.getWidth(), a.getY()/a.getHeight());
						Thread IAthread = new Thread() {
							public void run() {
								while(!getIa().brain()) {
									try {
										Thread.sleep(100);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}						    		
								}
								//this.stop();
							}
						} ; 


						IAthread.start();


					}
				});
			}
		}
		this.setVisible(true);
		//this.deplacerAgent(0, this.p.getSize() - 1);

		System.out.println("Nope");
	}

	public PanneauPlateau() {

	}

	//	public void IA () {
	//		
	//		this.Joueur=this.p.getAgent();
	//			try {
	//				System.out.println("Position X:"+this.Joueur.getPosX()+" Y :"+this.Joueur.getPosY());
	//				if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurHaut())
	//					deplacerAgent(this.Joueur.getPosY(),this.Joueur.getPosX()-1);
	//				else if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurDroit())
	//					deplacerAgent(this.Joueur.getPosY()+1,this.Joueur.getPosX());
	//				else if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurGauche())
	//					deplacerAgent(this.Joueur.getPosY()-1,this.Joueur.getPosX());
	//				else if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurBas())
	//					deplacerAgent(this.Joueur.getPosY(),this.Joueur.getPosX()+1);
	//				else if(this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isTresor())
	//					return;
	//				else if(this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isPuit())
	//					return;
	//				else if(this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isWumpus())
	//					return;
	//				System.out.println("Position X:"+this.Joueur.getPosX()+" Y :"+this.Joueur.getPosY());
	//				Thread.sleep(1000);
	//			}catch(Exception e) {
	//				System.out.println("C'est BALO :noel:");
	//			}
	//	}
	public void deplacerAgent(int newPosX, int newPosY){
		int oldPosX=this.p.getAgent().getPosX();
		int oldPosY = this.p.getAgent().getPosY();
		this.p.deplacerAgent(newPosX, newPosY);

		CombinedIcon previousIcon = (CombinedIcon) this.buttons[oldPosY][oldPosX].getIcon();

		CombinedIcon nextIcon = (CombinedIcon) this.buttons[newPosY][newPosX].getIcon();
		nextIcon.add(previousIcon.getLastImageIcon(), this.getWidth() / this.buttons.length - 3, this.getHeight() / this.buttons.length - 3);

		previousIcon.removeLast();


		this.setSkinAfterMovement(nextIcon, newPosX, newPosY);
		this.setSkinAfterMovement(previousIcon, oldPosX, oldPosY);
		this.refresh();

		popup(newPosX, newPosY);
	}
	public void setPanneauPlateau(Plateau p) {
		this.p = p;
		this.buttons = new JButton[this.p.getSize()][this.p.getSize()];
		this.setSize(950, 950);
		this.setLayout(new GridLayout(this.p.getSize(), this.p.getSize()));

		// Generation des cases du plateau
		CombinedIcon ci = new CombinedIcon();
		for (int i = 0; i < this.p.getSize(); i++) {
			for (int j = 0; j < this.p.getSize(); j++) {
				this.buttons[i][j] = new JButton(/* i+","+j */);
				ci = Skin(i, j);
				this.buttons[i][j].setIcon(ci);
				this.add(this.buttons[i][j]);
			}
		}
		this.plat.setPlateau(p);
		this.setVisible(false);
		this.setVisible(true);
	}

	// Methodes
	// ____________________________________________________________________

	// Permet de modifier le contenu du PanneauMission pan
	public void changePanel(String texte, String titre) {
		//this.pan.setText(texte);
		//this.pan.repaint();// Appelle la méthode PanneauMission.paintComponent
	}

	// Permet d'actualiser l'affichage apres un deplacement
	public void setSkinAfterMovement(CombinedIcon ci, int x, int y) {
		this.buttons[y][x].setIcon(ci);
	}

	// Assigne un Skin aux diff�rentes cases
	public CombinedIcon Skin(int y, int x) {
		ImageIcon c = new ImageIcon("Case.png");
		if(y==0)
			c = new ImageIcon("CaseBordure1.png");
		if(y==this.buttons.length-1)
			c = new ImageIcon("CaseBordure2.png");
		if(x==0)
			c = new ImageIcon("CaseBordure3.png");
		if(x==this.buttons.length-1)
			c = new ImageIcon("CaseBordure4.png");
		if(x==0&&y==0)
			c = new ImageIcon("CaseBordure6.png");
		if(y==0&&x==buttons.length-1)
			c = new ImageIcon("CaseBordure5.png");		
		if(y==buttons.length-1&&x==0)
			c = new ImageIcon("CaseBordure7.png");	
		if(x==buttons.length-1&&y==buttons.length-1)
			c = new ImageIcon("CaseBordure8.png");

		CombinedIcon ci = new CombinedIcon();
		ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);

		if (p.getCase(x, y).isMurHaut()) {
			c = new ImageIcon("Mur_h.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}

		if (p.getCase(x, y).isMurDroit()) {
			c = new ImageIcon("Mur_d.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if (p.getCase(x, y).isMurBas()) {
			c = new ImageIcon("Mur_b.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if (p.getCase(x, y).isMurGauche()) {
			c = new ImageIcon("Mur_g.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(p.getCase(x, y).isWumpus()){
			c = new ImageIcon("wumpus.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(p.getCase(x, y).isPuit()){
			c = new ImageIcon("puit.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(p.getCase(x, y).isTresor()){
			c = new ImageIcon("tresor.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(y==this.p.getSize()-1&&x==0){
			c = new ImageIcon("Pion_rouge.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}

		return ci;

	}

	public void popup(int newPosX, int newPosY ){
		if(p.getCase(newPosX,newPosY).isPuit())
		{
			JOptionPane jop = new JOptionPane();

			ImageIcon icon = new ImageIcon("putindance.gif");
			int option = jop.showConfirmDialog(null, "Voulez vous recommencez ?","Perdue !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);


			if(option == JOptionPane.OK_OPTION){

				setVisible(false); 

				plat.lancerPartie(p.getSize(),plat.getHeight(), plat.getWidth());

			}
			if(option == JOptionPane.NO_OPTION){

				setVisible(false); 
				plat.dispose();


				Fenetre fen  = new Fenetre();

			}
		}

		if(p.getCase(newPosX,newPosY).isWumpus())
		{
			JOptionPane jop = new JOptionPane();

			ImageIcon icon = new ImageIcon("paresseux2.gif");
			int option = jop.showConfirmDialog(null, "Voulez vous recommencez ?","Perdue !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);


			if(option == JOptionPane.OK_OPTION){

				setVisible(false); 

				plat.lancerPartie(p.getSize(),plat.getHeight(), plat.getWidth());

			}
			if(option == JOptionPane.NO_OPTION){

				setVisible(false); 
				plat.dispose();


				Fenetre fen  = new Fenetre();

			}
		}

		if(p.getCase(newPosX,newPosY).isTresor())
		{
			JOptionPane jop = new JOptionPane();

			ImageIcon icon = new ImageIcon("win.gif");
			int option = jop.showConfirmDialog(null, "Voulez vous recommencez ?","Gagn� !", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);


			if(option == JOptionPane.OK_OPTION){

				setVisible(false); 

				plat.lancerPartie(p.getSize(),plat.getHeight(), plat.getWidth());

			}
			if(option == JOptionPane.NO_OPTION){

				setVisible(false); 
				plat.dispose();


				Fenetre fen  = new Fenetre();

			}
		}
	}
	// Accesseurs
	// ____________________________________________________________________

	// Retourne le bouton de coordonneees (i,j)
	public JButton getButton(int i, int j) {
		return this.buttons[i][j];
	}

	// Remplace le bouton demande par le bouton passe en parametre
	public void setButton(JButton button, int i, int j) {
		this.buttons[i][j] = button;
	}

	// Permet de recuperer le plateau de la fenetre
	public Plateau getPlateau() {
		return this.p;
	}

	public void setPlateau(Plateau p) {
		this.p = p;
	}

	// Mets à jour le PanneauMission
	public void refresh() {
		this.setVisible(false);
		this.setVisible(true);
	}

	public JButton[][] getButtons() {
		return buttons;
	}

	public void setButtons(JButton[][] buttons) {
		this.buttons = buttons;
	}

	public IAclass getIa() {
		return Ia;
	}

	public void setIa(IAclass ia) {
		Ia = ia;
	}




}
