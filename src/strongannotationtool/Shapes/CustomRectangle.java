

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author brhil
 */
public class CustomRectangle extends Rectangle {

    private boolean selected;
    private Color color;

    public CustomRectangle(double x, double y, double width, double height) {

        super(width, height ,Color.TRANSPARENT );
        super.setLayoutX(x);
        super.setLayoutY(y);
        
    }
    public CustomRectangle(double x, double y, double width, double height,Color fill , Boolean Selected) {
        
        super(width, height);
        
        
        super.setLayoutX(x);
        super.setLayoutY(y);
        
        selected= Selected;
        color=fill;
        
        if(selected) super.setFill(fill);
        else super.setFill(Color.TRANSPARENT);
        
    }
    public void setRectFill(Color C){
        
        color=C;
    }
    public Color getRectFill(){
        
        return color;
    }
    
    public void select(Boolean selected){
        
        this.selected=selected;
        
        if(selected) setFill(color);
        
        else setFill(Color.TRANSPARENT);
    }
    
    boolean isSelected() {
        return selected;
    }
    
    public boolean intersects(Rectangle rect) {

        if (this.getLayoutX() < rect.getLayoutX() + rect.getWidth() && this.getLayoutX() + this.getWidth() > rect.getLayoutX()
                && this.getLayoutY() < rect.getLayoutY() + rect.getHeight() && this.getLayoutY() + this.getHeight() > rect.getLayoutY()) {
            return true;
        }
        
        if (rect.getX() < this.getX() + this.getWidth() && rect.getX() + rect.getWidth() > this.getX() &&
                rect.getY() < this.getY() + this.getHeight() && rect.getY() + rect.getHeight() > this.getY()) {
                return true;
        }
         
        return false;

    }

    public boolean intersects(CustomPolygon poly) {

        if (IfBoundsIntersect(poly)) return false;

        else if (IfPolyInsideRect(poly))  return true;
        
        else if(IfRectSegmentIntersectPolySegment(poly)) return true;

        else if(IfInsideRect(poly)) return true;
        
        else return false;
    }

    public boolean IfInsideRect(CustomPolygon poly) {
        double layoutX = this.getLayoutX();
        double layoutY = this.getLayoutY();
        if (poly.contains(new Vector(layoutX, layoutY))) {
            return true;
        }
        return false;
    }

    public boolean IfRectSegmentIntersectPolySegment(CustomPolygon poly) {
        Segment[] segmentpoly = poly.getSegments();
        Segment[] sugmentrect = new Segment[5];
        double layoutX = this.getLayoutX();
        double layoutY = this.getLayoutY();
        double layoutX2 = this.getLayoutX()+this.getWidth();
        double layoutY2 = this.getLayoutY()+this.getHeight();
        sugmentrect[0] = new Segment(layoutX,layoutY,layoutX2,layoutY);
        sugmentrect[1] = new Segment(layoutX2,layoutY, layoutX2, layoutY2);
        sugmentrect[2] = new Segment(layoutX2, layoutY2,layoutX,layoutY2);
        sugmentrect[3] = new Segment(layoutX,layoutY2,layoutX,layoutY);
        sugmentrect[4] = new Segment(layoutX,layoutY,layoutX2, layoutY2);
        for (Segment A : sugmentrect) {
            for (Segment B : segmentpoly) {
                if (A.Intersect(B) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean IfPolyInsideRect(CustomPolygon poly) {
        for (Vector v : poly.getVectors()) {
            if (this.inside(v)) {
                return true;
            }
        }
        return false;
    }

    public boolean IfBoundsIntersect(CustomPolygon poly) {
        Bounds localToScreen = poly.localToScreen(poly.getBoundsInLocal());
        Bounds localToScreen1 = localToScreen(getBoundsInLocal());
        if (!localToScreen.intersects(localToScreen1)) {
            return true;
        }
        return false;
    }

    public boolean contains(Segment s) {

        Vector pointA = s.getVectorA();
        Vector pointB = new Vector(s.getVectorA().getX()+s.getVectorB().getX(), s.getVectorA().getY()+s.getVectorB().getY());
        
        return inside(pointA)
                || inside(pointB);
    }

    public boolean intersects(Segment s) {

        Vector pointA = s.getVectorA();
        Vector pointB = new Vector(s.getVectorA().getX()+s.getVectorB().getX(), s.getVectorA().getY()+s.getVectorB().getY());
        
        return inside(pointA)
                != inside(pointB);
    }

    public boolean inside(Segment s) {

        Vector pointA = s.getVectorA();
        Vector pointB = new Vector(s.getVectorA().getX()+s.getVectorB().getX(), s.getVectorA().getY()+s.getVectorB().getY());
        
        return inside( pointA)
                && inside( pointB);
    }

    public boolean inside(Vector v) {

        return inside(v.getX(), v.getY());

    }
    
    public boolean inside(double x, double y) {

        return this.getLayoutX() < x
                && this.getLayoutX() + this.getWidth() >= x
                && this.getLayoutY() < y
                && this.getLayoutY() + this.getHeight() >= y;
    }

}
