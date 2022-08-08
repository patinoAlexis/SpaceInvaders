package spaceInvaders;

import java.awt.Graphics;
import java.awt.Point;
import javax.swing.ImageIcon;

import java.awt.Image;

enum TipoBala{
     azul,rojo,verde;   
}
/*
 * Dentro de este clase se encuentra las caracteristicas y la opcion de imprimir
 * que tendra una bala dentro del juego.
 * 
 */
public class Bala extends ObjetoJuego {

    int speed=3;
    private Image img;
    TipoBala tb;
    public Bala(Point pos,Juego juego){
        super(pos,juego);
        to = TipoObjeto.bala;
        img = (new ImageIcon("balas.png")).getImage();
        tb = TipoBala.azul;
    }

    @Override
    public boolean actualizar() {
        if(!vivo)
        {
            return false;
        }
            
        posicion.y-=speed;
        if(posicion.y < 20)
            vivo=false;
        return true;
    }
    
    @Override
    public void paintObject(Graphics g) {
        if(!vivo)
            return;

        g.drawImage(img, posicion.x-15,posicion.y-50,posicion.x+15, posicion.y,300,30,360,130,null);

    }
    
    
}
