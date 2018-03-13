package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

import back.Case;
import back.CaseDijkstra;
import back.Plateau;

public class Fenetre extends JFrame{
	private int height;
	private int width;
	private Plateau plateau;
	public Fenetre(){
		this.setLocation(0,0);
		this.setTitle("Wumpus : Edition Babtou");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.width = (int)screenSize.getWidth();
		this.height = (int)screenSize.getHeight();
		this.setSize(820,860);
		//this.setSize(width,height-(height-950));
		this.setVisible(true);
		//this.plateau=new Plateau(4);
		this.setContentPane(new PanneauMenu(this));
		//this.setContentPane(new PanneauPlateau(plateau, this));
	}
	
	public void lancerPartie(int platesize, int height, int width) {
		this.plateau=new Plateau(platesize);
		PanneauPlateau pan =new PanneauPlateau(plateau, this);
		pan.setSize(400,400);
		this.setContentPane(pan);
		try {
			CaseDijkstra soluce = plateau.resolveMumpusIteratif(plateau.findFirstCase(), plateau.caseTresor());
			//System.out.println(soluce.size());
			System.out.println(soluce.chemin());
		} catch (Exception e){
			
		}
	}
	public static void main (String[] args){
		Fenetre fen = new Fenetre();
	}
	public Plateau getPlateau() {
		return plateau;
	}
	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

}
