/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.iu;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import sm.jj.graficos.MyRectangle;

/**
 * Clase para el lienzo de Imagenes
 * 
 * @author Jota
 */
public class Lienzo2DImagen extends Lienzo2D {
    /**
     * img : imagen del lienzo
     * rect : rectangulo tipo Rectangle2D para el borde del lienzo
     * s : tipo del borde del lienzo
     * 
     */
    BufferedImage img = null;
    private Rectangle2D rect = new Rectangle2D.Double();
    private Shape clip = getClip();
    private Stroke s = new  BasicStroke(
                1,                       // grosor: 1 píxels
                BasicStroke.CAP_BUTT,      // terminación: recta
                BasicStroke.JOIN_ROUND,    // unión: redondeada 
                1f,                        // ángulo: 1 grado
                new float[] {5, 5, 5, 5}, // línea de 5, 5 blancos, línea de 5, 5 blancos
                2                          // fase
            );
    
    /**
     * Pinta una imagen en el lienzo.
     * 
     * @param g grafico donde se pinta la imagen
     */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(img != null){
            g.drawImage(img,0,0,this);
            rect.setFrame(0, 0, this.img.getWidth(), this.img.getHeight());
            ((Graphics2D)g).setStroke(s);
            ((Graphics2D)g).draw(rect);
            
        }   
    }
    
    /**
     * Establece la imagen del lienzo
     * 
     * @param img imagen a insertar
     */
    public void setImage(BufferedImage img){
        this.img = img;
        if(img!=null) {     
            setPreferredSize(new Dimension(img.getWidth(),img.getHeight()));
        }
    }
    
    /**
     * Devuelve la imagen del lienzo
     * 
     * @param drawVector define "true" : dibujar vector de formas "false" : no
     * @return img imagen 
     */
    public BufferedImage getImage(boolean drawVector){
        if(drawVector){
            BufferedImage image = new BufferedImage(this.img.getWidth(),this.img.getHeight(),this.img.getType());
            Shape auxShape = this.clip;
            this.clip = null;
            
            this.paint(image.createGraphics());
            
            this.clip = auxShape;
            
            return image;
        }
        return img;
    }
    
    /**
     * Devuelve la imagen del lienzo
     * 
     * @return img imagen 
     */
    public BufferedImage getImage(){
        return img;
    }
}
