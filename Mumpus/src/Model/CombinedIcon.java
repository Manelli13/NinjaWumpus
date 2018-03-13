package Model;
import javax.swing.ImageIcon;
import javax.swing.Icon;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class CombinedIcon extends ImageIcon implements Icon {
	 private ArrayList<Icon> icons;

	 //Constructeurs_____________________________________________________________
	 
     public CombinedIcon() {
         icons = new ArrayList<Icon>();
     }

     //Fonctions_________________________________________________________________
     
     //Ajoute une image a l'icone, et la redimensionne a la taille passee en parametre
     public void add(ImageIcon i, int width, int height) {
    	 ImageIcon icon = new ImageIcon(i.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
         icons.add(icon);
     }

     //Retire une image
     void remove(Icon i) {
         icons.remove(i);
     }
     
     public void removeLast() {
    	 icons.remove(icons.size() - 1);
     }
     
     public ImageIcon getLastImageIcon() {
    	 return (ImageIcon) icons.get(icons.size() - 1);
     }

     //Affiche toutes les images de l'icone une par une
     public void paintIcon(Component c, Graphics g, int x, int y) {

        
         for(int i = 0; i < icons.size(); i++) {
             icons.get(i).paintIcon(c, g, 1, 0);
             
         }
     }
}
