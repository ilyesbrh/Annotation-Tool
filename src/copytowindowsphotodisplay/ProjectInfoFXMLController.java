/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import copytowindowsphotodisplay.Model.Project;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class ProjectInfoFXMLController implements Initializable {

    @FXML
    private ImageView imageview;
    @FXML
    private Label name;
    @FXML
    private Label classNumber;
    @FXML
    private Label imageNumber;
    @FXML
    private Label classifiedNumber;
    @FXML
    private Label labledNumber;
    @FXML
    private ProgressIndicator Progress;

    private final Image image;

    Project project;

    ProjectInfoFXMLController(Project project, Image image) {

        this.project = project;

        this.image = image;

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imageview.setImage(image);
        name.setText(project.Name.getValue());
        classNumber.setText(project.getClassNumber().toString());
        classifiedNumber.setText(project.getClassifiedNumber().toString());
        labledNumber.setText(project.getLabledNumber().toString());
        imageNumber.setText(project.getImagesNumber().toString());
        // image number div labled images = progress
        if(project.getLabledNumber()==0) Progress.setProgress(0);
        else Progress.setProgress(project.getLabledNumber().doubleValue()/project.getImagesNumber().doubleValue());

    }

    @FXML
    private void Setting(ActionEvent event) {
    }

    @FXML
    private void Open(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFXML.fxml"));

        MainFXMLController controller = new MainFXMLController(project);

        loader.setController(controller);

        Stage s = new Stage();
        try {
            Parent load = loader.load();
            s.setScene(new Scene(load));

        } catch (IOException ex) {
        }

        s.show();

    }

}
