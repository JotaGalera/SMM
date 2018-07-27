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

/**
 * Clase intermediaría para controlar las formas que tiene relleno
 * 
 * Hereda de la clase "MyShape", clase principal.
 * 
 * @author Jota
 */
public class WithFilled extends MyShape {
    /**
     * Filled : variable utilizada para controlar si una figura esta rellena o 
     * no.
     * FilledDegradadaVertical : variable utilizada para controlar si una figura estara 
     * rellena con degradado o no de forma vertical.
     * FilledDegradadaVertical : variable utilizada para controlar si una figura estara 
     * rellena con degradado o no de forma horizontal.
     * Vertical : objeto GradientPaint , necesario para realizar degradado
     * Horizontal : objeto GradientPaint , necesario para realizar degradado
     */
    boolean filled = false;
    boolean filledDegradadaVertical = false;
    boolean filledDegradadaHorizontal = false;
    protected GradientPaint vertical;
    protected GradientPaint horizontal;
    
    /**
     * Devuelve si la figura esta rellena o no.
     * 
     * @return filled manifiesta "true" (relleno) "false" (no relleno) 
     */
    public boolean isFilled() {
        return this.filled;
    }
    
    /**
     * Devuelve si la figura esta o no rellena con degradado.
     * 
     * @return filledDegradada "true" (relleno) "false" (no relleno)
     */
    public boolean isFilledDegradadaVertical(){
        return this.filledDegradadaVertical;
    }
    
    /**
     * Devuelve si la figura esta o no rellena con degradado.
     * 
     * @return filledDegradada "true" (relleno) "false" (no relleno)
     */
    public boolean isFilledDegradadaHorizontal(){
        return this.filledDegradadaHorizontal;
    }
    
    /**
     * Inserta el valor "true" o "false" , si la figura está rellena o no.
     * 
     * @param filled establece el valor(true o false) de relleno de la figura
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
        if(filled){
            this.filledDegradadaHorizontal=false;
            this.filledDegradadaVertical=false;
        }
    }
    
    /**
     * Inserta el valor "true" o "false" , si la figura esta rellena o no con
     * degradado.
     * 
     * @param filledD establece el valor(true o false) de relleno de la figura
     */
    public void setFilledDegradadaVertical(boolean filledD){
        this.filledDegradadaVertical = filledD;
        if(filledD){
            this.filledDegradadaHorizontal=false;
            this.filled=false;
        }
    }
    
    /**
     * Inserta el valor "true" o "false" , si la figura esta rellena o no con
     * degradado.
     * 
     * @param filledD establece el valor(true o false) de relleno de la figura
     */
    public void setFilledDegradadaHorizontal(boolean filledD){
        this.filledDegradadaHorizontal = filledD;
        if(filledD){
            this.filled=false;
            this.filledDegradadaVertical=false;
        }
    }
    
    /**
     * Llama al metodo paint de "MyShape.class"
     * 
     * @param g2d gráfico donde debe pintarse la figura.
     */
    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
        
    }
    
}
