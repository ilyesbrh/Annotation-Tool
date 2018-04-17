/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import copytowindowsphotodisplay.Model.Project;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import org.dom4j.Element;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class ClassesFXMLController implements Initializable {

    private JFXListView<String> ImagesListView;
    @FXML
    private Label imageNumber;
    private ProgressIndicator Progress;
    @FXML
    private Label Name;
    @FXML
    private FontAwesomeIconView Icon;
    @FXML
    private JFXButton Edit;
    @FXML
    private JFXButton Close;
    @FXML
    private FontAwesomeIconView Icon1;
    @FXML
    private JFXButton Delete;
    @FXML
    private FontAwesomeIconView Icon11;
    
    
    private final Element Class;
    private final JFXDialog notePane;

    
    /**
     * Initializes the controller class.
     * @param projet
     */
    
    public ClassesFXMLController(Element element,JFXDialog notePane) {
        
        this.notePane=notePane;
        this.Class=element;
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        imageNumber.setText(Project.getImagesNumberInClass(Class).toString());
        Name.setText(Class.attribute(1).getValue());
    }    

    private void showpop(MouseEvent event) {
        
    }

    @FXML
    private void EditClassName(ActionEvent event) {
        
        if(Icon.getGlyphName().equals("CHECK_SQUARE_ALT")){
            JFXButton btn = (JFXButton) event.getSource();
            btn.setBackground(null);
            Icon.setGlyphName("EDIT");
            //selectedItem.setName(Name.getText());
            //File classified = selectedItem.getProject().getClassifiedDir();
            //File file = new File(classified, Name.getText());
            //selectedItem.getClassDir().renameTo(file);
            
        }else{
            JFXButton btn = (JFXButton) event.getSource();
            btn.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            Icon.setGlyphName("CHECK_SQUARE_ALT");
        }
        
    }

}
