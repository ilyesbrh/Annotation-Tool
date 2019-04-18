
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXToggleButton;
import copytowindowsphotodisplay.Model.Images;
import copytowindowsphotodisplay.Model.Project;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
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
    public GridView<Images> ImagesGrid;

    public Project project;
    public StackPane DialogPane;
    @FXML
    private JFXToggleButton LabledCheckBox;

    public AllImagesFXMLController(Project project, StackPane dialogPane) {
        this.project = project;
        DialogPane = dialogPane;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        GridViewINI(ImagesGrid);

    }

    @FXML
    private void NewImages(ActionEvent event) {

        try {
            // New Project PopUp INI
            NewImagesFXMLController newController = new NewImagesFXMLController(project, this);

            FXMLLoader Newload = new FXMLLoader(getClass().getResource("NewImagesFXML.fxml"));
            Newload.setController(newController);

            AnchorPane load = Newload.load();
            JFXDialog NewProject = new JFXDialog(DialogPane, load, JFXDialog.DialogTransition.CENTER);

            NewProject.show();

        } catch (IOException ex) {
            Logger.getLogger(ProjectUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void GridViewINI(GridView<Images> Gridview) {

        Gridview.setCacheHint(CacheHint.SPEED);
        Gridview.setItems(FXCollections.observableArrayList(project.getImages()));
        Gridview.setVerticalCellSpacing(10);
        Gridview.setHorizontalCellSpacing(10);
        Gridview.setCellHeight(250);
        Gridview.setCellWidth(250);
        Gridview.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        Gridview.setCellFactory((GridView<Images> arg0) -> {
            cell cell = new cell(Gridview, DialogPane);
            cell.setCacheHint(CacheHint.SPEED);
            return cell;
        });
        
    }

    @FXML
    private void Delete(ActionEvent event) throws IOException {
        
        JFXDialog Jfxdialog = new JFXDialog(); 
        ResourceBundle resourceBundle = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                
                if(key.equals("popup"))
                    return Jfxdialog;
                if(key.equals("grid"))
                    return ImagesGrid;
                return null;
            }

            @Override
            public Enumeration<String> getKeys() {
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DeleteFXML.fxml"));
        loader.setResources(resourceBundle);
        AnchorPane load = loader.load();
        
        Jfxdialog.setDialogContainer(DialogPane);
        Jfxdialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        Jfxdialog.setContent(load);
        Jfxdialog.show();
    }

    @FXML
    private void SelectAll(ActionEvent event) {
        JFXButton source = (JFXButton) event.getSource();

        if (!source.getStyle().isEmpty()) {
            source.setStyle("");
            source.setTextFill(Color.GREEN);
            for (Images item : ImagesGrid.getItems()) {
                
                item.UnSelect();
            }
        } else {
            source.setStyle("-fx-background-color : #96ceb4;");
            source.setTextFill(Color.WHITE);
            for (Images item : ImagesGrid.getItems()) {
                
                item.select();
            }
        }
    }

    public class cell extends GridCell<Images> {

        private final GridView<Images> Grid;
        private ImageViewFXMLController controller;

        private cell(GridView<Images> arg0, StackPane NoteStack) {

            Grid = arg0;

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("ImageViewFXML.fxml"));

                controller = new ImageViewFXMLController(NoteStack,arg0);

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

        @Override
        protected void updateItem(Images item, boolean empty) {

            try {

                if (empty == true) {
                    return;
                }

                //imageElement.selectNodes("/Shape/Class").isEmpty();
                if (!LabledCheckBox.isSelected() && !item.isLabled()) {
                    return;
                }

                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                controller.UpdateItem(item);

                /*
                root.setPrefHeight(Grid.getCellHeight());
                root.setPrefWidth(Grid.getCellWidth());
                 */
            } catch (Exception ex) {
            }
        }

    }

}