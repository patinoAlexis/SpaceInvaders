package spaceInvaders;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.Point;
import java.awt.Rectangle;
public class OpcionesImagen extends Opciones{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ImageIcon img;
    private Point d;
    private Rectangle rect;
    private Color colRect;

    public OpcionesImagen(String t, ActionListener ac,ImageIcon image, Point d){
        super("");
        addActionListener(ac);
        setName(t);
        setSize(image.getIconWidth()+20,image.getIconHeight()+20);
        setBounds(d.x,d.y,image.getIconWidth()+20,image.getIconHeight()+20);
        
        this.d = d;
        img = image;
        setIcon(new ImageIcon(img.getImage()));
        setRollover(Color.BLUE,new Rectangle(0,0,image.getIconWidth()+20,image.getIconHeight()+20));
    }
    public void setRollover(Color col, Rectangle r){
        colRect = col;
        rect = r;
    }
    @Override
    public void paint(Graphics g){
        setBounds(d.x,d.y,img.getIconWidth()+20,img.getIconHeight()+20);
        if(getModel().isRollover())
        {
            g.setColor(colRect);
            g.fillRect(rect.x,rect.y, rect.width,rect.height);
        }
        super.paint(g);
        
        
    }
}
