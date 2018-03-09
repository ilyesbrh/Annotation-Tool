/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import copytowindowsphotodisplay.Model.Project;
import static copytowindowsphotodisplay.Model.Project.LIST_OF_PROJECTS;
import static copytowindowsphotodisplay.Model.Project.SavingDir;
import static copytowindowsphotodisplay.Model.Project.SavingFile;
import static copytowindowsphotodisplay.Model.Project.saveImageToFile;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    File image;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Name.textProperty().addListener((observable, oldValue, newValue) -> {

            if (image == null) {
                Demand.setDisable(true);
                return;
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
            
        });
    }

    @FXML
    private void Demande(ActionEvent event) throws IOException {

        String name = Name.getText();
        boolean autoSave = AutoSave.isSelected();
        boolean saveImages = SaveImages.isSelected();

        File projectDir = SavingDir(Directory, name);

        File path = SavingFile(projectDir, name + ".xml");

        File imagesDir = SavingDir(projectDir, "Images");

        File imagePath = saveImageToFile(new Image(image.toURI().toString()), projectDir, name);

        try {
            new Project(name, path, imagePath, autoSave, saveImages, imagesDir);
        } catch (Exception ex) {
            System.err.println("cant be created");
        }
    }

    @FXML
    private void directoryChooser(ActionEvent event) {
        DirectoryChooser cs = new DirectoryChooser();
        Directory = cs.showDialog(new Stage());
    }

    @FXML
    private void imageChooser(ActionEvent event) {

        image = new FileChooser().showOpenDialog(new Stage());
        Imageview.setImage(new Image(image.toURI().toString()));
    }

    @FXML
    private void Cancel(ActionEvent event) {
    }

}
