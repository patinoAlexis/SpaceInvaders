/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders;

//import java.awt.BorderLayout;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.Dimension;
//import java.awt.Image;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author MSI PC
 */
public class VentanaPrincipal extends JFrame implements KeyListener, MouseListener{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private MenuPrincipal menu = new MenuPrincipal(this);
    private Juego juego;

    private boolean jugando = false;    

    public VentanaPrincipal(int w, int h, String nombre) {
        super(nombre);

        Dimension d = new Dimension(w, h);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);  
        inicializar();

        menu.iniciar(0);
    }

    public void inicializar(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);

        setResizable(false);
        setLocationRelativeTo(null);
        Image icon = new ImageIcon(getClass().getResource("icon.png")).getImage();
        this.setIconImage(icon);
        menu.setVisible(true);
        add(menu);
        setVisible(true);
    }
    public Font establecerFuente(){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("let_bit.ttf"));
        } catch(IOException | FontFormatException e) {
            //excepcion
        }
        return null;
    }

    public void iniciarJuego(){
        jugando = true;
        this.remove(menu);
        juego = new Juego(this);
        add(juego);
        setVisible(true);
        juego.iniciar(-330);
    }
    public void iniciarMenu(){
        jugando = false;
        this.remove(juego);
        add(menu);
        setVisible(true);
        menu.iniciar(-330);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(jugando)
            juego.keyPressed(e);
        else
            menu.keyPressed();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(jugando)
            juego.y = 0;
        else
            menu.keyPressed();
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
