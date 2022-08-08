package spaceInvaders;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.awt.Rectangle;
import java.awt.FontFormatException;
import javax.swing.JLabel;
import java.io.File;
import java.awt.Graphics;
/*
 * Dentro de esta clase nos sirve simplemente para poder desplegar el texto
 * Como el texto que aparece al inicio del juego o el que aparece dentro
 * de los botones
 */
public class Etiqueta extends JLabel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Rectangle rect=null;
    public Etiqueta(String t, Rectangle r) {
        super(t);
        setForeground(Color.WHITE);
        setFont(establecerFuente().deriveFont(20f));
        rect = r;
        setBounds(r);
    }

    public Font establecerFuente(){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("let_bit.ttf"));
        } catch(IOException | FontFormatException e) {
            //excepcion
        }
        return null;
    }
    public void deriveFont(float f){
        setFont(establecerFuente().deriveFont(f));
    }
    @Override
    public void paint(Graphics g){
        if(rect != null)
            setBounds(rect);
        super.paint(g);
    }
}
