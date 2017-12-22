/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import copytowindowsphotodisplay.Model.Project;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class NewFXMLController implements Initializable {

    @FXML
    private JFXToggleButton AutoSave;
    @FXML
    private JFXToggleButton SaveImages;
    @FXML
    private JFXTextField Name;
    @FXML
    private ImageView Imageview;
    
    File Directory;

    File Image;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Demande(ActionEvent event) throws IOException {
        
        if(Image ==null) return;
        
        if(Directory ==null) return;
        
        if(Name.getText().equals("") ) return;
        
        for(File f:Directory.listFiles()) {
            
            if(Name.getText().equals(f.getName()) ) return;
            
        }
        
        new Project(Name.getText(), new Image(Image.toURI().toString()), Directory,AutoSave.isSelected(),SaveImages.isSelected());
        
    }

    @FXML
    private void directoryChooser(ActionEvent event) {
        DirectoryChooser cs=new DirectoryChooser();
        Directory=cs.showDialog(new Stage());
    }

    @FXML
    private void imageChooser(ActionEvent event) {
        
        Image = new FileChooser().showOpenDialog(new Stage());
        Imageview.setImage(new Image(Image.toURI().toString()));
    }

    @FXML
    private void Cancel(ActionEvent event) {
    }
    
}
