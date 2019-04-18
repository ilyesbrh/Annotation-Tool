/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


/**
 *
 * @author brhil
 */
public class Segment {
    
    private Vector VectorA; 
    private Vector VectorB; 

    public Segment(double x, double y , double x2, double y2) {
        this.VectorA= new Vector(x, y);
        this.VectorB= new Vector(x2-x, y2-y);
        
    }
    
    public Vector getVectorA() {
        return VectorA;
    }

    public void setVectorA(Vector VectorA) {
        this.VectorA = VectorA;
    }

    public Vector getVectorB() {
        return VectorB;
    }

    public void setVectorB(Vector VectorB) {
        this.VectorB = VectorB;
    }
   
    /**
     *
     * @param segment
     * @return if true return intersect point else return null 
     */
    public Vector Intersect(Segment segment) {
            // a
        Double x1 = this.VectorA.getX();
        Double y1 = this.VectorA.getY();
        Double x2 = this.VectorB.getX()+x1;
        Double y2 = this.VectorB.getY()+y1;

            // b
        Double x3 = segment.VectorA.getX();
        Double y3 = segment.VectorA.getY();
        Double x4 = segment.VectorB.getX()+x3;
        Double y4 = segment.VectorB.getY()+y3;	    

        Double a1, a2, b1, b2, c1, c2;
        Double r1, r2 , r3, r4;
        Double denom, offset, num;
        
        Vector intersection= new Vector(0.0, 0.0);

        a1 = y2 - y1;
        b1 = x1 - x2;
        c1 = (x2 * y1) - (x1 * y2);

        r3 = ((a1 * x3) + (b1 * y3) + c1);
        r4 = ((a1 * x4) + (b1 * y4) + c1);

        if ((r3 != 0) && (r4 != 0) && same_sign(r3, r4))
            return null;

        a2 = y4 - y3; // Compute a2, b2, c2
        b2 = x3 - x4;
        c2 = (x4 * y3) - (x3 * y4);
        r1 = (a2 * x1) + (b2 * y1) + c2; // Compute r1 and r2
        r2 = (a2 * x2) + (b2 * y2) + c2;

        if ((r1 != 0) && (r2 != 0) && (same_sign(r1, r2)))
            return null;

        denom = (a1 * b2) - (a2 * b1); //Line segments intersect: compute intersection point.

        if (denom == 0)
            return intersection;

        if (denom < 0) offset = -denom / 2; else offset = denom / 2;

        num = (b1 * c2) - (b2 * c1);
        
        if (num < 0) intersection.setX((num - offset) / denom); 
        
        else intersection.setX( (num + offset) / denom);

        num = (a2 * c1) - (a1 * c2);
        
        if (num < 0)  intersection.setY( ( num - offset) / denom); else intersection.setY( (num + offset) / denom);

        return intersection;
    }

    private boolean same_sign(Double r3, Double r4) {
        if(r3 * r4 > 0) return true;
        return false;
    }
    public static Segment LineToSegment(Line x ){
        
        return new Segment(x.getStartX(), x.getStartY(), x.getEndX(), x.getEndY());
        
    }
    public static Line SegmentToLine(Segment x ){
        
        
        
        Line line = new Line(x.VectorA.getX(), x.VectorA.getY(), x.VectorA.getX()+x.VectorB.getX(), x.VectorA.getY()+x.VectorB.getY());
        line.setStroke(Color.AQUA);
        return line;
        
    }
    @Override
    public String toString(){
      
        
        return VectorA.toString()+" "+VectorB.toString();
    }
}
