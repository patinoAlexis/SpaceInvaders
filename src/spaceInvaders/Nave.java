package spaceInvaders;


import java.awt.Graphics;
import java.awt.Point;

import java.awt.Image;

import javax.swing.ImageIcon;


public class Nave extends ObjetoJuego {

    private static int DERECHA = 0;
    private static int IZQUIERDA = 1;
    public int direccion = 2;

    public int vel_mov = 3;

    private boolean movimiento = false;
    private Image img;
    public Nave(Point pos, Juego juego) {
        super(pos, juego);
        to = TipoObjeto.nave;
        img = (new ImageIcon("nave.png")).getImage();
    }

    public void setMovimiento(boolean bol) {
        movimiento = bol;
    }

    public void derecha() {
        direccion = DERECHA;
    }

    public void izquierda() {
        direccion = IZQUIERDA;
    }

    public void disparar(){
        Bala bala=new Bala(new Point(posicion.x + 50,posicion.y),juego);
        juego.objetos.add(bala);
        juego.balas.add(bala);
    }

    @Override
    public boolean actualizar() {
        if (movimiento) {
            switch (direccion) {
                case 0:
                    posicion.x = Math.min(680, posicion.x + vel_mov);
                    break;
                case 1:
                    posicion.x = Math.max(180, posicion.x - vel_mov);
            }
        }
        return false;
    }

    @Override
    public void paintObject(Graphics g) {
        g.translate(20, 0);
        g.drawImage(img, posicion.x, posicion.y, null);
    }

    

}
