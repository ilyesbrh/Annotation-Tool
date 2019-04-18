/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import copytowindowsphotodisplay.Model.Project;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.dom4j.Element;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class MainFXMLController implements Initializable {

    @FXML
    private StackPane DialogPane;
    @FXML
    private AnchorPane MainPane;
    @FXML
    private JFXDrawer Drawer;
    @FXML
    private AnchorPane DrawerPane;
    @FXML
    private AnchorPane ContentPane;

    Project project;
    private JFXDialog Jfxdialog;

    public MainFXMLController(Project project) {
        this.project = project;
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Drawer.setSidePane(DrawerPane);
        Drawer.open();
        Drawer.setOnDrawerClosed((event) -> {
            Drawer.setDisable(true);
            Drawer.setVisible(false);
            
        });
        this.Jfxdialog = new JFXDialog(); 
        this.Jfxdialog.setDialogContainer(DialogPane);
        this.Jfxdialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        ALLImagesINI();
    }    

    private void ALLImagesINI() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AllImagesFXML.fxml"));
        
        AllImagesFXMLController controller = new AllImagesFXMLController(project,DialogPane);

        loader.setController(controller);
        
        Parent load = null;
        try {
            load = (Parent) loader.load();
            AnchorPane.setBottomAnchor(load, 0.0);
            AnchorPane.setLeftAnchor(load, 0.0);
            AnchorPane.setTopAnchor(load, 0.0);
            AnchorPane.setRightAnchor(load, 0.0);

            ContentPane.getChildren().add(load);
        } catch (IOException ex) {
            Logger.getLogger(MainFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    private void ClassesPopUpINI() throws IOException {
        // New Project PopUp INI
        GridPane grid = new GridPane();
        ScrollPane scrollPane=new ScrollPane(grid);
        scrollPane.maxWidthProperty().bind(Jfxdialog.getDialogContainer().widthProperty().subtract(100));
        scrollPane.maxHeightProperty().bind(Jfxdialog.getDialogContainer().heightProperty().subtract(10));
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.Jfxdialog.setContent(scrollPane);

        Integer i=0;
        Integer j=0;
        
        for (Element object : project.getClasses()) {

            ClassesFXMLController newController=new ClassesFXMLController(object,Jfxdialog);

            FXMLLoader Newload=new FXMLLoader(getClass().getResource("ClassesFXML.fxml"));
            Newload.setController(newController);
            AnchorPane load = Newload.load();
            
            grid.add(load, j, i);
            j++;
            if(j==3){
                j=0;
                i++;
            }

        }
    }    

    @FXML
    private void DrawDrawer(MouseEvent event) {
        
        if (Drawer.isHidden()) {
                Drawer.setDisable(false);
                Drawer.setVisible(true);
                Drawer.open();

            } else {
                Drawer.close();
            }
        
    }

    @FXML
    private void ClassesPane(ActionEvent event) throws IOException {
        
        ClassesPopUpINI();
        Jfxdialog.show();
    }

    @FXML
    private void LabledTablePane(ActionEvent event) {
    }
    @FXML
    private void SaveProject(ActionEvent event) {
        project.save();
        Notifications.create()
                        .position(Pos.BOTTOM_RIGHT)
                        .hideAfter(Duration.seconds(3))
                        .title("New")
                        .text("New Project is created")
                        .showInformation();
    }
    private void ExportINI() throws IOException {
        
        ResourceBundle resourceBundle = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                
                return project;
            }

            @Override
            public Enumeration<String> getKeys() {
                return null;
            }
        };
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExportFXML.fxml"));
        loader.setResources(resourceBundle);
        AnchorPane load = loader.load();
        
        Jfxdialog.setContent(load);
    }
    

    @FXML
    private void Export(ActionEvent event) throws IOException {
        
        ExportINI();
        Jfxdialog.show();
        
        
    }

}