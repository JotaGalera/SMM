package sm.jj.graficos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 * Clase utilizada para poder realizar "Trazo libre" mediante una consecuencia 
 * de puntos, almacenados en dos arrays por coordenada "X" e "Y".
 * 
 * Hereda de la clase "WithoutFilled" es decir, que tiene relleno
 * 
 * @author Jota
 */
public class FreeDraw extends WithoutFilled{
    /**
    * Variables: 
    *   MyGP   : objeto GeneralPath al que añadiremos los dos vectores de coordenadas
    *   para pintar el trazo libre.
    *   Xpoint : lista de coordenadas X de los puntos.
    *   Ypoint : lista de coordenadas Y de los puntos.
    *   myClip : utilizado para dibujar el area contenedora de nuestro MyGP.
    */
    private GeneralPath MyGP;
    private ArrayList<Double> Xpoint = new ArrayList();
    private ArrayList<Double> Ypoint = new ArrayList();
    private Shape myClip = null;
    
    /**
     * Metodo constructor de la clase
     */
    public FreeDraw(){
        this.MyGP = new GeneralPath(GeneralPath.WIND_NON_ZERO);
    }
    
    /**
     * Devuelve el valor de la variable MyGP de la clase "FreeDraw".
     * 
     * @return MyGP variable MyGP de la clase "FreeDraw"
     */
    public GeneralPath getMyGP(){
        return this.MyGP;
    }
    
    /**
     * Establece el primer punto de inicio del trazo libre utilizando la función
     * moveTo propia de GeneralPath.
     * 
     * @param x Coordenada X del punto
     * @param y Coordenada Y del punto
     */
    public void setFirstPointGP(double x,double y){
        
        this.Xpoint.add(x);
        this.Ypoint.add(y);
        this.MyGP.moveTo( this.Xpoint.get(0) , this.Ypoint.get(0) );
    }
    
    /**
     * Establece el resto de puntos de nuestro trazo libre utilizando la función
     * lineTo propia de GeneralPath.
     * 
     * @param x Coordenada X del punto
     * @param y Coordenada Y del punto
     */
    public void setGP(double x,double y){
        this.Xpoint.add(x);
        this.Ypoint.add(y);
        this.MyGP.lineTo(this.Xpoint.get( Xpoint.size()-1 ) ,this.Ypoint.get( Ypoint.size()-1 ) );
        
        
    }
    
    
    
    /**
     * Establece el area contenedora de nuestro trazado libre(MyGP) en la 
     * variable "myClip". Para ello devolvemos el area
     * mediange getBounds.
     */
    public void setMyClip(){
        this.myClip = this.MyGP.getBounds2D();
    }
    
    /**
     * Utilizada para desplazar nuestro trazo libre(MyGP) a otro punto.
     * Para ello utilizamos 2 arrays auxiliares (uno para X otro para Y) en los
     * que se introduce el nuevo valor de coordenadas segun el punto + distancia
     * a la que se debe desplazar.
     * Distancia calculada mediante la diferencia entre el punto al que queremos
     * llevarla menos el punto inicial.
     * La última parte sirve para que si el trazo libre había sido cerrado 
     * previamente, siga estandolo durante el desplazamiento de este.
     * 
     * @param p Punto al que se debe desplazar nuestro trazo libre
     */
    public void setLocationFreeDraw(Point2D p){
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
                setFirstPointGP( Xpoint2.get(i), Ypoint2.get(i) );
                firstPoint = false;
            }
            else{
                setGP( Xpoint2.get(i), Ypoint2.get(i) );  
            } 
            
        }
        
    }
    
    /**
     * Pintará el objeto MyGP.
     * Se encarga de pintar el cambio de color, cambio de trazo y el area 
     * contenedora de la figura en caso de estar seleccionada.
     * 
     * @param g2d gráfico donde se debe pintar nuestro objeto(MyGP)
     */
    public void MyPaint(Graphics2D g2d){
        super.MyPaint(g2d);
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
