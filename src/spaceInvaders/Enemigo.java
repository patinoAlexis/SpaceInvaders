package spaceInvaders;

import java.awt.Graphics;
import java.awt.Point;

import java.util.Random;
import javax.swing.ImageIcon;
import java.awt.Image;

/*
 * Dentro de esta clase se encuentran las caracteristicas que se tendran de cada enemigo
 * del juego, donde existiran 3 tipos de enemigos diferentes.
 */

enum TipoEnemigo{
    uno,dos,tres;
}

public class Enemigo extends ObjetoJuego {

    public static int PUNT_ENEMIGO_1 = 10;
    public static int PUNT_ENEMIGO_2 = 20;
    public static int PUNT_ENEMIGO_3 = 30;

    public int vel_mov = 1;
    public boolean vivo=true;
    private Image img;
    public TipoEnemigo te;
    public boolean abajo = false;

    private int irAbajo=1;
    private int lado=0;

    public int limiteInf;
    public int limiteSup;

    public Enemigo(Point pos, Juego juego) {
        super(pos, juego);
        to = TipoObjeto.enemigo;
        img = new ImageIcon("enemigos.png").getImage();
        te = TipoEnemigo.uno;
    }
    public Enemigo(Point pos, Juego juego,TipoEnemigo t) {
        super(pos, juego);
        to = TipoObjeto.enemigo;
        img = new ImageIcon("enemigos.png").getImage();
        seleccionarTipo(t);
    }

    public void seleccionarTipo(TipoEnemigo t){
        te = t;
    }
    public void moverseAbajo(){
        abajo = true;
    }
    public void setLado(int i){
        lado = i;
    }
    @Override
    public boolean actualizar() {
        if(!vivo)
            return false;
        if(abajo)
        {
            if(irAbajo !=5)
            {
                posicion.y+=8;
                irAbajo++;
            }
            else
            {
                irAbajo=1;
                abajo = false;
            }
        }
        else
        {
            if(lado ==1)
            {
                posicion.x+=vel_mov;
                if(posicion.x > limiteSup)
                    lado = 0;
            }   
            else
            {
                if(lado == 0)
                {
                    posicion.x-=vel_mov;
                    if(posicion.x < limiteInf)
                        lado=1;
                }
                    
            }
                
        }
        return true;
    }
    public void setLimites(int limiteInferior, int limiteSuperior){
        limiteInf =limiteInferior;
        limiteSup = limiteSuperior;
    }
    public static TipoEnemigo enemigoRandom(){
        Random r = new Random();
        int i = r.nextInt(10);
        switch(i){
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            return TipoEnemigo.uno;
            case 5:
            case 6:
            case 7:
            return TipoEnemigo.dos;
            case 8:
            case 9:
            default: return TipoEnemigo.tres;
        }
    }
    @Override
    public void paintObject(Graphics g) {
        if(!vivo)
        {
            return;
        }
        switch(te)
        {
            case uno:
                g.drawImage(img,posicion.x,posicion.y,posicion.x+80, posicion.y+60,30,10,120,100,null);
                break;
            case dos:
                g.drawImage(img,posicion.x,posicion.y,posicion.x+80, posicion.y+60,150,10,220,90,null);
                break;
            case tres:
                g.drawImage(img,posicion.x,posicion.y,posicion.x+80, posicion.y+60,300,10,380,100,null);
                break;
        }
        

    }
    
}
