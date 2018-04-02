/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import copytowindowsphotodisplay.Model.Project;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;
import org.dom4j.Element;

/**
 * FXML Controller class
 *
 * @author brhil
 */
public class NewImagesFXMLController implements Initializable {

    
    /**
     * Initializes the controller class.
     */
    Project project;
    GridView<Element> ImagesGrid;
            
    public NewImagesFXMLController(Project project, AllImagesFXMLController gridImages) {
        
        ImagesGrid = gridImages.ImagesGrid;
       this.project=project;
    }    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void DirOpen(ActionEvent event) throws FileNotFoundException, IOException {
        
        List<File> openDirectoryImagesChooser = openDirectoryImagesChooser();
        for (File file : openDirectoryImagesChooser) {

            ImagesGrid.getItems().add(project.AddImage(file));

        }

        
    }

    @FXML
    private void ImagesOpen(ActionEvent event) throws IOException {
        
        List<File> openImagesChooser = OpenImagesChooser();
        if(openImagesChooser== null) return;
        for (File file : openImagesChooser) {
            
            ImagesGrid.getItems().add(project.AddImage(file));
            
        }
        
    }
    public static List<File> openDirectoryImagesChooser() throws FileNotFoundException {
        
        DirectoryChooser directoryChooser = new DirectoryChooser();
        
        File showDialog = directoryChooser.showDialog(new Stage());

        if (showDialog.canRead() && showDialog.isDirectory()) {

            File[] listFiles = showDialog.listFiles((File pathname) -> {

                String name = pathname.getName();
                return name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpeg");
            });
            
            return Arrays.asList(listFiles);
        }
        
        return null;
    }
    public static List<File> OpenImagesChooser() {

        List<File> L;
        Stage stage = new Stage();
        stage.setAlwaysOnTop(true);
        FileChooser fileChooser;
        // filechooser
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("image filter", "*.JPG", "*.PNG", "*.JPEG");
        fileChooser.getExtensionFilters().add(extFilter);
        L = fileChooser.showOpenMultipleDialog(stage);
        return L;

    }
    
}
