/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXRippler;
import copytowindowsphotodisplay.Model.Images;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class ImageViewFXMLController implements Initializable {

    @FXML
    private Rectangle MouseEntred;
    @FXML
    private FontAwesomeIconView Classified;
    @FXML
    private FontAwesomeIconView Labled;
    @FXML
    private Label Name;
    @FXML
    private FontAwesomeIconView Selected;
    @FXML
    private AnchorPane RippleAnchor;
    @FXML
    private AnchorPane ImageViewPane;
    @FXML
    private ImageView imageView;

    private Images image;
    
    private StackPane NoteStack;

    ImageViewFXMLController(Images item, StackPane NoteStack) {
        
        this.NoteStack=NoteStack;
        this.image=item;
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Name.setText(image.getName());
        
        Selected.setVisible(false);
        
        if(image.isClassified()) Classified.setVisible(true);
        else                     Classified.setVisible(false);
        
        if(image.isLabled()) Labled.setVisible(true);
        else                 Labled.setVisible(false);
        
        Image img=new Image(image.getDir().toURI().toString(),100, 100, false, true, true);
        imageView.setImage(img);
        
        
        
        JFXRippler ripple=new JFXRippler(RippleAnchor);
        
        ImageViewPane.getChildren().add(ImageViewPane.getChildren().size()-1,ripple);
        
        // open Project
        RippleAnchor.setOnMouseClicked((event) -> {
            
        OpenStrongAnnotation();
            
            
        });
        
        RippleAnchor.setOnMouseEntered((event) -> {
            
            MouseEntred.setStrokeWidth(8);
        });
        RippleAnchor.setOnMouseExited((event) -> {
            
            MouseEntred.setStrokeWidth(0);
        });
    }    

    @FXML
    private void ImageInfo(ActionEvent event) {
        
    }

    private void OpenStrongAnnotation() {
        
    }
    
}
