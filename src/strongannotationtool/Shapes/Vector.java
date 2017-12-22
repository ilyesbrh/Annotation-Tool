/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes;

/**
 *
 * @author brhil
 */
public class Vector {
    
    private Double x;
    private Double y;

    public Vector(Double x,Double y){
        
        this.x=x;
        this.y=y;
        
    }
    
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
    
    public Vector add( Vector vector )
    {
            return new Vector(this.x += vector.x, this.y += vector.y);
    }

    public Vector subtract( Vector vector  )
    {
            return new Vector(this.x -= vector.x, this.y -= vector.y);
    }

    public Vector multiply ( Double multiplier )
    {
            return new Vector(this.x *= multiplier, this.y *= multiplier);
    }

    public Double length ()
    {
            return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public Double cross ( Vector vector  )
    {
            return this.x * vector.y - this.y * vector.x;
    }

    public Double dot ( Vector vector  )
    {
            return this.x * vector.x + this.y * vector.y;
    }
    public String toString(){
        
        return x.toString() +" " + y.toString();
    }
    
}
