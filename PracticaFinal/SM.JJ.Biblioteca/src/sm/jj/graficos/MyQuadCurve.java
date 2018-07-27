/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;


/**
 *Clase dedicada a la curva con un punto de control, a partir de una linea y
 * un punto.
 * 
 * Hereda de "WithOutFilled.class" es decir, no tiene relleno.
 * 
 * @author Jota
 */
public class MyQuadCurve extends WithoutFilled{
    /**
     * QCLine : Linea que utilizamos para después curvarla
     * MyQC : Almacena la linea y la curva a través del punto de control
     * mp1 : Punto de inicio de la linea
     * mp2 : Punto final de la linea
     * mpc : Punto de control de la curva
     * arco : Controla si es posible realizar un arco
     * myClip : Area contenedora de la figura
     * 
     */
    private MyLine QCLine = new MyLine();
    private QuadCurve2D MyQC;
    private Point2D mp1 = new Point2D.Double(0,0);
    private Point2D mp2 = new Point2D.Double(0,0);
    private Point2D mpc = null;
    private boolean arco = false;
    private Shape myClip = null;
    
    /**
     * Constructor de la clase "MyQuadCurve.class".
     */
    public MyQuadCurve(){
        this.MyQC = new QuadCurve2D.Double();
    }
    
    /**
     * Devuelve el punto inicial de la linea.
     * 
     * @return mp1 punto inicial
     */
    public Point2D getP1(){
        return this.mp1;
    }
    
    /**
     * Devuelve el punto final de la linea. 
     * 
     * @return mp2 punto final
     */
    public Point2D getP2(){
        return this.mp2;
    }
    
    /**
     * Devuelve el punto de control de la curva.
     * 
     * @return mpc punto control
     */
     public Point2D getPC(){
        return this.mpc;
    }
    
    /**
     * Devuelve la curva del objeto de tipo MyQuadCurve.
     * 
     * @return MyQC 
     */
    public QuadCurve2D getMyQC(){
        return this.MyQC;
    }
     
    /**
     * Devuelve la linea del objeto de tipo MyQuadCurve.
     * 
     * @return QCLine
     */
    public MyLine getLineQC(){
        return this.QCLine;
    }
    
    /**
     * Indica si se ha tomado el punto para el arco.
     * 
     * @return arco "true": se ha tomado "false": no se ha tomado
     */
    public boolean getIsPossibleArco(){
        return this.arco;
    }
    
    /**
     * Almacenamos el punto de inicio y fin de la linea QCLine, utilizada de 
     * forma símbolica, tal que a partir de ella se convertira en una curva.
     */
    public void setMQLine(){
        this.mp1 = this.QCLine.getPIni();
        this.mp2 = this.QCLine.getPFin();
    }
    
    /**
     * Establece el punto de curvatura de una linea.
     * 
     * @param p punto donde deve ir la curva
     */
    public void setArcMQ(Point2D p){
        System.out.println("Entro");
        this.mpc = p;
        this.arco = true;
        setMQLine();
        setMyQC();
        
    }
    
    /**
     * Establece la curva en nuestro objeto MyQC.
     * 
     */
    public void setMyQC(){
        this.MyQC.setCurve(this.mp1,this.mpc,this.mp2);
    }
    
    /**
     * Establece la nueva localización del punto de curvatura de nuestro objeto
     * MyQC.
     * 
     * @param p punto nuevo 
     */
    public void setLocationMyQC(Point2D p){
        this.mpc.setLocation(   p.getX()  ,  p.getY()  ); 
        setMyQC();
    }
    
    /**
     * Establece el area contenedora de la curva
     */
    public void setMyClip(){
        
            this.myClip = this.MyQC.getBounds2D();
            ((Rectangle2D)this.myClip).setFrame( this.MyQC.getX1()-3, this.MyQC.getY1()-3,( this.MyQC.getX2()-this.MyQC.getX1() )+6, ( this.MyQC.getY2()-this.MyQC.getY1() )+6 );
        
    }
    
    /**
     * Pintará el objeto QCLine como prototipo de linea y MyQC como curva.
     * Se encarga de pintar el cambio de color, cambio de trazo y el area 
     * contenedora de la figura en caso de estar seleccionada.
     * 
     * @param g2d gráfico donde se debe pintar nuestro objeto(QCLine y MyQC)
     */
    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
        
        if(arco)
            g2d.draw(this.MyQC);
        else if(!arco)
            g2d.draw(this.QCLine.getLine());
        
        if(this.getSelected()){
            g2d.setColor(Color.black);
            g2d.setStroke(this.borderStroke);
            g2d.draw(this.myClip);
        }else if(!this.getSelected() && this.myClip != null){
            this.myClip = null;
        }
    }
    
    
}
