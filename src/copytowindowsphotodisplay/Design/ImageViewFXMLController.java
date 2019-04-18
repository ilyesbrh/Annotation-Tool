/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXToggleButton;
import copytowindowsphotodisplay.Model.Images;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import org.controlsfx.control.GridView;
import strongannotationtool.Design.AnnotationPaneController;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class ImageViewFXMLController implements Initializable {

    private Rectangle MouseEntred;
    @FXML
    private FontAwesomeIconView Labled;
    @FXML
    private Label Name;
    @FXML
    private JFXToggleButton Selected;
    @FXML
    private AnchorPane RippleAnchor;
    @FXML
    private AnchorPane ImageViewPane;
    @FXML
    private ImageView imageView;

    @FXML
    private JFXButton Details;

    private Images image;
    private JFXDialog Jfxdialog;
    private StackPane NoteStack;
    private final GridView<Images> grid;

    ImageViewFXMLController(StackPane NoteStack ,GridView<Images> grid ) {

        this.NoteStack=NoteStack;
        this.grid = grid;
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
        
    }    
    public void UpdateItem(Images item){
        
        this.image=item;
        
        Name.setText(image.Name.getValue());
        Tooltip tooltip = new Tooltip(image.Name.getValue());
        tooltip.setFont(new Font(16));
        Name.setTooltip(tooltip);
        
        item.selected.bindBidirectional(Selected.selectedProperty());
        
        Labled.setVisible(image.isLabled());
        
        if(imageView.getImage() != null && imageView.getImage().isBackgroundLoading())
            imageView.getImage().cancel();
        
        Image img=image.getImage(250, 250, false, true, true);
        imageView.setImage(img);
    }


    private void OpenStrongAnnotation() throws IOException, URISyntaxException {
        if(Jfxdialog == null){
            this.Jfxdialog = new JFXDialog(); 
            this.Jfxdialog.setDialogContainer(NoteStack);
            this.Jfxdialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        }
        AnnotationPaneController newController=new AnnotationPaneController(Jfxdialog,grid);
            
        FXMLLoader Newload=new FXMLLoader(getClass().getResource("/strongannotationtool/Design/AnnotationPane.fxml"));
        Newload.setController(newController);

        AnchorPane load = Newload.load();
        
        newController.setimage(image);
        
        load.prefWidthProperty().bind(NoteStack.widthProperty().subtract(100));
        load.prefHeightProperty().bind(NoteStack.heightProperty().subtract(50));
        this.Jfxdialog.setContent(load);
        this.Jfxdialog.show();
    }
    
}
