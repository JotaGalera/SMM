package sm.jj.graficos;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Clase dedicada al rectangulo.Establece la forma de pintarse a traves de dos
 * puntos.
 * 
 * Hereda de "WithFilled.class" tiene relleno.
 * 
 * @author Jota
 */
public class MyRectangle extends WithFilled{
    
    /**
     * rectangulo2D : variable Rectangle2D utilizado para pintar rectangulos.
     * myClip : area contendedora de nuestra forma.
     */
    private Rectangle2D rectangulo2D;
    private Shape myClip = null;
    
    /**
     * Costructor de la clase "MyRectangle.class"
     */
    public MyRectangle(){
        rectangulo2D = new Rectangle2D.Float();
    }
    
    /**
     * Establece a través de dos puntos, pt1 y pt2, las dimensiones del 
     * rectangulo(a través de setFrameFromDiagonal funcion de Rectangle2D)
     * 
     * @param pt1 esquina superior izquierda
     * @param pt2 esquina inferior derecha
     */
    public void setMyRectangleFirstPoin(Point2D pt1, Point2D pt2){
        rectangulo2D.setFrameFromDiagonal(pt1, pt2);
    }
    
    /**
     * Devuelve el rectangulo propio del objeto de la clase "MyRectangle"
     * 
     * @return rectangulo2D rectangulo de tipo Rectangle2D
     */
    public Rectangle2D getMyRectangle(){
        return this.rectangulo2D;
    }
    
    /**
     * Establece la posición del rectangulo a través de un punto.
     * Para ello se utiliza una función propia de Rectangle2D setFrame.
     * Le indicamos el punto donde queremos transportar el rectangulo y que 
     * mantenga el ancho y el alto que teníamos.
     * 
     * @param p nueva localización
     */
    public void setLocationMyRectangle(Point2D p){
        rectangulo2D.setFrame(p.getX(), p.getY() , this.rectangulo2D.getWidth() , this.rectangulo2D.getHeight());  
    }
    
    /**
     * Establece el area contenedora de nuestra forma.Para ello devolvemos el area
     * mediange getBounds.
     * 
     */
    public void setMyClip(){
        this.myClip = this.rectangulo2D.getBounds2D();
        ((Rectangle2D)this.myClip).setFrame(this.rectangulo2D.getX()-3, this.rectangulo2D.getY()-3, this.rectangulo2D.getWidth()+6, this.rectangulo2D.getHeight()+6);
    }
    
    /**
     * devuelve el area contenedora de la forma
     * 
     * @return myClip area contenedora
     */
    public Shape getMyClip(){
        return this.myClip;
    }
    
    /**
     * Pintará el objeto rectangulo2D, llamando previamente al MyPaint de la clase
     * superior "WithFilled.class".
     * Se encarga de pintar el relleno en caso de que tenga, el borde(tanto grosor
     * como tipo), el color tanto del relleno como del borde, y de establecer 
     * un recuadro del objeto que contiene a este en caso de estar seleccionado.
     * 
     * @param g2d gráfico donde se debe pintar nuestro objeto(rectangulo2D)
     */
    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
        
        
        
        if(this.isFilled()){
            g2d.setColor(this.getBackgroundColor());
            g2d.fill(this.rectangulo2D);
        }
        else if(this.isFilledDegradadaVertical()){
            System.out.println("Entro");
            vertical = new GradientPaint(0, 0 , getColor(), 0, (float)rectangulo2D.getHeight()/2, getBackgroundColor());
            g2d.setPaint(vertical);
            g2d.fill(this.rectangulo2D);
        }
        else if(this.isFilledDegradadaHorizontal()){
            horizontal = new GradientPaint(0,0,getColor(), (float)rectangulo2D.getWidth()/2, (float)rectangulo2D.getHeight()/2, getBackgroundColor());
            g2d.setPaint(horizontal);
            g2d.fill(this.rectangulo2D);
        } 
               
        g2d.setColor(this.getColor());
        g2d.draw(this.rectangulo2D);
        
        
        System.out.println(this.isFilled()+" " +this.isFilledDegradadaVertical()+"" + this.isFilledDegradadaHorizontal());
        if(this.getSelected()){
            g2d.setColor(Color.black);
            g2d.setStroke(this.borderStroke);
            g2d.draw(this.myClip);
        }else if(!this.getSelected() && this.myClip != null){
            this.myClip = null;
        }
        
    }
    
}
