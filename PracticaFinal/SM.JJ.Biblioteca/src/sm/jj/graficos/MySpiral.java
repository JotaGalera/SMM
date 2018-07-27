/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.graficos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * MySpiral es una forma propia con la que podemos pintar una especie de espiral.
 * Para ver el funcionamiento real de la figura es necesario seleccionar el tipo
 * de trazo "discontinuo2". Aunque no se ha obligado a serlo para poder experimentar
 * con distintos tipos de trazo en un futuro.
 * 
 * Hereda de la clase "WithoutFilled.class" por tanto no tiene relleno.
 * 
 * @author Jota
 */
public class MySpiral extends WithoutFilled{
    /**
     * MyGP : contenedor de nuestra forma.
     * Xpoint : lista de coordenadas X de puntos.
     * Ypoint : lista de coordenadas Y de puntos.
     * myClip : area contenedora de la forma
     * contenida : variable que indica si la figura se encuentra en cierto punto
     */
    private GeneralPath MyGP;
    private ArrayList<Double> Xpoint = new ArrayList();
    private ArrayList<Double> Ypoint = new ArrayList();
    private Shape myClip = null; 
    boolean contenida = false;
    
    /**
     * Constructor de la clase
     */
    public MySpiral(){
        this.MyGP = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    }
    
    /**
     * Establece el centro de nuestra forma
     * 
     * @param x coordenada x 
     * @param y coordenada y
     */
    public void setFirstPointGP(double x,double y){
        this.Xpoint.add(x);
        this.Ypoint.add(y);
        this.MyGP.moveTo(x, y);
    }
    
    /**
     * Establece el resto de puntos( circunferencia alrededor del centro ) de la forma
     * 
     * @param x coordenada x
     * @param y coordenada y
     */
    public void setGP(double x,double y){
        this.Xpoint.add(x);
        this.Ypoint.add(y);
        this.MyGP.lineTo(x, y);

        
    }
    
    /**
     * Devuelve el valor de la variable MyGP de la clase "MySpiral".
     * 
     * @return MyGP variable MyGP de la clase "MySpiral"
     */
    public GeneralPath getMyGP(){
        return this.MyGP;
    }
    
    /**
     * Establece el area contenedora de nuestra espiral (MyGP) en la 
     * variable "myClip". Para ello devolvemos el area
     * mediange getBounds.
     */
    public void setMyClip(){
        this.myClip = this.MyGP.getBounds2D();
    }
    
    /**
     * Indica si el centro de la figura se encuentra, sobre un radio de 10 a 
     * partir del punto central, en el punto que seleccionamos
     * 
     * @param p punto a comprobar si está la espiral
     * @return contenida "true": lo esta "false": no lo esta
     */
    public boolean myContainsLine(Point2D p){
        
        Point2D pAux = new Point2D.Double(Xpoint.get(0), Ypoint.get(0));
        
        if( pAux.distance(p) <= 10.0D){
            contenida = true;
        }else 
            contenida = false;
        
        return contenida;
    }
    
    /**
     * Utilizada para desplazar nuestra espiral(MyGP) a otro punto.
     * Para ello utilizamos 2 arrays auxiliares (uno para X otro para Y) en los
     * que se introduce el nuevo valor de coordenadas segun el punto + distancia
     * a la que se debe desplazar.
     * Distancia calculada mediante la diferencia entre el punto al que queremos
     * llevarla menos el punto inicial.
     * 
     * @param p Punto al que se debe desplazar nuestra espiral
     */
    public void setLocationSpiral(Point2D p){
        ArrayList<Double> Xpoint2 = new ArrayList();
        ArrayList<Double> Ypoint2 = new ArrayList();
        boolean firstPoint = true;
        double distanciaX = 0;
        double distanciaY = 0;
        
        distanciaX = p.getX() - Xpoint.get(0);
        distanciaY = p.getY() - Ypoint.get(0);
        for(int i=0; i<Xpoint.size() ; i++){
            Xpoint2.add( Xpoint.get(i)+distanciaX );
        }
        for(int i=0; i<Ypoint.size() ; i++){
            Ypoint2.add( Ypoint.get(i)+distanciaY );
        }
        
        this.Xpoint.clear();
        this.Ypoint.clear();
        this.MyGP.reset();
        for(int i=0 ; i < Xpoint2.size() ; i++){
            
            if(firstPoint){
                setFirstPointGP( Xpoint2.get(i), Ypoint2.get(i) );
                firstPoint = false;
            }
            else{
                setGP( Xpoint2.get(i), Ypoint2.get(i) );  
            } 
            
        }
        
    }
    
    /**
     * Pintará el objeto MyGP.
     * Se encarga de pintar el cambio de color, cambio de trazo y el area 
     * contenedora de la figura en caso de estar seleccionada.
     * 
     * @param g2d gráfico donde se debe pintar nuestro objeto(MyGP)
     */
    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
        g2d.draw(this.MyGP); 
        if(this.getSelected()){
            g2d.setColor(Color.black);
            g2d.setStroke(this.borderStroke);
            g2d.draw(this.myClip);
        }else if(!this.getSelected() && this.myClip != null){
            this.myClip = null;
        } 
    }
    
}
