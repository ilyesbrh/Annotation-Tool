/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXDialog;
import copytowindowsphotodisplay.Model.Images;
import copytowindowsphotodisplay.Model.Project;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class AllImagesFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    GridView<Images> ImagesGrid;
    
    Project project;
    StackPane DialogPane;
    
    
    public AllImagesFXMLController(Project project, StackPane dialogPane) {
        this.project = project;
        DialogPane= dialogPane;
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        GridViewINI(ImagesGrid);

    }    

    @FXML
    private void NewImages(ActionEvent event) {
        
        try {
            // New Project PopUp INI
            NewImagesFXMLController newController=new NewImagesFXMLController(project);
            
            FXMLLoader Newload=new FXMLLoader(getClass().getResource("NewImagesFXML.fxml"));
            Newload.setController(newController);
            
            AnchorPane load = Newload.load();
            JFXDialog  NewProject = new JFXDialog(DialogPane, load, JFXDialog.DialogTransition.CENTER);    
            
            NewProject.show();
            
        } catch (IOException ex) {
            Logger.getLogger(ProjectUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void GridViewINI(GridView<Images> Gridview) {
        
        Gridview.setItems(project.IMAGES);
        Gridview.setVerticalCellSpacing(20);
        Gridview.setHorizontalCellSpacing(20);
        Gridview.setCellHeight(200);
        Gridview.setCellWidth(200);
        Gridview.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        Gridview.setCellFactory(new Callback<GridView<Images>, GridCell<Images>>() {
            
            @Override public GridCell<Images> call(GridView<Images> arg0) {
                
                return new cell(Gridview,DialogPane);
            }
        });
    }
    public class cell extends GridCell<Images>{

        private final GridView<Images> Grid;

        private final StackPane NoteStack;
        
        private cell(GridView<Images> arg0 ,StackPane NoteStack) {
            
            Grid=arg0;
            this.NoteStack = NoteStack;
        }

        @Override
        protected void updateItem(Images item, boolean empty) {
            
            try {
                
                if(empty==true) return;
                
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                
                
                FXMLLoader loader=new FXMLLoader(getClass().getResource("ImageViewFXML.fxml"));
                
                ImageViewFXMLController controller =new  ImageViewFXMLController( item, NoteStack);
                
                loader.setController(controller);
                
                AnchorPane root = loader.load();
                
                /*
                root.setPrefHeight(Grid.getCellHeight());
                root.setPrefWidth(Grid.getCellWidth());
                */
                
                setGraphic(root);
                
            } catch (IOException ex) {
            }
        }

        
    }
    
}
