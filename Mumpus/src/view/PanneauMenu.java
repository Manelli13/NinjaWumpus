package view;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PanneauMenu extends JPanel implements ActionListener{
	private JButton config;
	private JButton partiePreco;
	private JButton partieAlea;
	private JButton titre;
	private JButton lancerpartie;
	private JButton retour;
	private JButton load;
	private JButton tutoriel;
	private Fenetre fen;
	private int taillePlateau;
	private JTextField textField;
	public PanneauMenu (Fenetre fen){
		this.fen=fen;
		this.setLayout(null);
		this.setSize(fen.getWidth(),fen.getHeight());
		this.taillePlateau=0;
		this.Init();
	}
	public void refresh(){
		this.setVisible(false);
		this.setVisible(true);
	}
	/*public void Init(){
		config= new JButton("Configurer");
		lancerpartie= new JButton("Lancer la partie");
		config.setBounds(this.getWidth()/2-150,200,300,100);
		lancerpartie.setBounds(this.getWidth()/2-150,450,300,100);
		config.addActionListener(this);
		lancerpartie.addActionListener(this);
		this.add(config);
		this.add(lancerpartie);
	}*/
	public void Init(){
		this.setLayout(null);
		this.partieAlea=new JButton("Commencer");
		this.partieAlea.addActionListener(this);
		partieAlea.setBounds(this.getWidth()/2-150,250,300,100);
		this.add(this.partieAlea);
	}
	public void PartieAlea(){
		this.setLayout(null);
		this.titre= new JButton("Entrez la taille du plateau");
		this.retour= new JButton("Retour au menu");
		titre.setEnabled(false);
		textField = new JTextField();
		this.textField.setColumns(10);
		this.textField.addActionListener(this);
		titre.setBounds(this.getWidth()/2-150,100,300,100);
		textField.setBounds(this.getWidth()/2-150,250,300,100);
		retour.setBounds(this.getWidth()/2-150,400,300,100);
		retour.addActionListener(this);
		this.add(titre);
		this.add(retour);
		this.add(textField);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==this.load){
			System.out.println("Fonction non programm�e");
		}
		if (e.getSource()==this.partiePreco){
			System.out.println("Fonction non programm�e");
		}
		if(e.getSource()==this.tutoriel){
			System.out.println("Fonction non programm�e");
		}
		if(e.getSource()==this.textField){
			this.taillePlateau = Integer.parseInt(this.textField.getText());
			this.titre.setText("Taille entree");
			if (this.taillePlateau <4)
				this.taillePlateau=4;
			if (this.taillePlateau>30)
				this.taillePlateau=30;
			this.fen.lancerPartie(this.taillePlateau, this.getWidth(),this.getHeight());
		}
		if (e.getSource()==config){
			this.removeAll();
			this.revalidate();
			this.refresh();
			//this.config();
		}
		if (e.getSource()==this.partieAlea){
			this.removeAll();
			this.revalidate();
			this.refresh();
			this.PartieAlea();
		}
		if (e.getSource()==this.retour){
			this.removeAll();
			this.revalidate();
			this.refresh();
			this.Init();
		}
		/*if (e.getSource()==this.lancerpartie){
			if(this.taillePlateau==0){
				this.removeAll();
				this.revalidate();
				this.refresh();
				this.Init();
			}
			else{
				this.setVisible(false);
				this.fen.lancerPartie(this.taillePlateau, this.getWidth(),this.getHeight());
			}

		}*/

	}
}
