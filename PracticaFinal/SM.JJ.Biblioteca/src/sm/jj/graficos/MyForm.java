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
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Clase dedicada a mi Forma, consta de poder pintar cualquier tipo de poligono,
 * ya sea un triangulo, rectangulo, pentagono ... 
 * Son un conjunto de puntos que se unen de forma consecutiva y tenemos la opción
 * de cerrarlo o no.
 * 
 * Hereda de "WithFilled.class" por lo que tiene relleno
 * 
 * @author Jota
 */
public class MyForm extends WithFilled{
    /**
    * Variables: 
    *   MyGP   : objeto GeneralPath al que añadiremos los dos vectores de coordenadas
    *   para pintar nuestra forma.
    *   Xpoint : lista de coordenadas X de los puntos.
    *   Ypoint : lista de coordenadas Y de los puntos.
    *   Cerrado: variable de control para saber si el objeto está cerrado o no.
    *   myClip : utilizado para dibujar el area contenedora de nuestro MyGP.
    */
    private GeneralPath MyGP;
    private ArrayList<Double> Xpoint = new ArrayList();
    private ArrayList<Double> Ypoint = new ArrayList();
    private boolean cerrado = false;
    private Shape myClip = null;
    
    /**
     * Metodo constructor de la clase
     */
    public MyForm(){
        this.MyGP = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    }
    
    /**
     * Devuelve el valor de la variable MyGP de la clase "MyForm".
     * 
     * @return MyGP variable MyGP del objeto de la clase "MyForm"
     */
    public GeneralPath getMyForm(){
        return this.MyGP;
    }
    
    /**
     * Establece el primer punto de inicio de la forma utilizando la función
     * moveTo propia de GeneralPath.
     * 
     * @param x Coordenada X del punto
     * @param y Coordenada Y del punto
     */
    public void setMyFormFirstPoint(double x, double y){
        this.Xpoint.add(x);
        this.Ypoint.add(y);
        this.MyGP.moveTo( this.Xpoint.get(0) , this.Ypoint.get(0) );
    }
    
    /**
     * Establece el resto de puntos de nuestra forma utilizando la función
     * lineTo propia de GeneralPath.
     * 
     * @param x Coordenada X del punto
     * @param y Coordenada Y del punto
     */
    public void setMorePoints(double x, double y){
        this.Xpoint.add(x);
        this.Ypoint.add(y);
        this.MyGP.lineTo(this.Xpoint.get( Xpoint.size()-1 ) ,this.Ypoint.get( Ypoint.size()-1 ) );
    }
    
    /**
     * Cierra nuestra forma(MyGP) uniendo el primer punto de MyGP con el último,
     * a su vez cambia el estado de la variable cerrado a "true" para indicar 
     * que el objeto ha sido cerrado.
     */
    public void closeMyForm(){
        this.cerrado = true;
        this.MyGP.closePath();
    }

    /**
     * Establece el area contenedora de nuestra forma(MyGP) en la 
     * variable "myClip". Para ello devolvemos el area
     * mediange getBounds.
     */
    public void setMyClip(){
        this.myClip = this.MyGP.getBounds2D();
    }
    
    /**
     * Utilizada para desplazar nuestra forma(MyGP) a otro punto.
     * Para ello utilizamos 2 arrays auxiliares (uno para X otro para Y) en los
     * que se introduce el nuevo valor de coordenadas segun el punto + distancia
     * a la que se debe desplazar.
     * Distancia calculada mediante la diferencia entre el punto al que queremos
     * llevarla menos el punto inicial.
     * 
     * 
     * @param p Punto al que se debe desplazar nuestra forma
     */
    public void setLocationMyForm(Point2D p){
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
                
                setMyFormFirstPoint(Xpoint2.get(i), Ypoint2.get(i) );
                firstPoint = false;
            }
            else{
                
                setMorePoints(Xpoint2.get(i), Ypoint2.get(i) );  
            } 
            
        }
        
        if(this.cerrado == true)
            closeMyForm();
        
    }
    
    /**
     * Pintará el objeto MyGP, llamando previamente al MyPaint de la clase
     * superior "WithFilled.class".
     * Se encarga de pintar el relleno en caso de que tenga, el borde(tanto grosor
     * como tipo), el color tanto del relleno como del borde, y de establecer 
     * un recuadro del objeto que contiene a este en caso de estar seleccionado.
     * 
     * @param g2d gráfico donde se debe pintar nuestro objeto(MyGP)
     */
    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
        
        if(this.isFilled()){
            g2d.setColor(this.getBackgroundColor());
            g2d.fill(this.MyGP);
        }
        else if(this.isFilledDegradadaVertical()){
            System.out.println("Entro");
            vertical = new GradientPaint(0, 0 , getColor(), 0, (float) (this.MyGP.getBounds2D().getHeight()/2), getBackgroundColor());
            g2d.setPaint(vertical);
            g2d.fill(this.MyGP);
        }
        else if(this.isFilledDegradadaHorizontal()){
            horizontal = new GradientPaint(0,0,getColor(), (float) (this.MyGP.getBounds2D().getWidth()/2), (float)(this.MyGP.getBounds2D().getHeight()/2), getBackgroundColor());
            g2d.setPaint(horizontal);
            g2d.fill(this.MyGP);
        } 
        g2d.setColor(this.getColor());
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