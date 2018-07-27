/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.graficos;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.Shape;
/**
 * Clase de la cual cuelgan todas las formas. Contará con los atributos
 * que serán comunes a todas las formas.
 * 
 * @author Jota
 */
public class MyShape {
    /**
     *  Stroke : Grosor del objeto.
     *  Color : Color del borde del objeto.
     *  SecondColor : Color del relleno del objeto.
     *  Composition : Transparencia del objeto.
     *  Render : Alisado del objeto.
     *  MyClip : Area del objeto.
     *  BorderStroke : Tipo de trazado del borde del objeto.
     *  Selected : Controla si el objeto ha sido seleccionado o no en el modo
     *  editar.
     */
    private Stroke stroke = new BasicStroke();          //Trazo
    private Color color,secondColor;            //Color
    private Composite composition = AlphaComposite.getInstance(3);;  //Transparencia
    private RenderingHints render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);;  //Alisado
    private Shape myClip = null;
    protected Stroke borderStroke = new  BasicStroke(
                1,                         // grosor: 1 píxels
                BasicStroke.CAP_BUTT,      // terminación: recta
                BasicStroke.JOIN_ROUND,    // unión: redondeada 
                1f,                        // ángulo: 1 grado
                new float[] {5, 5, 5, 5},  // línea de 10, 5 blancos, línea de 5, 5 blancos
                2                          // fase
            );
    protected boolean selected = false;
    
    /**
     * Devuelve el valor del trazo de la forma.
     * 
     * @return Stroke estrado del trazo de la forma
     */
    public Stroke getStroke(){ return this.stroke; };
    
    
    /**
     * Función para cambiar el tamaño del trazo de la forma.
     * 
     * @param s define el tamaño del trazo
     */
    public void setStroke(Stroke s){
        this.stroke = s;
    }
    
    
    /**
     * Devuelve el valor de la variable color de la forma.
     * 
     * @return Color color de la forma
     */
    public Color getColor(){ return this.color; };
    
    /**
     * Función para cambiar el color de la forma.
     * 
     * @param color establece el color
     */
    public void setColor(Color color){
        this.color = color;
    }
    
    /**
     * Devuelve el valor del color del relleno de la forma. 
     * 
     * @return secondColor color relleno
     */
    public Color getBackgroundColor(){ return this.secondColor; };
    
    /**
     * Función para cambiar el color del relleno de la forma
     * 
     * @param color color de relleno
     */
    public void setBackgroundColor(Color color){
        this.secondColor = color;
    }
    
    /**
     * Devuelve el valor de transparencia de la forma.
     * 
     * @return Composite valor transparencia
     */
    public Composite getComposite(){ return this.composition; };
    
    /**
     * Función para cambiar la transparencia de la forma.
     * 
     * @param clarity modifica el grado de transparencia
     */
    public void setComposite(float clarity){
        this.composition = AlphaComposite.getInstance(3, clarity);
    }
    
    /**
     * Devuelve el valor del alisado de la forma.
     * 
     * @return render valor alisado
     */
    public RenderingHints getRender(){ return this.render; };
    
    /**
     * Función para modificar el alisado de una forma, según el valor de la 
     * variable "alisar".
     * 
     * @param alisar true:se alisa false:no se alisa
     */
    public void setRender(boolean alisar){
        if(alisar){
            this.render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        else {
            this.render = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
    }
    
    /**
     * Establece el valor de la variable selected en función de si la forma esta
     * seleccionada en el modo editar.
     * 
     * @param s "true" : seleccionado "false": no seleccionado
     */
    public void setSelected(boolean s){
        this.selected=s;
    }
   
    /**
     * Indica si la figura esta o no seleccionada
     * 
     * @return selected "true" : seleccionado "false": no seleccionado
     */
    public boolean getSelected(){
        return this.selected;
    }
    
    /**
     * Establecerá el valor de los atributos del objeto en el grafico g2d, grosor,
     * color, transparencia y alisado.
     * 
     * @param g2d grafico donde se pintará la forma.
     */
    public void MyPaint(Graphics2D g2d){
        g2d.setStroke(this.stroke);
        g2d.setComposite(this.composition);
        g2d.setColor(this.color);
        g2d.setRenderingHints(this.render);
    }    
    
}
