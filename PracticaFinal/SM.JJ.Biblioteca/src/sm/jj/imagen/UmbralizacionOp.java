/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.imagen;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import sm.image.BufferedImageOpAdapter;
/**
 * Clase destinada a poder aplicar un filtro Umbralización sobre una imágen.
 * Se genera una imagen donde cada uno de los pixeles toma un valor 1 o 0 en 
 * función de una propiedad.
 * 
 * Hereda de BufferedImageOpAdapter
 * 
 * @author Jota
 */
public class UmbralizacionOp extends BufferedImageOpAdapter{
    
    /**
     * Umbral -> Valor requisito para realizar cambios a negro o blanco.
     */
    private int umbral;
    
    /**
     * Constructor de la clase UmbralizacionOp
     * 
     * @param umbral valor entero
     */
    public UmbralizacionOp(int umbral){
        this.umbral = umbral;
    }
    
    /**
     * Operación filtro a partir de una imagen source , aplicada a una destino.
     * Recorre los pixeles de la imagen fuente, y para cada "Red","Green","Blue"
     * saca un valor que suma y divide entre tres, si pasa el umbral lo pone en 
     * blanco, sino lo convierte en negro
     * 
     * @param src imagen fuente
     * @param dest imagen destino
     * @return dest imagen destino
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
                    ArrayList<Float> valores = new ArrayList<Float>();
                    
                    
                    float sampleR = srcRaster.getSample(x, y, band);
                    float sampleG = srcRaster.getSample(x, y, band+1);
                    float sampleB = srcRaster.getSample(x, y, band+2);
                    float mediaValueSample = (sampleR + sampleG + sampleB)/3;
                    
                    int valueR = mediaValueSample < this.umbral ? 0 : 255;
                    int valueG = mediaValueSample < this.umbral ? 0 : 255;
                    int valueB = mediaValueSample < this.umbral ? 0 : 255;
                    destRaster.setSample(x, y, band, valueR);
                    destRaster.setSample(x, y, band+1, valueG);
                    destRaster.setSample(x, y, band+2, valueB);
                 
                }
            }
        }
        return dest;
    }
    
}
