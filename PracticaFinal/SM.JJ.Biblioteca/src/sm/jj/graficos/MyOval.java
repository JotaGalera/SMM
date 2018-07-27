/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.graficos;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Clase destinada a pintar la Elipse a través de 2 puntos.
 * 
 * Hereda de "WithFilled.class" por lo que tiene relleno
 * 
 * @author Jota
 */
public class MyOval extends WithFilled{
    /**
     * MyOval : objeto de tipo Ellipse2D utilizado para pintar la elipse
     * mycLIP : area contenedora de la figura
     */
    private Ellipse2D MyOval;
    private Shape myClip = null;
    
    /**
     * Constructor de la clase "MyOval.class"
     */
    public MyOval(){
        MyOval= new Ellipse2D.Float();
    }
    
    /**
     * Establece los puntos en los que debe estar nuestra elipse.
     * Se utiliza una función propia de Ellipse2D setFrameFromDiagona.
     * 
     * 
     * @param p1 punto inicial(esquina superior izquierda)
     * @param p2 punto final (esquina inferior derecha)
     */
    public void setMyOval(Point2D p1, Point2D p2){
        this.MyOval.setFrameFromDiagonal(p1, p2);
    }
    
    /**
     * Establece el area contenedora de la figura. Para ello devolvemos el area
     * mediange getBounds.
     * 
     */
    public void setMyClip(){    
            this.myClip = this.MyOval.getBounds2D();
            ((Rectangle2D)this.myClip).setFrame(this.MyOval.getX()-3, this.MyOval.getY()-3, this.MyOval.getWidth()+6, this.MyOval.getHeight()+6);
        
    }
    
    /**
     * Devuelve el ovalo propio del objeto de la clase "MyOval.class"
     * 
     * @return MyOval objeto de la clase Ellipse2D
     */
    public Ellipse2D getMyOval(){
        return this.MyOval;
    }
    
    /**
     * Establece la localización nueva de la elipse a través de un punto dado.
     * Para ello se utiliza setFrame y se le da como parámetros el punto nuevo,
     * el ancho y el alto de la elipse a mantener.
     * 
     * @param p nueva localización
     */
    public void setLocationMyOval(Point2D p){
        this.MyOval.setFrame(p.getX(), p.getY(), this.MyOval.getWidth(), this.MyOval.getHeight());
    }
    
    
    /**
     * Pintará el objeto MyOval, llamando previamente al MyPaint de la clase
     * superior "WithFilled.class".
     * Se encarga de pintar el relleno en caso de que tenga, el borde(tanto grosor
     * como tipo), el color tanto del relleno como del borde, y de establecer 
     * un recuadro del objeto que contiene a este en caso de estar seleccionado.
     * 
     * @param g2d gráfico donde se debe pintar nuestro objeto(MyOval)
     */
    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
        
        if(this.isFilled()){                            //Relleno Normal
            g2d.setColor(this.getBackgroundColor());
            g2d.fill(this.MyOval);
        }
        else if(this.isFilledDegradadaVertical()){      //Relleno Vertical
            System.out.println("Entro");
            vertical = new GradientPaint(0, 0 , getColor(), 0, (float) (this.MyOval.getHeight()/2), getBackgroundColor());
            g2d.setPaint(vertical);
            g2d.fill(this.MyOval);
        }
        else if(this.isFilledDegradadaHorizontal()){    //Relleno Horizontal
            horizontal = new GradientPaint(0,0,getColor(), (float) (this.MyOval.getWidth()/2), (float)(this.MyOval.getHeight()/2), getBackgroundColor());
            g2d.setPaint(horizontal);
            g2d.fill(this.MyOval);
        } 
        g2d.setColor(this.getColor());
        g2d.draw(this.MyOval);
        
        if(this.getSelected()){
            g2d.setColor(Color.black);
            g2d.setStroke(this.borderStroke);
            g2d.draw(this.myClip);
        }else if(!this.getSelected() && this.myClip != null){
            this.myClip = null;
        }
        
    }
}
