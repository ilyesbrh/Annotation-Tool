/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import copytowindowsphotodisplay.Model.Project;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class AllImagesFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Project project;

    public AllImagesFXMLController(Project project) {
        this.project = project;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
