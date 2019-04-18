/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import copytowindowsphotodisplay.Model.Project;
import static copytowindowsphotodisplay.Model.Project.LIST_OF_PROJECTS;
import static copytowindowsphotodisplay.Model.Project.SavingDir;
import static copytowindowsphotodisplay.Model.Project.SavingFile;
import static copytowindowsphotodisplay.Model.Project.saveImageToFile;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class NewFXMLController implements Initializable {

    @FXML
    private JFXToggleButton AutoSave;
    @FXML
    private JFXToggleButton SaveImages;
    @FXML
    private JFXTextField Name;
    @FXML
    private ImageView Imageview;
    @FXML
    private JFXButton Demand;

    File Directory;

    private JFXDialog JfxDialog;
    private Thread Transformation;

    public NewFXMLController() throws URISyntaxException{

    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            Imageview.setImage(new Image(getClass().getResource("image.png").toURI().toString()));
            
            JfxDialog = (JFXDialog) rb.getObject("p");
            
            Transformation =  (Thread) rb.getObject("t");
            
            
            Name.textProperty().addListener((observable, oldValue, newValue) -> {
                
                new Thread(() -> {
                    
                    if (Directory == null) {
                        Demand.setDisable(true);
                        return;
                    }
                    
                    if (Name.getText().equals("")) {
                        Demand.setDisable(true);
                        return;
                    }
                    
                    for (File f : Directory.listFiles()) {
                        
                        if (Name.getText().equals(f.getName())) {
                            Demand.setDisable(true);
                            return;
                        }
                        
                    }
                    for (Project p : LIST_OF_PROJECTS) {
                        
                        if (Name.getText().equals(p.Name.getValue())) {
                            
                            Demand.setDisable(true);
                            return;
                        }
                    }
                    Demand.setDisable(false);
                }).run();
                
            });
        } catch (URISyntaxException ex) {
            Logger.getLogger(NewFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Demande(ActionEvent event) throws IOException {

        String name = Name.getText();

        File projectDir = SavingDir(Directory, name);

        File path = SavingFile(projectDir, name + ".xml");
        
        File imagesDir = SavingDir(projectDir, "Images");

        File imagePath = saveImageToFile(Imageview.getImage(), projectDir, name);

        try {
            boolean autoSave = AutoSave.isSelected();
            boolean saveImages = SaveImages.isSelected();
            
            new Project(name, path, imagePath, true, true, imagesDir);
            JfxDialog.close();
            Transformation.run();
        } catch (Exception ex) {
            System.err.println("cant be created");
        }
    }

    @FXML
    private void directoryChooser(ActionEvent event) {
        DirectoryChooser cs = new DirectoryChooser();
        Directory = cs.showDialog(new Stage());
        
            if (Directory == null) {
                Demand.setDisable(true);
                return;
            }

            if (Name.getText().equals("")) {
                Demand.setDisable(true);
                return;
            }

            for (File f : Directory.listFiles()) {

                if (Name.getText().equals(f.getName())) {
                    Demand.setDisable(true);
                    return;
                }

            }
            for (Project p : LIST_OF_PROJECTS) {

                if (Name.getText().equals(p.Name.getValue())) {

                    Demand.setDisable(true);
                    return;
                }
            }
            Demand.setDisable(false);
    }

    @FXML
    private void imageChooser(MouseEvent event) {
        
        try {
            Imageview.setImage(new Image(new FileChooser().showOpenDialog(new Stage()).toURI().toString()));
        } catch (Exception e) {
            System.out.println("Canceled !");
        }
            if (Directory == null) {
                Demand.setDisable(true);
                return;
            }

            if (Name.getText().equals("")) {
                Demand.setDisable(true);
                return;
            }

            for (File f : Directory.listFiles()) {

                if (Name.getText().equals(f.getName())) {
                    Demand.setDisable(true);
                    return;
                }

            }
            for (Project p : LIST_OF_PROJECTS) {

                if (Name.getText().equals(p.Name.getValue())) {

                    Demand.setDisable(true);
                    return;
                }
            }
            Demand.setDisable(false);
    }

    @FXML
    private void Cancel(ActionEvent event) {
        JfxDialog.close();
    }

}
