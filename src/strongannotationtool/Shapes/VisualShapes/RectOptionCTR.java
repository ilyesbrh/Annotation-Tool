/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class RectOptionCTR implements Initializable {

    @FXML
    private JFXButton Delete;
    @FXML
    private JFXColorPicker color;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        
    }    

    public void setShape(DrawRectangle aThis) {
        
        Delete.setOnAction((event) -> {
            
            
            aThis.remove(aThis.Parent);
            aThis.OptionPopup.hide();
        
        });
        
        color.valueProperty().addListener((event) -> {
            
            Color value = color.getValue();
            Color newColor = new Color(value.getRed(), value.getGreen(), value.getBlue(), 0.4);
            aThis.setFill(newColor);
            
        });
    }
    public void setShape(DrawCircle aThis) {
        
        Delete.setOnAction((event) -> {
            
            
            aThis.remove(aThis.Parent);
            aThis.OptionPopup.hide();
            
        });
        
        color.valueProperty().addListener((event) -> {
            
            Color value = color.getValue();
            Color newColor = new Color(value.getRed(), value.getGreen(), value.getBlue(), 0.4);
            aThis.setFill(newColor);
            
        });
    }
    
}
