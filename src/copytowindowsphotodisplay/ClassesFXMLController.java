/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import copytowindowsphotodisplay.Model.Class;
import copytowindowsphotodisplay.Model.Project;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class ClassesFXMLController implements Initializable {

    @FXML
    private JFXListView<copytowindowsphotodisplay.Model.Class> ImagesListView;
    @FXML
    private Label imageNumber;
    @FXML
    private Label LabledNumber;
    @FXML
    private ProgressIndicator Progress;
    @FXML
    private JFXTextField Name;
    private final Project project;
    @FXML
    private FontAwesomeIconView Icon;
    
    private Class selectedItem;
    private final StackPane notePane;
    
    /**
     * Initializes the controller class.
     * @param projet
     */
    
    public ClassesFXMLController(Project projet,StackPane notePane) {
        
        this.notePane=notePane;
        this.project=projet;
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImagesListView.setItems(project.CLASSES);
        ImagesListView.setCellHorizontalMargin(10.0);
    }    

    @FXML
    private void showpop(MouseEvent event) {
        selectedItem = ImagesListView.getSelectionModel().getSelectedItem();
        Name.setText(selectedItem.toString());
        imageNumber.setText(Double.toString(selectedItem.IMAGES.size()));
        LabledNumber.setText(Double.toString(selectedItem.getLabledNumber()));
        try {
            
            Double progress = selectedItem.IMAGES.size() / selectedItem.getLabledNumber().doubleValue();
            Progress.setProgress(progress);
        } catch (Exception e) {
            Progress.setProgress(0.0);
        }
    }

    @FXML
    private void EditClassName(ActionEvent event) {
        
        if(Icon.getGlyphName().equals("CHECK_SQUARE_ALT")){
            
            Icon.setGlyphName("EDIT");
            Name.setEditable(false);
            selectedItem.setName(Name.getText());
            
        }else{
            Icon.setGlyphName("CHECK_SQUARE_ALT");
            Name.setEditable(true);
        }
        
    }

    @FXML
    private void AddClass(ActionEvent event) throws IOException {
        
        NewClassPopUpFXMLController newController=new NewClassPopUpFXMLController(project);
            
        FXMLLoader Newload=new FXMLLoader(getClass().getResource("NewClassPopUpFXML.fxml"));
        Newload.setController(newController);

        AnchorPane load = Newload.load();
        JFXDialog  NewProject = new JFXDialog(notePane, load, JFXDialog.DialogTransition.CENTER);    
        NewProject.show();
    }
    
}
