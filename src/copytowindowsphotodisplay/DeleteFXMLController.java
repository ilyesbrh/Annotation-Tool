/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import copytowindowsphotodisplay.Model.Images;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.controlsfx.control.GridView;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class DeleteFXMLController implements Initializable {

    @FXML
    private JFXButton yes;
    @FXML
    private JFXButton no;
    @FXML
    private JFXCheckBox delete;
    @FXML
    private Label number;

    GridView<Images> ImagesGrid;
    ArrayList<Images> list=new ArrayList<>();
    JFXDialog jfxD;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ImagesGrid = (GridView<Images>) rb.getObject("grid");
        jfxD = (JFXDialog) rb.getObject("popup");
        
        for (Images item : ImagesGrid.getItems()) {
            
            if(item.selected.getValue())
                list.add(item);
        }
        number.setText(Integer.toString(list.size()));
    }    

    @FXML
    private void yes(ActionEvent event) throws URISyntaxException {
        
        for (Images object : list) {
            
            if(object.selected.getValue()){
                
                ImagesGrid.getItems().remove(object);
                object.remove(delete.isSelected());
            }
        }
        jfxD.close();
        for (Images item : ImagesGrid.getItems()) {
            item.UnSelect();
        }
    }

    @FXML
    private void no(ActionEvent event) {
        
        jfxD.close();
    }
    
}
