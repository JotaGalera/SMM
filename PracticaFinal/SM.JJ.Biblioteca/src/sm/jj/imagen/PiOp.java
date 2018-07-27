/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.imagen;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import sm.image.BufferedImageOpAdapter;

/**
 * Este filtro pretende utilizar una matriz, similar al estilo sepia, pero en su lugar
 * hemos jugado con el número Pi. Siendo el elemento central [1][1] el valor real de pi
 * y el resto de elementos solo sus decimales. Conseguimos un efecto "tenebroso".
 * 
 * Hereda de BufferedImageOpAdapter.
 * 
 * @author Jota
 */
public class PiOp extends BufferedImageOpAdapter{
    
    /**
     * Matriz para multiplicar y efectuar el filtro.
     */
     private final float[][] matrix = new float[][]{
        {0.1415926535897932384f,0.1415926535897932384f, 0.1415926535897932384f }, 
        { 0.1415926535897932384f,3.1415926535897932384f ,0.1415926535897932384f, }, 
        { 0.1415926535897932384f, 0.1415926535897932384f, 0.1415926535897932384f}
     };
     
     /**
      * Constructor de la clase
      */
     public PiOp(){};
     
     
     /**
      * Recorre los pixeles de una imagen y cada color de estos los multiplica 
      * por nuestra matriz Pi, conseguiendo un efecto tenebroso en la imagen.
      * 
      * @param src imagen fuente
      * @param dest imagen destino
      * @return imagen modificada
      */
    @Override
    public BufferedImage filter(BufferedImage src, BufferedImage dest) {
        if(src == null)
            throw new NullPointerException("src image is null");
        if(dest == null)
            dest = createCompatibleDestImage(src,null);
        
        
        WritableRaster destRaster = dest.getRaster();
        WritableRaster srcRaster = src.getRaster();
        for(int x = 0; x < srcRaster.getWidth(); x++){
            for(int y = 0; y < srcRaster.getHeight(); y++){
                for(int band = 0; band < srcRaster.getNumBands();band = band + 3){
                    float sampleR = srcRaster.getSample(x, y, band);
                    float sampleG = srcRaster.getSample(x, y, band+1);
                    float sampleB = srcRaster.getSample(x, y, band+2);
                    
                    float outR = (sampleR * this.matrix[0][0] + sampleG*this.matrix[0][1] + sampleB*this.matrix[0][2]);
                    float outG = (sampleR * this.matrix[1][0] + sampleG*this.matrix[1][1] + sampleB*this.matrix[1][2]);
                    float outB = (sampleR * this.matrix[2][0] + sampleG*this.matrix[2][1] + sampleB*this.matrix[2][2]);
                    destRaster.setSample(x, y, band, outR);
                    destRaster.setSample(x, y, band+1, outG);
                    destRaster.setSample(x, y, band+2, outB);
                    
                
                }
            }
        }
        return dest;
    } 
}
