/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.Model;

import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

/**
 *
 * @author brhil
 */
public class CustomPolygon extends Polygon {
    
   
/*****************************************************************************
 *****************************************************************************/
    private static Color selectedColor = Color.web("Gray", 0.2);
    private static Color strokeColor = Color.BLACK;
    
    private final ArrayList<Circle> circleListe=new ArrayList<>();
    
    private AnchorPane Parent;
    private ImageView parentImg;
    
    private Double Kw=new Double(1);
    private Double Kh=new Double(1);
    
    public CustomPolygon() {
        
        this.setFill(selectedColor);
        this.setStroke(strokeColor);
        
        this.setOnMousePressed(Poly_pressed);
        this.setOnMouseDragged(Poly_Dragged);
        this.setOnMouseReleased(Poly_released);
        this.setOnMouseEntered((event) -> {
            
        });
    }
    
    public CustomPolygon(AnchorPane parent) {
        
        this.setFill(selectedColor);
        this.setStroke(strokeColor);
        
        this.setOnMousePressed(Poly_pressed);
        this.setOnMouseDragged(Poly_Dragged);
        this.setOnMouseReleased(Poly_released);
        this.setOnMouseEntered((event) -> {
            
        });
        
        addTo(parent);
        
    }

    public void addTo(AnchorPane parent) {
        
        this.Parent= parent;
        parent.getChildren().add(this);
        ImageView img=null;
        for (Object obj : parent.getChildren()) {
            
            if(obj instanceof ImageView) {
                img=(ImageView)obj;
                break;
            }
        }
        parentImg=img;
        Bind(img);
        
        for (int i = 0; i < getPoints().size(); i=i+2) {
            
            Circle c = new Circle(10, Color.CHARTREUSE);
            c.setLayoutX(getLayoutX());
            c.setLayoutY(getLayoutY());
            c.setCenterX(getPoints().get(i));
            c.setCenterY(getPoints().get(i+1));
            parent.getChildren().add(c);
            
            circleListe.add(c);
            
            c.setOnMousePressed(Circle_pressed);
            c.setOnMouseDragged(Circle_Draged);
            c.setOnMouseReleased(Circle_released);
        }
        
    }
    public void RemoveFrom(AnchorPane parent) {
        
        this.Parent= null;
        parent.getChildren().remove(this);
        parent.getChildren().removeAll(circleListe);
        UnBind();
        
    }
    
    EventHandler<MouseEvent> Poly_pressed= (MouseEvent event) -> {
    };
    EventHandler<MouseEvent> Poly_Dragged= (MouseEvent event) -> {
    };;
    EventHandler<MouseEvent> Poly_released= (MouseEvent event) -> {
    };;
    EventHandler<MouseEvent> Circle_pressed= (MouseEvent event) -> {
    };;
    EventHandler<MouseEvent> Circle_Draged= (MouseEvent event) -> {
    };;
    EventHandler<MouseEvent> Circle_released= (MouseEvent event) -> {
    };;
    
    private void Bind(ImageView img) {
            parentImg = img;
        if (img!= null) {
            this.layoutXProperty().bind(img.layoutXProperty());
            this.layoutYProperty().bind(img.layoutYProperty());
        }
    }
    private void UnBind() {
        parentImg=null;
        this.layoutXProperty().unbind();
        this.layoutYProperty().unbind();
    }
    public void setK(Double k){
    
        setKw(k);
        setKh(k);
    
    }
    public void setK(Double kw,Double kh){
    
        setKw(kw);
        setKh(kh);
    
    }
    public void setKw(Double kw){
        
        Double defkw = kw/Kw;
        
        for(int i=0;i<getPoints().size();i=i+2){
            
            Double p = getPoints().get(i);
            Double P=p*defkw;

            getPoints().set(i, P);
        }
        
        Kw=kw;
        
    }
    public void setKh(Double kh){
        
        Double defkh = kh/Kh;
        
        for(int i=1;i<getPoints().size();i=i+2){
            
            Double p = getPoints().get(i);
            p=p*defkh;

            getPoints().set(i, p);
        }
        
        Kh=kh;
    }

//..................Collision.............................................................................. 
    private Segment[] segments;
     
    public Segment[] getSegments() {

        Double[] arrayOfPoints = this.getPoints().toArray(new Double[0]);

        Segment[] s = new Segment[arrayOfPoints.length / 2];

        int index = 0;
        for (int x = 0; x < arrayOfPoints.length; x = x + 2) {

            Segment temp;
            try {
                // if last Point out of array exption invoked
                temp = new Segment(arrayOfPoints[x], arrayOfPoints[x + 1], arrayOfPoints[x + 2], arrayOfPoints[x + 3]);
            } catch (Exception i) {
                //then create segment between last and first Point
                temp = new Segment(arrayOfPoints[x], arrayOfPoints[x + 1], arrayOfPoints[0], arrayOfPoints[1]);
            }
            s[index] = temp;
            index++;
        }

        return s;

    }

    public Vector[] getVectors() {
        
        Double[] arrayOfPoints = this.getPoints().toArray(new Double[0]);

        Vector[] s = new Vector[arrayOfPoints.length / 2];

        int index = 0;
        
        for (int x = 0; x < arrayOfPoints.length; x = x + 2) {

            s[index] = new Vector(arrayOfPoints[x], arrayOfPoints[x + 1]);

            index++;
        }
        return s;
    }

    public boolean intersects(CustomPolygon poly) {

        Bounds localToScreen = localToScreen(getBoundsInLocal());
        Bounds localToScreen1 = poly.localToScreen(poly.getBoundsInLocal());

        if (!localToScreen.intersects(localToScreen1)) {
            return false;
        }

        for (Vector v : getVectors()) {

            if (poly.contains(v)) {
                return true;
            }

        }

        for (Segment A : getSegments()) {
            for (Segment B : poly.getSegments()) {

                if (A.Intersect(B) != null) {
                    return true;
                }

            }
        }
        return false;

    }

    public boolean contains(Vector x) {

        Segment segment = new Segment(x.getX(), x.getY(), this.getLayoutX() - 1, this.getLayoutY() - 1);

        for (Segment temp : getSegments()) {
            if (segment.Intersect(temp) != null) {
                return true;
            }
        }

        return false;
    }
    public void addPoint(Vector v){
        
        addPoint(v.getX(), v.getY());
    }
    public void addPoint(double x, double y){
        
        getPoints().addAll(x,y);
        segments=null;
    }
    public void removePoint(int index){
        
        if(index < 0 || index > getPoints().size()-1) return;        
    }

/***********************I********************************
 ********************** / ********************************
 ***********************O********************************/
    
    @Override
    public String toString() {
        
        String S="";
        
        for(int i=0;i<getPoints().size();i++){
            
            Double p = getPoints().get(i);
            if(i%2 == 0){
                
                Double P=p*Kw;

                S= S.concat(P.toString()+",");
            }else{
                
                Double P=p*Kh;

                S= S.concat(P.toString()+",");
            }
            
        }
        
        return S.substring(S.length()-1); //To change body of generated methods, choose Tools | Templates.
    }
    public static CustomPolygon fromString(String S){
        try{
        CustomPolygon customPolygon = new CustomPolygon();
        String[] split = S.split(",");
        for(int i=0;i<split.length;i=i+2){
            
            customPolygon.addPoint(new Double(split[i]),new Double(split[i+1]));
            
        }
        return customPolygon;
        }catch(Exception i){
            return null;
        }
        
    }
    
    
    
}
