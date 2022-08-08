/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders;

import javax.swing.*;
//import javax.swing.ImageIcon;
//import javax.swing.JButton;
//import javax.swing.JComponent;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.event.MouseInputListener;


import java.awt.*;
import java.awt.event.*;
//import java.awt.image.BufferStrategy;
//import java.awt.BorderLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.File;

/**
 *
 * @author MSI PC
 */
public class MenuPrincipal extends JPanel implements Runnable, ActionListener{
    /**
    *
    */
    private static final long serialVersionUID = 1L;

    private VentanaPrincipal vent;
    private Thread HiloPrincipal;

    private boolean corriendo = false;
    private Etiqueta nom1,nom2,nom3;
    Image background = new ImageIcon(getClass().getResource("estrellas.jpg")).getImage();
    int y=0;

    public MenuPrincipal(VentanaPrincipal vent) {
        this.vent = vent;
    }

    public void iniciar(int y) {
        if (corriendo)
            return;
        this.y = y;
        HiloPrincipal = new Thread(this);
        HiloPrincipal.start();
        corriendo = true;
    }

    public synchronized void stop() {
        try {
            HiloPrincipal.join();
            corriendo = false;
        } catch (Exception e) {
            System.out.println("Error al intentar detener el juego");
        }
    }

    @Override
    public void run() {
        long initialTime = System.nanoTime();
        final double timeU = 1000000000 / 20;
        final double timeF = 1000000000 / 60;
        double deltaU = 0, deltaF = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (corriendo) {

            long currentTime = System.nanoTime();
            deltaU += (currentTime - initialTime) / timeU;
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if (deltaU >= 1) {
                actualizar();
                deltaU--;
            }

            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - timer > 1000) {
                System.out.println("Frames: " + frames);
                frames = 0;
                timer += 1000;
            }
        }

        stop();
    }

    public void render() {
        repaint();
    }

    public void actualizar() {
        if(y > -330)
        {
            if(y == 0)
            {
                nom1 = new Etiqueta("Un proyecto de POOA",new Rectangle(0,20,1000,80));
                nom1.deriveFont(50f);
                nom1.setForeground(new Color(255,255,255,255));
                nom1.setHorizontalAlignment(JLabel.CENTER);
                nom1.setVerticalAlignment(JLabel.CENTER);
                add(nom1);

                nom2 = new Etiqueta("Hecho por:",new Rectangle(0,100,1000,60));
                nom2.deriveFont(45f);
                nom2.setForeground(new Color(255,255,255,255));
                nom2.setHorizontalAlignment(JLabel.CENTER);
                nom2.setVerticalAlignment(JLabel.CENTER);
                add(nom2);

                nom3 = new Etiqueta("Alexis Ivan Patiño Victoria", new Rectangle(0,360,1000,60));
                nom3.deriveFont(40f);
                nom3.setForeground(new Color(255,255,255,255));
                nom3.setHorizontalAlignment(JLabel.CENTER);
                nom3.setVerticalAlignment(JLabel.CENTER);
                add(nom3);
            }

            y-=1;
        }
        else
        {
            if(corriendo)
            {
                remove(nom1);
                remove(nom2);
                remove(nom3);
                //remove(nom4);
                corriendo = false;
                dibujarMenu();
            }
            
        }
            
    }

    public void dibujarMenu(){
        OpcionesMenu op = new OpcionesMenu("Jugar",this, (new Rectangle(375,300,250,110)));
        op.setColorRect(new Color(25,25,112));
        op.setColorRectPress(new Color(51,60,135));
        op.setColorRectRoll(new Color(57,57,211));
        op.setFocusable(false);
        add(op);

        /*op = new OpcionesMenu("Puntuaciones",this, (new Rectangle(650,300,250,110)));
        op.setColorRect(new Color(25,25,112));
        op.setColorRectPress(new Color(51,60,135));
        op.setColorRectRoll(new Color(57,57,211));
        op.setFocusable(false);
        add(op);*/

        Etiqueta titulo = new Etiqueta("Space Invaders",new Rectangle(220,20, 800, 100));
        titulo.deriveFont(50f);
        add(titulo);

        OpcionesImagen op2 = new OpcionesImagen("Cerrar",this,new ImageIcon("cerrar.png"),new Point(10,550));
        op2.setRollover(new Color(240,0,0,200), new Rectangle(0,10,180,160));
        op2.setFocusable(false);
        add(op2);
        repaint();
    }
    public Font establecerFuente(){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("let_bit.ttf"));
        } catch(IOException | FontFormatException e) {
            //excepcion
        }
        return null;
    }
    public void keyPressed() {
        if(y != -330)
            y=-330;

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, y,null);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String se = e.getActionCommand();
        
        if(se.equals("Jugar"))
        {
            vent.iniciarJuego();
        }
        else
        {
            if(se.equals("Puntuaciones"))
            {
                System.out.println("funciono puntuaciones");
            }
            else
            {
                if(se.equals(""))
                {
                    vent.dispose();
                    System.exit(0);
                }
            }
            
        }
    }
    

}
