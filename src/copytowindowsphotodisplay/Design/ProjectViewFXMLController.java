/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRippler;
import copytowindowsphotodisplay.Model.Project;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javax.imageio.IIOImage;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class ProjectViewFXMLController implements Initializable {

    @FXML
    private AnchorPane ProjectViewPane;
    @FXML
    private ImageView Imageview;
    @FXML
    private Label Name;
    @FXML
    private AnchorPane RipplePane;

    Project project;
    
    String name;
    
    Image image;
    
    StackPane NoteStack;
    
    public ProjectViewFXMLController(Project project , StackPane NoteStack) throws URISyntaxException {
        
        this.project=project;
        
        this.name=project.Name.getValue();
        
        try {
            this.image = new Image(project.ImagePath.getValue());
            
        } catch (Exception e) {
            this.image = new Image(getClass().getResource("img.JPG").toURI().toString(),true);
        }
        this.NoteStack= NoteStack;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        Name.setText(name);
        
        Imageview.setImage(image);
        Imageview.setClip(new Circle(75, 70, 65));
        
        JFXRippler ripple=new JFXRippler(RipplePane);
        
        ProjectViewPane.getChildren().add(ProjectViewPane.getChildren().size()-1,ripple);
        
        // open Project
        RipplePane.setOnMouseClicked((event) -> {
            
            try {
                infoShow();
            } catch (IOException ex) {
                Logger.getLogger(ProjectViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        });
        
    }    

    
    private void infoShow() throws IOException {
        
        ProjectInfoFXMLController obj=new ProjectInfoFXMLController(project,image);
        
        FXMLLoader loader=new FXMLLoader(getClass().getResource("ProjectInfoFXML.fxml"));
        
        loader.setController(obj);
        
        JFXDialog dialog=new JFXDialog( NoteStack, loader.load(), JFXDialog.DialogTransition.LEFT);
        
        dialog.show();
        
        
    }
    
}
