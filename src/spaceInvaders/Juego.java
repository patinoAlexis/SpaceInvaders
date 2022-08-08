/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceInvaders;

import javax.swing.*;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;


import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.concurrent.TransferQueue;
import java.io.File;


/**
 *
 * @author MSI PC
 */
public class Juego extends JPanel implements Runnable, ActionListener{
    /**
    *
    */
    private static final long serialVersionUID = 1L;
    private VentanaPrincipal vent;
    private Thread HiloPrincipal;

    private boolean corriendo = false;
    private boolean dibujarOpciones = false;
    private boolean perder = false;
    Image background = new ImageIcon(getClass().getResource("estrellas.jpg")).getImage();
    int y=0;

    public int num_puntos=0;
    public int max_puntos=0;
    public int inicioFila;
    public int limiteSup=0;
    public int tiempo=200;
    public int espacio;

    private Etiqueta puntos;
    private Etiqueta vidas;
    private Etiqueta[] empezar = new Etiqueta[2];
    private Etiqueta numOleada;
    private OpcionesMenu pausar;

    public ArrayList<ObjetoJuego> objetos = new ArrayList<ObjetoJuego>();
    public ArrayList<Bala> balas = new ArrayList<>();
    public ArrayList<ArrayList<Enemigo>> enemigos = new ArrayList<>();
    private Nave nave;

    public int bajar = 0;
    public int disp = 0;
    private boolean spawnear=false;
    private boolean pulsarTecla = false;

    public Juego(VentanaPrincipal vent) {
        this.vent = vent;
        //setOpaque(true);
    }

    public void iniciar(int y) {
        if (corriendo)
            return;
        if(background == null)
            System.out.println("fallo");
        this.y = y;
        HiloPrincipal = new Thread(this);
        corriendo = true;
        HiloPrincipal.start();
    }
    public void iniciar(){
        if(corriendo)
            return;
        HiloPrincipal = new Thread(this);
        corriendo = true;
        HiloPrincipal.start();
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
        final double timeU = 1000000000 / 100;
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
        if(y < 0)
        {
            y+=1;
        }
        else
        {
            if(!dibujarOpciones){
                dibujarJuego();
                dibujarOpciones = true;
            }
            else
            {
                bajar++;
                disp++;
                if(max_puntos == num_puntos)
                {
                    if(!pulsarTecla)
                    {
                        
                        empezar[0].setVisible(true);
                        empezar[1].setVisible(true);
                        nave.posicion = new Point(450,600);
                        nave.setMovimiento(false);
                        int i = Integer.parseInt(numOleada.getText());
                        numOleada.setText(Integer.toString(i+1));
                        actualizarPuntos(num_puntos);
                        pulsarTecla=true;
                    }
                    else
                    {
                        if(spawnear)
                        {
                            empezar[0].setVisible(false);
                            empezar[1].setVisible(false);
                            nave.setMovimiento(true);
                            pulsarTecla = false;
                        }
                    }
                }
                if(spawnear)
                    crearOleada(numOleada.getText());
                else
                {
                    for(ArrayList<Enemigo> columna : enemigos)
                    {
                        ArrayList<Enemigo> lis = (ArrayList<Enemigo>) columna.clone();
                        for(Enemigo en : lis)
                        {
                            if(bajar >= tiempo){
                                en.abajo = true;
                            }
                            if(en.posicion.y >= 500)
                            {
                                columna.remove(en);
                                bajarVida(en);
                            }
                            for(Bala b : balas){
                                if(en.posicion.x <=  (b.posicion.x+15) && (en.posicion.x+80) >= (b.posicion.x+15))
                                {
                                    if(en.posicion.y <=  (b.posicion.y) && (en.posicion.y+50) >= (b.posicion.y))
                                    {
                                        if(en.vivo)
                                        {
                                            if(en.te == TipoEnemigo.uno)
                                                num_puntos += Enemigo.PUNT_ENEMIGO_1;
                                            else
                                            {
                                                if(en.te == TipoEnemigo.dos)
                                                    num_puntos += Enemigo.PUNT_ENEMIGO_2;
                                                else
                                                    num_puntos += Enemigo.PUNT_ENEMIGO_3;
                                            }
                                            b.vivo = false;
                                            columna.remove(en);
                                            objetos.remove(en);
                                            
                                        }
                                        en.vivo = false;
                                        
                                    }
                                }
                            }
                        }
                        
                    }
                    if(bajar >= tiempo)
                        bajar = 0;
                    if(Integer.parseInt(vidas.getText())  <= 0)
                        perder();
                }
                
                ArrayList<ObjetoJuego> lista = (ArrayList<ObjetoJuego>) objetos.clone();
                for(ObjetoJuego ob : lista)
                {
                    if(ob.vivo)
                        ob.actualizar();
                    else
                    {
                        objetos.remove(ob);
                        if(ob.to == TipoObjeto.bala)
                            balas.remove(ob);
                    } 
                }
            }
            
        }
            
    }
    public void crearOleada(String s){
        int oleada = Integer.parseInt(s);
        int num_fila;
        int num_colum;
        int suma_espacio=0;
        switch(oleada){
            case 1:
                num_colum = 4;                
                num_fila = 3;
                break;
            case 2:
                num_colum = 5;
                num_fila = 4;
                break;
            case 4:
                tiempo = 190;
                num_colum = 6;
                num_fila = 5;
                suma_espacio = 25;
                break;
            case 5:
                tiempo = 180;
                num_colum = 6;
                num_fila = 5;
                suma_espacio = 25;
                break;
            case 6:
                tiempo = 170;
                num_colum = 6;
                num_fila = 5;
                suma_espacio = 25;
                break;
            default: 
                num_colum = 6;
                num_fila = 5;
                suma_espacio = 25;
                break;
        }
        espacio = ((720/num_fila)/2)+suma_espacio;
        if(oleada == 1)
            limiteSup = espacio*(3)-espacio;
        else
        {
            if(oleada == 2)
                limiteSup = espacio*(4)-espacio;
            else
                limiteSup = espacio*(5)-espacio;
        }
        inicioFila = 200;
        int inicioColum = 20;
        enemigos = new ArrayList<>();


        for(int i = 0 ; i < num_colum ; i++){
            ArrayList<Enemigo> fila = new ArrayList<Enemigo>();
            int aux=inicioFila;
            int aux2=limiteSup;
            int aux3=0;
            TipoEnemigo te = Enemigo.enemigoRandom();
            for(int j = 0 ; j < num_fila ; j++ ){
                Enemigo e = new Enemigo(new Point(aux,inicioColum),this,te);
                
                aux+=espacio;
                e.setLimites(180+aux3,700-aux2);
                aux2 -=espacio;
                aux3 +=espacio;
                fila.add(e);
                objetos.add(e);
            }
            if(te == TipoEnemigo.uno)
                max_puntos +=10*num_fila;
            else
            {
                if(te == TipoEnemigo.dos)
                    max_puntos+=20*num_fila;
                else
                    max_puntos+=30*num_fila;
            }
            inicioColum +=80;
            enemigos.add(fila);
        }

        ArrayList<Bala> lista = (ArrayList<Bala>)balas.clone();
        for(Bala b : lista){
            balas.remove(b);
            objetos.remove(b);
        }
        spawnear = false;
    }
    
    public void dibujarJuego(){  
        
        Etiqueta eti = new Etiqueta("Puntos:",new Rectangle(820,10,150,60));
        eti.deriveFont(20f);
        add(eti);
        puntos = new Etiqueta("0000",new Rectangle(820,40,150,60));
        puntos.deriveFont(20f);
        add(puntos);

        eti = new Etiqueta("Oleada:",new Rectangle(820,100,150,60));
        eti.deriveFont(20f);
        add(eti);
        numOleada = new Etiqueta("0",new Rectangle(820,130,150,60));
        numOleada.deriveFont(20f);
        add(numOleada);

        eti = new Etiqueta("Vidas:",new Rectangle(820,230,150,60));
        eti.deriveFont(20f);
        add(eti);
        vidas = new Etiqueta("3",new Rectangle(820,250,150,60));
        vidas.deriveFont(20f);
        add(vidas);

        empezar[0] = new Etiqueta("Presione cualquier tecla", new Rectangle(200,200,600,100));
        empezar[0].setHorizontalAlignment(JTextField.CENTER);
        empezar[0].setVerticalAlignment(JTextField.CENTER);
        empezar[0].deriveFont(30f);
        add(empezar[0]);

        empezar[1] = new Etiqueta("para empezar", new Rectangle(350,300,300,100));
        empezar[1].setHorizontalAlignment(JTextField.CENTER);
        empezar[1].setVerticalAlignment(JTextField.CENTER);
        empezar[1].deriveFont(30f);
        add(empezar[1]);

        OpcionesImagen op = new OpcionesImagen("Salir",this,new ImageIcon("salir.png"),new Point(0,600));
        op.setRollover(new Color(204,204,0,200), new Rectangle(5,-10,190,160));
        op.setFocusable(false); 
        add(op);

        pausar = new OpcionesMenu("Pausar",this, (new Rectangle(20,20,170,110)));
        pausar.setColorRect(new Color(25,25,112));
        pausar.setColorRectPress(new Color(51,60,135));
        pausar.setColorRectRoll(new Color(57,57,211));
        pausar.setFocusable(false);
        add(pausar);

        nave = new Nave(new Point(450,600),this);
        nave.setMovimiento(true);
        objetos.add(nave);
    }

    public void actualizarPuntos(int punt){
        puntos.setVisible(false);
         int i = Integer.parseInt(puntos.getText());
        int num = Integer.toString(i+punt).length();
        
        switch(num)
        {
            case 0: String s = "0000";puntos.setText(s);break;
            case 1: String d = "000"+Integer.toString(i+punt);puntos.setText(d);break;
            case 2: String e = "00"+Integer.toString(i+punt);puntos.setText(e);break;
            case 3: String f = "0"+Integer.toString(i+punt);puntos.setText(f);break;
            default:String h = Integer.toString(i+punt);puntos.setText(h);
        }
        puntos.setVisible(true);
    }
    public void bajarVida(Enemigo en){

        en.vivo =false;
        objetos.remove(en);

        int i = Integer.parseInt(vidas.getText());
        vidas.setText(Integer.toString(i-1));

        if(en.te == TipoEnemigo.uno)
            max_puntos -= 10;
        else
        {
            if(en.te == TipoEnemigo.dos)
                max_puntos-=20;
            else
                max_puntos-=30;
        }
    }
    
    public static Font establecerFuente(){
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File("let_bit.ttf"));
        } catch(IOException | FontFormatException e) {
            //excepcion
        }
        return null;
    }

    public void keyPressed(KeyEvent e) {
        if(y != 0)
        {
            y=0;
        }
        else
        {
            
            int i = e.getKeyCode();
            if(i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A)
            {
                nave.izquierda();
            }
            else
            {
                if(i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT)
                {
                    nave.derecha();
                }
            }
            if(i == KeyEvent.VK_SPACE){
                if(disp >=30)
                {
                    nave.disparar();
                    disp=0;
                }       
            }
            if(i == KeyEvent.VK_ESCAPE){
                if(corriendo)
                    pausar();
                else
                    reanudar();
            }
            else
            {
                if(pulsarTecla)
                    spawnear = true;
            }
        }
    }

    private BufferedImage getBuffer(){
        Dimension d = getSize();
        BufferedImage buffer=new BufferedImage(d.width,d.height,BufferedImage.TYPE_INT_RGB);
        Graphics g2 = buffer.createGraphics();
        g2.drawImage(background, 0, y,null);
        if(y == 0)
        {
            g2.setColor(new Color(30,32,64,200));
            g2.fillRect(0, 0,200, 750);
            g2.fillRect(800, 0,200, 750);
        }
        if(!perder)
        {
            if(y == 0){
                try{
                for (ObjetoJuego ob : objetos) {
                    ob.paintObject(g2);
                }
                }catch(ConcurrentModificationException e){

                }
            }
        }
        else
        {
            g2.setColor(new Color(182,68,68));
            g2.fillRect(200,100,600, 400);
        }
        return buffer;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getBuffer(),0,0,null);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String se = e.getActionCommand();
        if(se.equals("Pausar"))
        {
            if(corriendo)
                pausar();
        }
        else
        {
            if(se.equals("Reanudar"))
            {
                reanudar();
            }
            else
            {
                if(se.equals(""))
                {
                    corriendo = false;
                    vent.iniciarMenu();
                }
                else
                {
                    if(se.equals("Volver a jugar"))
                    {
                        volverJugar();
                    }
                }
            }
            
        }
    }
    public void pausar(){
        corriendo = false;
        Etiqueta eti = new Etiqueta("PAUSA",new Rectangle(350,20,500,60));
        eti.deriveFont(80f);
        add(eti);
        empezar[0].setVisible(false);
        empezar[1].setVisible(false);
        pausar.setText("Reanudar");
    }
    public void reanudar(){
        iniciar();
        pausar.setText("Pausar");
        if(pulsarTecla)
        {
            empezar[0].setVisible(true);
            empezar[1].setVisible(true);
        }
        
        Component eti = this.findComponentAt(new Point(350,20));
        remove(eti);
    }
    public void perder(){

        corriendo = false;
        perder = true;
        
        Etiqueta eti = new Etiqueta("Has perdido",new Rectangle(340,200,400,60));
        eti.deriveFont(35f);
        add(eti);
        String s = "Conseguiste "+puntos.getText()+" puntos";
        eti = new Etiqueta(s,new Rectangle(250,250,600,60));
        eti.deriveFont(25f);
        add(eti);
        OpcionesMenu op  = new OpcionesMenu("Volver a jugar", this, new Rectangle(300,350,400,80));
        op.setColorRect(new Color(25,25,112));
        op.setColorRectPress(new Color(51,60,135));
        op.setColorRectRoll(new Color(57,57,211));
        op.setFocusable(false);
        add(op);
    }

    public void volverJugar(){
        Component eti = this.findComponentAt(new Point(340,200));
        remove(eti);
        eti = this.findComponentAt(new Point(250,250));
        remove(eti);
        eti = this.findComponentAt(new Point(300,350));
        remove(eti);

        puntos.setText("0000");
        numOleada.setText("0");
        vidas.setText("3");

        bajar = 0;
        disp = 0;
        num_puntos=0;
        max_puntos=0;
        tiempo = 200;
        spawnear=false;
        pulsarTecla=false;

        perder = false;

        enemigos = new ArrayList<>();
        objetos = new ArrayList<ObjetoJuego>();
        balas = new ArrayList<>();
        objetos.add(nave);
        iniciar();
    }
}
