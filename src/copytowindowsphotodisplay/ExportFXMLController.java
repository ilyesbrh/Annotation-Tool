/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import copytowindowsphotodisplay.Model.Project;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class ExportFXMLController implements Initializable {

    private Project project;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        this.project= (Project) rb.getObject("");
        // TODO
        
    }    

    @FXML
    private void Csv(ActionEvent event) {
        
        FileChooser chooser= new FileChooser();
        
        project.SaveAs(chooser.showSaveDialog(new Stage()),"CSV");
        
        
    }

    @FXML
    private void Xml(ActionEvent event) {
        
        FileChooser chooser= new FileChooser();
        
        project.SaveAs(chooser.showSaveDialog(new Stage()),"XML");
        
    }
    
}
