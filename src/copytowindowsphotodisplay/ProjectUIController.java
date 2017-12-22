/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXDialog;
import copytowindowsphotodisplay.Model.Project;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;

/**
 *
 * @author DaMi
 */
public class ProjectUIController implements Initializable {
    
    @FXML
    private AnchorPane MainPane;
    @FXML
    private StackPane NoteStackPane;
    @FXML
    private GridView<Project> Gridview;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
            // Binding NotePane and Main Anchor
            MainPane.prefWidthProperty().bind(NoteStackPane.widthProperty());
            MainPane.prefHeightProperty().bind(NoteStackPane.heightProperty());
        
            GridViewINI(Gridview);
            
    }    

    @FXML
    private void NewProject(ActionEvent event) {
        
        try {
            // New Project PopUp INI
            NewFXMLController newController=new NewFXMLController();
            
            FXMLLoader Newload=new FXMLLoader(getClass().getResource("NewFXML.fxml"));
            Newload.setController(newController);
            
            AnchorPane load = Newload.load();
            JFXDialog  NewProject = new JFXDialog(NoteStackPane, load, JFXDialog.DialogTransition.CENTER);    
            
            NewProject.show();
            
        } catch (IOException ex) {
            Logger.getLogger(ProjectUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void Open(ActionEvent event) throws IOException {
        
        DirectoryChooser choser=new DirectoryChooser();
        
        File URL = choser.showDialog(new Stage());
        
        Project.LoadProject(URL);
        
        
    }

    @FXML
    private void Settings(ActionEvent event) {
        
    }

    @FXML
    private void Help(ActionEvent event) {
        
    }

    private void GridViewINI(GridView<Project> Gridview) {
        
        Gridview.setItems(Project.LIST_OF_PROJECTS);
        Gridview.setVerticalCellSpacing(20);
        Gridview.setHorizontalCellSpacing(20);
        Gridview.setCellHeight(200);
        Gridview.setCellWidth(200);
        
        Gridview.setCellFactory(new Callback<GridView<Project>, GridCell<Project>>() {
            
            @Override public GridCell<Project> call(GridView<Project> arg0) {
                
                return new cell(Gridview,NoteStackPane);
            }
        });
        
    }
    public class cell extends GridCell<Project>{

        private final GridView<Project> Grid;

        private final StackPane NoteStack;
        
        private cell(GridView<Project> arg0 ,StackPane NoteStack) {
            
            Grid=arg0;
            this.NoteStack = NoteStack;
        }

        @Override
        protected void updateItem(Project item, boolean empty) {
            
            try {
                
                if(empty==true) return;
                
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                
                
                FXMLLoader loader=new FXMLLoader(getClass().getResource("ProjectViewFXML.fxml"));
                
                ProjectViewFXMLController controller =new  ProjectViewFXMLController( item, NoteStack);
                
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
