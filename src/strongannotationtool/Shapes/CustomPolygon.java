/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 *
 * @author brhil
 */
public class CustomPolygon extends Polygon {
    
    private Segment[] segments;

    public CustomPolygon() {
        
        setFill(Color.ANTIQUEWHITE);
        
    }
    
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
}
