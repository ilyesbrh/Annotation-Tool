/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author ilies
 */
public interface DrawableShape {
    
    abstract public void AddTo(AnchorPane p);
    abstract public void remove(AnchorPane p);
    abstract public void MoveTo(double deltaX, double deltaY);
    abstract public void ScaleTo(double xK,double yK);
    
}

