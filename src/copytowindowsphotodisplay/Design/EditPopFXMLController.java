/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class EditPopFXMLController implements Initializable {

    @FXML
    private JFXButton demande;
    @FXML
    private JFXToggleButton Enfant;
    @FXML
    private JFXToggleButton Fumeur;
    @FXML
    private FontAwesomeIconView NoteIcon;
    @FXML
    private JFXButton demande1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Demande(ActionEvent event) {
    }
    
}
