/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.imagen;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * Dedicada a efectuar un efecto "Ghost"(fantasma) sobre una imagen, llevandose
 * a un tono muy claro, similar a una foto fantasmal.
 * 
 * @author Jota
 */
public class MyFunction extends RescaleOp{
    
    /**
     * ImageOut -> imagen de salida
     */
    private BufferedImage imgOut;

    /**
     * Llama al constructor de rescaleOp
     * 
     * @param f factor de escala
     * @param f1 offset
     * @param rh espeficifar renderingHints  o null
     */
    public MyFunction(float f, float f1, RenderingHints rh) {
        super(f, f1, rh);
    }
    
    /**
     * Función para convertir una imagen a un tipo que deseemos.
     * 
     * @param img
     * @param type
     * @return 
     */
    BufferedImage convertImageType(BufferedImage img, int type){
        if(img == null) return null;
        BufferedImage imgOut = new BufferedImage(img.getWidth(), img.getHeight(),type);
    
        Graphics2D g2d = imgOut.createGraphics();
        g2d.drawImage(img, 0, 0,null);
        return imgOut;
    }
    
    /**
     * Aplica un filtro sobre una imagen y la retorna la imagen modificada. Si 
     * la imagen no es compatible la convierte a una del tipo RGB.
     * 
     * @param imgS imagen sobre la que actua
     * @return imagen modificada
     */
    public BufferedImage Actua(BufferedImage imgS){
        RescaleOp rop = new RescaleOp(-0.1f, 255.0f, null);
        BufferedImage imgD;
        try{
            return imgD = rop.filter(imgS, null);
        }
        catch(Exception ex){
            imgS = convertImageType(imgS, BufferedImage.TYPE_INT_RGB);
            return imgD = rop.filter(imgS,null);
        }
    }
    
    
}
