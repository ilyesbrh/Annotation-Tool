/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXListCell;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import copytowindowsphotodisplay.Model.Class;
import copytowindowsphotodisplay.Model.Project;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;

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
    
    /**
     * Initializes the controller class.
     */
    
    public ClassesFXMLController(Project p) {
        
        this.project=p;
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ImagesListView.setItems(project.CLASSES);
        ImagesListView.setCellHorizontalMargin(10.0);
    }    

    @FXML
    private void showpop(MouseEvent event) {
        Class selectedItem = ImagesListView.getSelectionModel().getSelectedItem();
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
        Name.setEditable(true);
    }

    @FXML
    private void AddClass(ActionEvent event) {
        
    }
    
}
