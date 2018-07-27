/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.jj.iu;

import java.awt.AlphaComposite;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Collection;
import java.util.Collections;
import javafx.embed.swing.SwingNode;
import sm.jj.graficos.FreeDraw;
import sm.jj.graficos.MyForm;
import sm.jj.graficos.MySpiral;
import sm.jj.graficos.MyQuadCurve;
import sm.jj.graficos.MyLine;
import sm.jj.graficos.MyOval;
import sm.jj.graficos.MyRectangle;
import sm.jj.graficos.MyShape;
import sm.jj.graficos.WithFilled;

/**
 *
 * @author Jota
 */
public class Lienzo2D extends javax.swing.JPanel {

    
    /**
     * MyVector : vector de formas
     * shape    : objeto MyShape
     * Color    : para establecer el color de la forma
     * SecondColor : para establecer el color del borde de la forma
     * Stroke   : para establecer grosor de la forma
     * grosor   : entero para establecer grosor 
     * trasparencia : para establecer la transparencia de la forma
     * UltimoBorde : controlar el tipo de trazo(Continuo, discontinuo, discontinuo2)
     * lastPosition : guarda la posición del vector en el que se escribe la 
     *                  linea de la curva con punto de control
     * selected : establece si la forma esta seleccionada
     * formClose : control para "MyForm"
     * lastShape : almacena la última figura creada , modificada o seleccionada
     * clipLienzo : area contenedora de Lienzo
     * rendert : control del alisado de una forma
     * tool : almacena la herramienta seleccionada
     * p1,p2,p3 : puntos utilizados para crear y modificar las formas
     * relleno : controla si una figura esta rellena
     * clarity : controla si una figura
     */
    private ArrayList<MyShape> MyVector = new ArrayList();
    private MyShape shape = new MyShape();
    private Color color = new Color(0,0,0);
    private Color secondColor = new Color(255,255,255);
    private Stroke stroke = new BasicStroke(1);
    private int grosor = 1; 
    private float transparencia=1.0f;
    private String ultimoBorde = "Continuas";
    private int lastPosition = 0;
    private boolean selected = false;
    private boolean FormClose = false;
    private MyShape lastShape = new MyShape();//para el editar
    private Shape clipLienzo = null;
    boolean rendert = false;
    int tool = 1;
    Point p1 = new Point(-10,-10);
    Point p2 = new Point(-10,-10);
    Point p3 = new Point(-10,-10);
    boolean relleno = false;
    boolean rellenoV = false;
    boolean rellenoH = false;
    /**
     * Creates new form Lienzo2D
     */
    public Lienzo2D() {
        initComponents();
    }

    /**
     * Metodo para pintar, recorre un vector de formmas y las pinta sobre un 
     * grafico g2d.
     * 
     * @param g grafico
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setClip(this.clipLienzo);
        for (MyShape s : MyVector) {
            s.MyPaint(g2d);
        }
        
    }
    
    /**
     * Devuelve una forma si ha sido seleccionada mediante un punto indicado.
     * 
     * @param p punto indicado
     * @return s forma seleccionada, null en caso de no seleccionar nada.
     */
    public MyShape getSelectedShape(Point2D p){
        ArrayList<MyShape> auxV = new ArrayList();
        
        for(int i = this.MyVector.size()-1 ; i >= 0 ; i--){
            auxV.add(this.MyVector.get(i));
        }
        
        for (MyShape s : auxV){
            
            if( s instanceof MyLine){
                if( ((MyLine)s).myContainsLine(p) ){
                    return s;
                }
            }
            else if( s instanceof MyRectangle){
                if( ((MyRectangle)s).getMyRectangle().contains(p) )
                return s;        
            }
            else if( s instanceof MyOval){
                if( ((MyOval)s).getMyOval().contains(p)   )
                return s;
            }
            else if( s instanceof MyQuadCurve){
                if( ((MyQuadCurve)s).getMyQC().contains(p)   )
                return s;
            }
            else if( s instanceof MySpiral){
                if( ((MySpiral)s).myContainsLine(p) )
                return s;
            }
            else if( s instanceof FreeDraw ){
                if( ((FreeDraw)s).getMyGP().contains(p)   )
                return s;
            }
            else if( s instanceof MyForm ){
                if( ((MyForm)s).getMyForm().contains(p)   )
                return s;
            
            }
            
        }  
        return null;
    }
    
    /**
     * Establece los atributos a una forma
     * 
     * @param s forma
     */
    public void setAtributos(MyShape s){
        if(s!=null){
            s.setSelected(this.selected);
            s.setColor(this.color);
            s.setBackgroundColor(this.secondColor);
            s.setStroke(this.stroke);
            s.setComposite(this.transparencia);
            if(s instanceof WithFilled){
                    ((WithFilled)s).setFilled(this.relleno);
                    ((WithFilled)s).setFilledDegradadaVertical(this.rellenoV);
                    ((WithFilled)s).setFilledDegradadaHorizontal(this.rellenoH);
            }
        }
    }
    
    /**
     * Devuelve el area contenedora del Lienzo.
     * 
     * @return clipLienzo area contenedora
     */
    public Shape getClip(){
        return this.clipLienzo;
    }
    
    /**
     * Establece el area contenedora del Lienzo
     * 
     * @param r area del rectangulo del lienzo
     */
    public void setClip(Rectangle2D r){
        this.clipLienzo = r.getBounds2D();
    }
    
    /**
     * Establece el grosor de forma
     * 
     * @param str grosor
     */
    public void setStroke(int str){
        this.grosor = str;
        setBorder(ultimoBorde);
        
        if(this.tool == 9){
            setAtributos(this.shape);
            repaint();
        }
    }
    
    /**
     * Devuelve el valor del grosor de la forma
     * 
     * @return aux valor grosor entero
     */
    public int getStroke(){
        String aux = stroke.toString();
        return Integer.parseInt(aux);
    }
    
    /**
     * Establece el tipo de borde de la forma, siendo posible: 1.Continuo, 
     * 2.Discontinuo y 3.Discontinuo2
     * 
     * @param c Tipo de borde
     */
    public void setBorder(String c){
        ultimoBorde = c;
        if(c == "Continuas"){
            this.stroke = new BasicStroke(this.grosor);
        }
        else if(c == "Discontinuas"){
            this.stroke = new  BasicStroke(
                this.grosor,               // grosor: this.grosor
                BasicStroke.CAP_BUTT,      // terminación: recta
                BasicStroke.JOIN_ROUND,    // unión: redondeada 
                1f,                        // ángulo: 1 grado
                new float[] {5, 5, 5, 5},  // línea de 5, 5 blancos, línea de 5, 5 blancos
                2                          // fase
            );
        }
        else if(c == "Discontinuas2"){
            this.stroke = new  BasicStroke(
                this.grosor,               // grosor: this.grosor
                BasicStroke.CAP_BUTT,      // terminación: recta
                BasicStroke.JOIN_ROUND,    // unión: redondeada 
                1f,                        // ángulo: 1 grado
                new float[] {15, 5, 1, 5}, // línea de 15, 5 blancos, línea de 1, 5 blancos
                2                          // fase
            );
        }
        if(this.tool == 9){
            repaint();
        }
    }
    
    /**
     * Establece el color de la forma
     * 
     * @param c color
     */
    public void SetColor(Color c){
        this.color = c;
    
        if(this.tool == 9){
            setAtributos(this.shape);
        }
    }
    
    /**
     * Devuelve el color de la forma
     * 
     * @return c color
     */
    public String getColor(){
        String c = this.color.toString();
        return c;
    }
    
    /**
     * Establece el color del relleno de la forma
     * 
     * @param c color
     */
    public void SetBackgroundColor (Color c){
        this.secondColor = c;
        
        if(this.tool == 9){
            setAtributos(this.shape);
            repaint();
        }
    }
    
    /**
     * Devuelve el valor del color del relleno
     * 
     * @param c color
     */
    public void GetBackgroundColor (Color c){
        this.secondColor = c;
    }
    
    /**
     * Establece si una forma esta rellena o no
     * 
     * @param p "true": con relleno "false": sin relleno
     */
    public void setFill(boolean p){
        this.relleno = p;
        if(this.tool == 9){
            setAtributos(this.shape);
            repaint();
        }
    }
    
    /**
     * Devuelve si una forma esta rellena o no
     * 
     * @return relleno "true": lo esta "false": no lo esta
     */
    public boolean getFill(){
        return this.relleno;
    }
    
    /**
     * Establece si la forma estará rellena con degradado horizontal.
     * 
     * @param p "true": lo esta "false": no lo esta
     */
    public void setFillH(boolean p){
        this.rellenoH = p;
        if(this.tool == 9){
            setAtributos(this.shape);
            repaint();
        }
    }
    
    /**
     * Devuelve si una forma esta rellena o no
     * 
     * @return relleno "true": lo esta "false": no lo esta
     */
    public boolean getFillH(){
        return this.rellenoH;
    }
    
    /**
     * Establece si la forma estará rellena con degradado vertical.
     * 
     * @param p "true": lo esta "false": no lo esta 
     */
    public void setFillV(boolean p){
        this.rellenoV = p;
        if(this.tool == 9){
            setAtributos(this.shape);
            repaint();
        }
    }
    
    /**
     * Devuelve si una forma esta rellena o no
     * 
     * @return relleno "true": lo esta "false": no lo esta
     */
    public boolean getFillV(){
        return this.rellenoV;
    }
    
    /**
     * Establece el grado de transparencia de una forma.
     * 
     * @param f valor float entre 0.0F(Invisible) y 1.0F(totalmente visible)
     */
    public void setClarity(float f){
       this.transparencia = f;
       
       if(this.tool == 9){
            setAtributos(this.shape);
            repaint();
        }
    }
    
    /**
     * Devuelve el valor de transparencia de una forma
     * 
     * @return clarity valor transparencia
     */
    public float getClarity(){
        return transparencia;
    }
    
    /**
     * Establece si el alisado de una forma
     * 
     * @param alisar "true": alisa "false": no alisa
     */
     public void setSmooth(boolean alisar){ 
        this.shape.setRender(alisar);
        this.rendert = alisar;
        
        if(this.tool == 9){
            setAtributos(this.shape);
            repaint();
        }
    }
    
    /**
     * Devuelve si la figura ha sido alisada o no
     * 
     * @return rendert "true" : si "false": no
     */
    public boolean getSmooth(){
        return this.rendert;
        
    }
    
    /**
     * Establece la herramienta seleccionada:
     * 1.Punto
     * 2.Linea
     * 3.Rectangulo
     * 4.Elipse
     * 5.Curva con un punto de control
     * 6.Espiral
     * 7.Trazo libre
     * 8.My Forma
     * 9.Editar
     * 
     * @param atributo tipo de herramienta seleccionada
     */
    public void setTool(int atributo){
        this.tool = atributo;  
    }
    
    /**
     * Devuelve la herramienta seleccionada
     * 
     * @return tool herramienta seleccionada
     */
    public int getTool(){
        return this.tool;
    }
    
    /**
     * Sube una posición adelante la forma en el vector de formas.
     * CUANTO MÁS A LA DERECHA EN EL VECTOR MÁS ARRIBA ESTARÁ
     */
    public void setUp(){
        if(this.shape != null && this.shape.getSelected() && this.shape != this.MyVector.get(this.MyVector.size()-1) ){
            ArrayList<MyShape> MyVectorAux = new ArrayList();
          
            
            for(int i = 0; i < MyVector.size() ; i++){
                if(this.shape != MyVector.get(i))
                    MyVectorAux.add(MyVector.get(i));
                else if( this.shape == MyVector.get(i) ){
                    MyVectorAux.add(MyVector.get(i+1));
                    MyVectorAux.add(MyVector.get(i));
                    i++;
                }
            }

            this.MyVector.clear();
            for(int i = 0; i< MyVectorAux.size() ; i++){
                MyVector.add(MyVectorAux.get(i));
            }
            for(int i = 0; i< MyVector.size() ; i++){
                System.out.println("Contenido Vector:"+MyVector.get(i));
            }
        }
        this.repaint();
    }
    
    /**
     * Baja una posición adelante la forma en el vector de formas.
     * CUANTO MÁS A LA DERECHA EN EL VECTOR MÁS ARRIBA ESTARÁ
     */
    public void setDown(){
        if(this.shape != null && this.shape.getSelected()){
            int aux = 0;
            ArrayList<MyShape> MyVectorAux = new ArrayList();
            MyShape shapeAux= null;
            
            for(int i=0 ; i < MyVector.size(); i++ ){
                if(this.shape == MyVector.get(i))
                    aux = i;
            }
            Collections.swap(MyVector, aux-1, aux);
        }
        this.repaint();
    
    }
    
    /**
     * Sube del todo en el lienzo la forma en el vector de formas.
     * CUANTO MÁS A LA DERECHA EN EL VECTOR MÁS ARRIBA ESTARÁ
     */
    public void setUpAll(){
        if(this.shape != null && this.shape.getSelected() && this.shape != this.MyVector.get(this.MyVector.size()-1) ){
            int aux = 0;
            ArrayList<MyShape> MyVectorAux = new ArrayList();
            MyShape shapeAux= null;
            
            for(int i=0 ; i < MyVector.size(); i++ ){
                if(this.shape == MyVector.get(i))
                    aux = i;
            }
            
            for(int i = 0; i < MyVector.size() ; i++){
                if(this.shape != MyVector.get(i))
                    MyVectorAux.add(MyVector.get(i));
            }
            MyVectorAux.add(MyVector.get(aux));
            this.MyVector.clear();
            for(int i = 0; i< MyVectorAux.size() ; i++){
                MyVector.add(MyVectorAux.get(i));
            }
            for(int i = 0; i< MyVector.size() ; i++){
                System.out.println("Contenido Vector:"+MyVector.get(i));
            }
        }
        this.repaint();
    }
    
    /**
     * Baja del todo en el lienzo la forma en el vector de formas.
     * CUANTO MÁS A LA DERECHA EN EL VECTOR MÁS ARRIBA ESTARÁ
     */
    public void setDownAll(){
        if(this.shape != null && this.shape.getSelected()){
            int aux = 0;
            ArrayList<MyShape> MyVectorAux = new ArrayList();
            MyShape shapeAux= null;
            
            
            for(int i=0 ; i < MyVector.size(); i++ ){
                if(this.shape == MyVector.get(i))
                    aux = i;
            }
            MyVectorAux.add(MyVector.get(aux));
            for(int i = 0; i < MyVector.size() ; i++){
                if(this.shape != MyVector.get(i))
                    MyVectorAux.add(MyVector.get(i));
                    
            }
            this.MyVector.clear();
            for(int i = 0; i< MyVectorAux.size() ; i++){
                MyVector.add(MyVectorAux.get(i));
            }
            for(int i = 0; i< MyVector.size() ; i++){
                System.out.println("Contenido Vector:"+MyVector.get(i));
            }
        }
        this.repaint();
    }
    
    /**
     * Sirve para actualizar la posición de los puntos de las formas
     * 
     * @param s forma
     * @param p1 punto inicio
     * @param p2 punto fin
     */
    public void updateShape(MyShape s,Point p1, Point p2){
        switch(this.tool){
            case 1:
                ((MyLine)s).setMyLine(p1, p1);
                break;
            case 2:
                ((MyLine)s).setMyLine(p1, p2);
                break;
            case 3:
                ((MyRectangle)s).setMyRectangleFirstPoin(p1,p2);
                break;
            case 4:
                ((MyOval)s).setMyOval(p1, p2);
                break;
            case 5:
                ((MyQuadCurve)s).getLineQC().setMyLine(p1, p2);
                break;
            case 6:
                ((MySpiral)s).setFirstPointGP(p1.getX(), p1.getY());
                ((MySpiral)s).setGP(p2.getX(),p2.getY());
                break;
            case 7:
                ((FreeDraw)s).setGP(p2.getX(),p2.getY());
                break;
            case 8:
                ((MyForm)s).setMorePoints(p1.getX(),p1.getY());
                break;
        }
    }
    
    /**
     * Crea la figura en función de la herramienta seleccionada y la añade al
     * vector de formas. Y actualiza sus atributos.
     * 
     */
    public void createShape(){
        this.shape = null;
        
        
        switch(this.tool){
            case 1:
                this.shape = new MyLine();
                this.MyVector.add(this.shape);  
                break;
            case 2:
                this.shape = new MyLine();
                this.MyVector.add(this.shape);
                break;
            case 3:
                this.shape = new MyRectangle();
                this.MyVector.add(this.shape);
                break;
            case 4:
                this.shape = new MyOval();
                this.MyVector.add(this.shape);
                break;
            case 5:
                this.shape = new MyQuadCurve();
                this.MyVector.add(this.shape);
                break;
            case 6:
                this.shape = new MySpiral();
                this.MyVector.add(this.shape);
                break;
            case 7:
                this.shape = new FreeDraw();
                this.MyVector.add(this.shape);
                ((FreeDraw)this.shape).setFirstPointGP(p1.getX(), p1.getY());
                break;
            case 8:
                this.shape = new MyForm();
                this.MyVector.add(this.shape);
                ((MyForm)this.shape).setMyFormFirstPoint(p1.getX(),p1.getY());
                this.FormClose = true;
                break;
        }
    
        if(this.shape != null){
            setAtributos(this.shape);
        }
            
    }
           
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    
    /**
     * Evento de presión del ratón.
     * A partir de él mandamos crear la figura en función de la herramienta 
     * seleccionada.
     * Además controlamos en la seccion editar, la figura seleccionada y que se
     * añada su area contenedora a esta.
     * 
     * @param evt evento
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        this.p1 = evt.getPoint();
        
        if(this.tool == 2){
            this.selected = false;
            if(this.shape != null)
            this.shape.setSelected(false);
            createShape();   
        }
        else if(this.tool == 3){
            this.selected = false;
            if(this.shape != null)
            this.shape.setSelected(false);
            createShape();            
        }
        else if(this.tool == 4){
            this.selected = false;
            if(this.shape != null)
            this.shape.setSelected(false);
            createShape();
        }
        else if(this.tool == 5){
            this.selected = false;
            if(this.shape != null)
            this.shape.setSelected(false);
            createShape();
        }
        
        else if(this.tool == 6){
            this.selected = false;
            if(this.shape != null)
            this.shape.setSelected(false);
            createShape();
        }
        else if(this.tool == 7){
            this.selected = false;
            if(this.shape != null)
            this.shape.setSelected(false);
            createShape();
        }else if(this.tool == 9){ // EDITAR 
            this.shape = getSelectedShape(p1);
            if(!evt.isMetaDown() ){
                if(this.shape != null){
                    this.selected = true;
                    this.shape.setSelected(this.selected);
                }
                if(this.shape == null){
                    this.shape = this.lastShape;
                }
                else if(this.shape != this.lastShape && this.lastShape != null ){
                    System.out.println("ei");
                    this.lastShape.setSelected(false);
                }
                System.out.println("getSelectedShape: "+shape);

                if(this.shape instanceof MyLine)
                    ((MyLine)this.shape).setMyClip();
                else if(this.shape instanceof MyRectangle)
                    ((MyRectangle)this.shape).setMyClip();
                else if(this.shape instanceof MyOval)
                    ((MyOval)this.shape).setMyClip();
                else if(this.shape instanceof MyQuadCurve)
                    ((MyQuadCurve)this.shape).setMyClip();
                else if(this.shape instanceof MySpiral)
                    ((MySpiral)this.shape).setMyClip();
                else if(this.shape instanceof FreeDraw)
                    ((FreeDraw)this.shape).setMyClip();
                else if(this.shape instanceof MyForm)
                    ((MyForm)this.shape).setMyClip();
            }
            
        }
        
        this.repaint();
    }//GEN-LAST:event_formMousePressed

    
    /**
     * Evento arrastrar ratón.
     * En este evento actualizamos los puntos en los que deben estar nuestras 
     * formas en caso de estar creandolas.
     * En el caso de editar modificamos la posición en la que se encuentran 
     * mientras se mantiene el area contenedora de estos.
     * 
     * @param evt evento
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        this.p2 = evt.getPoint();
        
        if(this.tool == 2){
            updateShape(this.shape,p1,p2);
        }
        else if(this.tool == 3){
            updateShape(this.shape,p1,p2);
        }
        else if(this.tool == 4){
            updateShape(this.shape,p1,p2);
        }
        else if(this.tool == 5){
            updateShape(this.shape, p1, p2);
            lastPosition = MyVector.size()-1;
        }
        else if(this.tool == 6){
            updateShape(this.shape, p1, p2);
        }
        else if(this.tool == 7){
            updateShape(this.shape, p1, p2);
        }
        else if( (this.tool)== 9 ){ 
            if(this.shape != null){
                if(this.shape instanceof MyLine){
                    System.out.println("Linea seleccionada");
                    ((MyLine)this.shape).setLocationMyLine(p2);
                    ((MyLine)this.shape).setMyClip();
                }
                else if(this.shape instanceof MyRectangle){
                    System.out.println("Rectangulo seleccionado dentro.");
                    ((MyRectangle)this.shape).setLocationMyRectangle(p2);
                    ((MyRectangle)this.shape).setMyClip();
                    
                }
                else if(this.shape instanceof MyOval){
                    System.out.println("Ovalo seleccionado dentro.");
                    ((MyOval)this.shape).setLocationMyOval(p2);
                    ((MyOval)this.shape).setMyClip();
                }
                else if(this.shape instanceof MyQuadCurve){
                    System.out.println("QueadCurve seleccionado dentro.");
                    ((MyQuadCurve)this.shape).setLocationMyQC(p2);
                    ((MyQuadCurve)this.shape).setMyClip();
                }
                else if(this.shape instanceof MySpiral){
                    System.out.println("Spiral seleccionado dentro.");
                    ((MySpiral)this.shape).setLocationSpiral(p2);
                    ((MySpiral)this.shape).setMyClip();
                }
                else if(this.shape instanceof FreeDraw){
                    System.out.println("freeDraw seleccionado");
                    ((FreeDraw)this.shape).setLocationFreeDraw(p2);
                    ((FreeDraw)this.shape).setMyClip();
                }
                else if(this.shape instanceof MyForm){
                    System.out.println("freeDraw seleccionado");
                    ((MyForm)this.shape).setLocationMyForm(p2);
                    ((MyForm)this.shape).setMyClip();
                }
            }
            
        }
        this.repaint();
        
    }//GEN-LAST:event_formMouseDragged

    /**
     * Evento click del ratón
     * En esta función se puede crear un punto en caso de tool == 1.
     * Insertar el punto de control de la curva en caso de tool == 5.
     * Se controla el buen funcionamiento de Mi Forma en caso de tool == 8.
     *  (Click izquierdo) en caso de no ser cerrada , se establece un nuevo
     *   punto inicial para mi forma.
     *  (Click izquierdo) en caso de ser cerrada , se manda cerrar.
     *  (Click derecho) seleccionar el resto de puntos de Mi Forma.
     * 
     * @param evt evento
     */
    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        this.p1 = evt.getPoint();
        this.p2 = evt.getPoint();
        this.p3 = evt.getPoint();
        if(this.tool == 1){
            createShape();
            updateShape(this.shape,p1,p2);
        }
        else if(this.tool == 5){
            this.shape = MyVector.get(lastPosition);
            if( !((MyQuadCurve)this.shape).getIsPossibleArco() )
                ((MyQuadCurve)this.shape).setArcMQ(p3);
        }
        else if(this.tool == 8 && !evt.isMetaDown()){
            if(!FormClose)
                createShape();
            else if(FormClose){
                
                ((MyForm)this.shape).closeMyForm();
                this.shape = null;
                this.FormClose=false;
            }
        }
        else if(this.tool == 8 && evt.isMetaDown()){
            updateShape(this.shape, p1, p2);
   
        }

        this.repaint();
    }//GEN-LAST:event_formMouseClicked

    /**
     * Evento desclickar ratón.
     * Repintado del lienzo.
     * En caso de tool == 9 , guarda cual fue la última forma seleccionada
     *  (Click Derecho) Para de editar.
     * 
     * @param evt evento
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        
        if(this.tool==9){
            repaint();
            this.lastShape = this.shape;
            if(evt.isMetaDown()){ //Parar de editar esa figura
                this.selected = false; 
                this.shape.setSelected(this.selected);
                this.shape = null;
                this.lastShape = null;
            }
        }
        repaint();
        
    }//GEN-LAST:event_formMouseReleased

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
