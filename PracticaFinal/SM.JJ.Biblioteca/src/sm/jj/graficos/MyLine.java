/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javafx.geometry.BoundingBox;

/**
 * Clase dedicada al punto y a la linea. En ella se establece la forma de 
 * pintarlos. Linea con dos puntos. Punto con dos puntos iguales el uno al otro.
 * 
 * Hereda de "WithOutFilled.class" por lo que no tiene relleno.
 * 
 * @author Jota
 */
public class MyLine extends WithoutFilled{
    
    /**
     * pIni : punto de inicio de la linea o punto
     * pFin : punto de fin de la lines o punto, en caso del punto pIni == pFin
     * myLine : objeto Line2D utilizado para pintar puntos o lineas mediante puntos
     * Contenida : variable controla si el la linea o punto esta en el punto 
     * seleccionado
     * myClip : Area contenedora de la figura.
     */
    private Point2D pIni,pFin; //variables para MQC
    private Line2D myLine;
    private boolean contenida = false;
    private Shape myClip = null;
    
    /**
     * Constructor de la clase "MyLine"
     */
    public MyLine(){
        this.myLine = new Line2D.Double();
    }
    
    /**
     * Establece el inicio y fin de la linea o punto. 
     * En caso de punto pIni == pFin.
     * 
     * @param p1 punto de inicio
     * @param p2 punto de fin
     */
    public void setMyLine(Point2D p1, Point2D p2){
        this.pIni = p1; //variables utilizadas para MQC
        this.pFin = p2; //variables utilizadas para MQC
        this.myLine.setLine(p1, p2);
    }
    
    /**
     * Indica si la linea o punto se encuentran cerca del punto que le pasamos.
     * Se da un margen de una distancia de 4, para la precisión a la hora de 
     * seleccionar el punto o la linea, puesto que se dificultaría la seleeccion
     * de estos de no ser así.
     * 
     * @param p en el que comprobamos si esta la figura
     * @return contenida true:contenida false:no contenida
     */
    public boolean myContainsLine(Point2D p){
        
        if( this.pIni == this.pFin ){
            if( this.myLine.getP1().distance(p) <= 4.0D)
                contenida = true;
        }
        else if(this.myLine.ptLineDist(p) <= 4.0D){
            contenida = true;
        }else 
            contenida = false;
        
        return contenida;
    }
    
    /**
     * Establece el area contenedora de nuestro punto o linea.Para ello devolvemos el area
     * mediange getBounds.
     */
    public void setMyClip(){
        this.myClip = this.myLine.getBounds2D();
        ((Rectangle2D)this.myClip).setFrame( this.myLine.getX1()-3, this.myLine.getY1()-3,( this.myLine.getX2()-this.myLine.getX1() )+6, ( this.myLine.getY2()-this.myLine.getY1() )+6 ); 
    }
    /**
     * Devuelve el area contenedora de nuestro punto o linea.
     * 
     * @return myClip area contenedora de nuestra figura 
     */
    public Shape getMyClip(){
        return this.myClip;
    }
    
    /**
     * Devuelve el punto inicial de la linea o el punto.
     * 
     * @return pIni punto incial
     */
    public Point2D getPIni(){
        return this.pIni;
    }
    
    /**
     * Devuelve el punto final de la linea o el punto.
     * 
     * @return pFin punto final
     */
    public Point2D getPFin(){
        return this.pFin;
    }
    
    /**
     * Devuelve nuestro punto o linea
     * 
     * @return myLine punto o linea 
     */
    public Line2D getLine(){
        return this.myLine;
    }
    
    /**
     * Modifica la localización de la linea o el punto. Para ello calculamos la 
     * distancia a la que se encuentra el punto inicial del punto al que queremos
     * desplazar la figura. 
     * La nueva localización será pInicial = punto seleccionado y 
     * pFin = pFin+distancia
     * 
     * 
     * @param p punto al que queremos desplazar la figura
     */
    public void setLocationMyLine(Point2D p){
        double auxX,auxY;
        auxX = p.getX() - this.myLine.getX1();
        auxY = p.getY() - this.myLine.getY1();
        Point2D newLocation = new Point2D.Double(auxX + myLine.getX2() , auxY + myLine.getY2());
        this.myLine.setLine(p,newLocation);
    }
    
    /**
     * Pintará el objeto myLine.
     * Se encarga de pintar el cambio de color, cambio de trazo y el area 
     * contenedora de la figura en caso de estar seleccionada.
     * 
     * @param g2d gráfico donde se debe pintar nuestro objeto(myLine)
     */

    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
        g2d.draw(this.myLine); 
        if(this.getSelected()){
            g2d.setColor(Color.black);
            g2d.setStroke(this.borderStroke);
            g2d.draw(this.myClip);
        }else if(!this.getSelected() && this.myClip != null){
            this.myClip = null;
        }
            
    }
    
}
