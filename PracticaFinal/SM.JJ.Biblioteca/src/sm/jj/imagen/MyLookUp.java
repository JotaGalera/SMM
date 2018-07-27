/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.imagen;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupTable;

/**
 *
 * @author Jota
 */
public class MyLookUp {
    
    public static final int TYPE_1 = 1;
    
    
    public static LookupTable myCreateLookUptable(int type){
    
        if(type == 1){
            return MyGoldFunctionLookUp();
        }
        else {
            return null;
        }
          
    }
    
    /**
     * Función MyGoldFunctionLookUp. Consiste en multiplicar los colores de la 
     * imagen por el número conocido como numero dorado. Recorre la imagen
     * 
     * @return Tl 
     */
    public static LookupTable MyGoldFunctionLookUp(){
        
        float gold = (float) 1.61803398874988 ;
        byte lt[] = new byte[256];
        float aux;
            
        for (int i = 0 ; i < 256 ; i++ ){
            aux = i * gold;    
            lt[i] = (byte)aux;
        }
         ByteLookupTable It = new ByteLookupTable(0, lt);
         
         return It;
    }
    
    
}
