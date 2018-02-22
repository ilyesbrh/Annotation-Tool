/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXRippler;
import copytowindowsphotodisplay.Model.Images;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import strongannotationtool.AnnotationPaneController;

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


    ImageViewFXMLController(StackPane NoteStack) {

        this.NoteStack=NoteStack;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        JFXRippler ripple=new JFXRippler(RippleAnchor);
        
        ImageViewPane.getChildren().add(ImageViewPane.getChildren().size()-1,ripple);
        
        // open Project
        RippleAnchor.setOnMouseClicked((event) -> {
            
            try {
                OpenStrongAnnotation();
            } catch (IOException ex) {
                Logger.getLogger(ImageViewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        });
        
        RippleAnchor.setOnMouseEntered((event) -> {
            
            MouseEntred.setStrokeWidth(8);
        });
        RippleAnchor.setOnMouseExited((event) -> {
            
            MouseEntred.setStrokeWidth(0);
        });
    }    
    public void UpdateItem(Images item){
        
        this.image=item;
        
        Name.setText(image.getName());
        
        Selected.setVisible(false);
        
        if(image.isClassified()) Classified.setVisible(true);
        else                     Classified.setVisible(false);
        
        if(image.isLabled()) Labled.setVisible(true);
        else                 Labled.setVisible(false);
        
        Image img=new Image(image.getDir().toURI().toString(),200, 200, false, false, false);
        imageView.setImage(img);
        
    }

    @FXML
    private void ImageInfo(ActionEvent event) {
        
    }

    private void OpenStrongAnnotation() throws IOException {
        
        AnnotationPaneController newController=new AnnotationPaneController();
            
        FXMLLoader Newload=new FXMLLoader(getClass().getResource("/strongannotationtool/AnnotationPane.fxml"));
        Newload.setController(newController);

        AnchorPane load = Newload.load();
        
        newController.setimage(image);
        
        Stage s=new Stage();
        s.setScene(new Scene(load));
        
        s.show();
    }
    
}
