/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;



/**
 *
 * @author MSI PC
 */
public class OpcionesMenu extends Opciones{
    /**
     *
     */
    private static final long serialVersionUID = 7458682979586110416L;

    private Color colorTexto;
    private Color colorRect;
    private Color colorRectPress;
    private Color colorRectRoll;
    private Rectangle rect;

    public OpcionesMenu(String t, ActionListener al,Rectangle r) {
        super(t);
        addActionListener(al);
        setText(t);

        colorTexto = new Color(255,255,255);
        setForeground(colorTexto);
        colorRect = new Color(18,10,143,255);
        colorRectPress = colorRect;
        colorRectRoll = colorRect;

        setSize(r.width,r.height);
        setBounds(r);

        rect = r;
    }
    public void setColorTexto(Color col){
        colorTexto = col;
    }
    public void setColorRectPress(Color col){
        colorRectPress = col;
    }
    public void setColorRectRoll(Color col){
        colorRectRoll = col;
    }
    public void setColorRect(Color col){
        colorRect = col;
    }
    @Override
    public void paint(Graphics g){
        setBounds(rect);
        if(getModel().isPressed())
        g.setColor(new Color(colorRectPress.getRed(),colorRectPress.getGreen(),colorRectPress.getBlue(),255));
        else
        {
            if(getModel().isRollover())
                g.setColor(new Color(colorRectRoll.getRed(),colorRectRoll.getGreen(),colorRectRoll.getBlue(),255));
            else
                g.setColor(new Color(colorRect.getRed(),colorRect.getGreen(),colorRect.getBlue(),255));
        }
        g.fillRect(0,0, this.getWidth(),this.getHeight());
        super.paint(g);
    }
}
