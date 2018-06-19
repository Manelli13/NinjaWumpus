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
	// private PanneauMission pan;

	// Constructeurs____________________________________________________________________

	// Constructeur de la fenetre affichant le plateau
	public PanneauPlateau(Plateau p, Fenetre plat) {
		this.plat = plat;
		this.p = p;
		this.buttons = new JButton[this.p.getSize()][this.p.getSize()];
		this.setSize(400,400);
		this.setLayout(new GridLayout(this.p.getSize(), this.p.getSize()));

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
						IA();

					}
				});
			}
		}
		this.setVisible(true);
		this.deplacerAgent(0, this.p.getSize() - 1);

		System.out.println("Nope");
	}

	public PanneauPlateau() {

	}

	public void IA () {
		
		this.Joueur=this.p.getAgent();
			try {
				System.out.println("Position X:"+this.Joueur.getPosX()+" Y :"+this.Joueur.getPosY());
				if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurHaut())
					deplacerAgent(this.Joueur.getPosY(),this.Joueur.getPosX()-1);
				else if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurDroit())
					deplacerAgent(this.Joueur.getPosY()+1,this.Joueur.getPosX());
				else if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurGauche())
					deplacerAgent(this.Joueur.getPosY()-1,this.Joueur.getPosX());
				else if(!this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isMurBas())
					deplacerAgent(this.Joueur.getPosY(),this.Joueur.getPosX()+1);
				else if(this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isTresor())
					return;
				else if(this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isPuit())
					return;
				else if(this.p.getCase(this.Joueur.getPosX(), this.Joueur.getPosY()).isWumpus())
					return;
				System.out.println("Position X:"+this.Joueur.getPosX()+" Y :"+this.Joueur.getPosY());
				Thread.sleep(1000);
			}catch(Exception e) {
				System.out.println("C'est BALO :noel:");
			}
	}
	public void deplacerAgent(int newPosX, int newPosY){
		int oldPosX=this.p.getAgent().getPosX();
		int oldPosY = this.p.getAgent().getPosY();
		this.p.deplacerAgent(newPosY, newPosX);

		CombinedIcon cio = (CombinedIcon) this.buttons[oldPosX][oldPosY].getIcon();

		CombinedIcon ci = (CombinedIcon) this.buttons[newPosY][newPosX].getIcon();
		ci.add(cio.getLastImageIcon(), this.getWidth() / this.buttons.length - 3, this.getHeight() / this.buttons.length - 3);

		cio.removeLast();


		this.setSkinAfterMovement(ci, newPosY, newPosX);
		this.setSkinAfterMovement(cio, oldPosX, oldPosY);
		this.refresh();
		
		popup(newPosY, newPosX);
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
	public void setSkinAfterMovement(CombinedIcon ci, int i, int j) {
		this.buttons[i][j].setIcon(ci);
	}

	// Assigne un Skin aux diff�rentes cases
	public CombinedIcon Skin(int i, int j) {
		ImageIcon c = new ImageIcon("Case.png");
		if(i==0)
			c = new ImageIcon("CaseBordure1.png");
		if(i==this.buttons.length-1)
			c = new ImageIcon("CaseBordure2.png");
		if(j==0)
			c = new ImageIcon("CaseBordure3.png");
		if(j==this.buttons.length-1)
			c = new ImageIcon("CaseBordure4.png");
		if(i==0&&j==0)
			c = new ImageIcon("CaseBordure6.png");
		if(i==0&&j==buttons.length-1)
			c = new ImageIcon("CaseBordure5.png");		
		if(i==buttons.length-1&&j==0)
			c = new ImageIcon("CaseBordure7.png");	
		if(i==buttons.length-1&&j==buttons.length-1)
			c = new ImageIcon("CaseBordure8.png");

		CombinedIcon ci = new CombinedIcon();
		ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);

		if (p.getCase(i, j).isMurHaut()) {
			c = new ImageIcon("Mur_h.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}

		if (p.getCase(i, j).isMurDroit()) {
			c = new ImageIcon("Mur_d.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if (p.getCase(i, j).isMurBas()) {
			c = new ImageIcon("Mur_b.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if (p.getCase(i, j).isMurGauche()) {
			c = new ImageIcon("Mur_g.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(p.getCase(i, j).isWumpus()){
			c = new ImageIcon("wumpus.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(p.getCase(i, j).isPuit()){
			c = new ImageIcon("puit.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(p.getCase(i, j).isTresor()){
			c = new ImageIcon("tresor.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}
		if(i==this.p.getSize()-1&&j==0){
			c = new ImageIcon("Pion_rouge.png");
			ci.add(c, plat.getWidth() / this.buttons.length - 3, plat.getHeight() / this.buttons.length - 3);
		}

		return ci;

	}

	public void popup(int newPosY, int newPosX ){
		if(p.getCase(newPosY,newPosX).isPuit())
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

		if(p.getCase(newPosY,newPosX).isWumpus())
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

		if(p.getCase(newPosY,newPosX).isTresor())
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




}
