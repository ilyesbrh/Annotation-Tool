/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRippler;
import copytowindowsphotodisplay.Model.Images;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
    private JFXCheckBox Selected;
    @FXML
    private AnchorPane RippleAnchor;
    @FXML
    private AnchorPane ImageViewPane;
    @FXML
    private ImageView imageView;

    private Images image;
    
    private StackPane NoteStack;
    private JFXDialog Jfxdialog;


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
            } catch (URISyntaxException ex) {
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
        
        Name.setText(image.Name.getValue());
        Tooltip tooltip = new Tooltip(image.Name.getValue());
        tooltip.setFont(new Font(16));
        Name.setTooltip(tooltip);
        
        item.selected.bindBidirectional(Selected.selectedProperty());
        
        Classified.visibleProperty().bind(image.Labled);
        
        if(imageView.getImage() != null && imageView.getImage().isBackgroundLoading())
            imageView.getImage().cancel();
        
        Image img=image.getImage(200, 200, false, false, true);
        imageView.setImage(img);
    }

    @FXML
    private void ImageInfo(ActionEvent event) {
        
    }

    private void OpenStrongAnnotation() throws IOException, URISyntaxException {
        
        AnnotationPaneController newController=new AnnotationPaneController();
            
        FXMLLoader Newload=new FXMLLoader(getClass().getResource("/strongannotationtool/AnnotationPane.fxml"));
        Newload.setController(newController);

        AnchorPane load = Newload.load();
        
        newController.setimage(image);
        
        load.prefWidthProperty().bind(NoteStack.widthProperty().subtract(100));
        load.prefHeightProperty().bind(NoteStack.heightProperty().subtract(50));
        
        this.Jfxdialog = new JFXDialog(); 
        this.Jfxdialog.setDialogContainer(NoteStack);
        this.Jfxdialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        this.Jfxdialog.setContent(load);
        this.Jfxdialog.show();
    }
    
}
