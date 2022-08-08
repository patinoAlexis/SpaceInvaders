/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders;


import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author MSI PC
 */
enum TipoObjeto{
    enemigo,bala,nave;
}
public abstract class ObjetoJuego{
    Point posicion;
    Juego juego;
    TipoObjeto to;
    boolean vivo=true;
    public ObjetoJuego(Point pos, Juego juego){
        this.posicion = pos;
        this.juego = juego;
    }
    
    public abstract boolean actualizar();
    public abstract void paintObject(Graphics g);
}
