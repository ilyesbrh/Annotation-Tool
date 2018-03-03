/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import com.jfoenix.controls.JFXCheckBox;
import copytowindowsphotodisplay.Model.Annotation;
import copytowindowsphotodisplay.Model.CLASS;
import copytowindowsphotodisplay.Model.Images;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class AddShapeToController implements Initializable {

    @FXML
    private VBox vbox;
    
    private Images image;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
    }   
      
    
     public void setImage(Images image ,Shape Athis ) {
         
        this.image=image;
        
        for (CLASS x : image.getProject().CLASSES) {

            JFXCheckBox box = new JFXCheckBox(x.getName());
            
            box.setMinHeight(50);
            box.setPrefHeight(60);
            vbox.getChildren().add(box);
            
            box.setOnAction((event) -> {
                
                if(box.isSelected())
                    CheckIt(x,Athis);
                else
                    UnCheckIt(x,Athis);
            });
        }
    }

    private void CheckIt(CLASS x, Shape Athis) {
        for (Annotation N : x.ANNOTATIONS) {
            
            if(N.getImage().getName().equals(image.getName())){
                
                if(!N.Shapes.contains(Athis))
                    N.Shapes.add(Athis);
                return;
            }
        }
        (new Annotation(x, image)).Shapes.add(Athis);
    }

    private void UnCheckIt(CLASS x, Shape Athis) {
        
        for (Annotation N : x.ANNOTATIONS) {
            
            if(N.getImage().getName().equals(image.getName())){
                
                if(N.Shapes.contains(Athis)){
                    
                    N.Shapes.remove(Athis);
                    return;
                }
            }
        }
        
    }

     
    
}
