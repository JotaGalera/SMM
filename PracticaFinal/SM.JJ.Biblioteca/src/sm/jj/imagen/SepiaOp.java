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
 * Clase dedicada a realizar un filtro sepia sobre una imagen
 * 
 * Hereda de BufferedImageOpAdapter.
 * 
 * @author Jota
 */
public class SepiaOp extends BufferedImageOpAdapter{
    /**
     * Matrix matriz de multiplicacion para aplicarla a la imagen.
     * 
     */
    
    private final float[][] matrix = new float[][]{
        {0.393f, 0.769f, 0.189f}, 
        {0.349f, 0.686f, 0.168f}, 
        {0.272f, 0.534f, 0.131f}
    };
    
    /**
     * Constructor de la clase SepiaOp
     */
    public SepiaOp(){}
    
    /**
     * Realiza un recorrido sobre los pixeles de una imagen y aplica el efecto 
     * sepia a cada uno de ellos. Si el valor sobrepasa el 255.0 se establecerá
     * como valor por defecto 255.0 pues no existe un valor superior 
     * representativo.
     * 
     * @param src imagen fuente
     * @param dest imagen destino
     * @return imagen modificada
     */
    public BufferedImage filter(BufferedImage src, BufferedImage dest){
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
                    
                    float outR = sampleR * this.matrix[0][0] + sampleG*this.matrix[0][1] + sampleB*this.matrix[0][2];
                    float outG = sampleR * this.matrix[1][0] + sampleG*this.matrix[1][1] + sampleB*this.matrix[1][2];
                    float outB = sampleR * this.matrix[2][0] + sampleG*this.matrix[2][1] + sampleB*this.matrix[2][2];
                    if(outR > 255.0){
                        int outRentero = 255;
                        destRaster.setSample(x, y, band, outRentero);
                    }else destRaster.setSample(x, y, band, outR);
                    if(outG > 255.0){
                        int outGentero = 255;
                        destRaster.setSample(x, y, band+1, outGentero);
                    }else destRaster.setSample(x, y, band+1, outG);
                    if(outB > 255.0){
                        int outBentero = 255;
                        destRaster.setSample(x, y, band+2, outBentero);
                    }else destRaster.setSample(x, y, band+2, outB);
                
                }
            }
        }
        return dest;
    }
}
