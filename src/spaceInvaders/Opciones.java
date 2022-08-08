package spaceInvaders;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.FontFormatException;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public abstract class Opciones extends JButton {


    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public Opciones(String t){
        super(t);
        Font bit = establecerFuente();
        if(bit == null)
            setFont(new Font("Arial",Font.PLAIN,30));
        else{
            setFont(bit);
        }
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false); 

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
    }
    public Font establecerFuente() {
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream("let_bit.ttf"));
            return Font.createFont(Font.TRUETYPE_FONT, myStream).deriveFont(20f);
        } catch(IOException | FontFormatException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
