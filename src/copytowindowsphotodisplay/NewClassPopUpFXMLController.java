/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import copytowindowsphotodisplay.Model.Project;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class NewClassPopUpFXMLController implements Initializable {

    @FXML
    private JFXTextField ClassNameField;
    @FXML
    private JFXButton BTN;
    @FXML
    private FontAwesomeIconView glyp;

    private final Project project;
    
    /**
     * Initializes the controller class.
     */
    
    public NewClassPopUpFXMLController(Project project) {
        this.project=project;
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ClassNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            
            boolean equals=false;
                     
            for (copytowindowsphotodisplay.Model.CLASS C : project.CLASSES) {
                
                if(C.getName().equals(newValue))
                equals = true;
            }
            
            if(!newValue.equals("") && !equals){
                
                BTN.setDisable(false);
                BTN.setStyle("-fx-background-color: #a8d0e7;");
                glyp.setGlyphName("CHECK_SQUARE_ALT");
            }else{
                
                BTN.setDisable(true);
                BTN.setStyle("-fx-background-color: white;");
                glyp.setGlyphName("CLOSE");
            }
            
        });
    }    

    @FXML
    private void addClass(ActionEvent event) {
        
        new copytowindowsphotodisplay.Model.CLASS(ClassNameField.getText(), project);
        
    }
    
}
