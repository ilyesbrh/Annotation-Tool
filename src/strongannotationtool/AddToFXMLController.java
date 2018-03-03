/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import copytowindowsphotodisplay.Model.Annotation;
import copytowindowsphotodisplay.Model.CLASS;
import copytowindowsphotodisplay.Model.Images;
import copytowindowsphotodisplay.NewClassPopUpFXMLController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class AddToFXMLController implements Initializable {

    private Images image;
    @FXML
    private VBox vbox;
    @FXML
    private StackPane notePane;
    private AnnotationPaneController annotation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    public void setAnnotationControler(AnnotationPaneController annot) {
        
        this.image = annot.image.get();
        this.annotation=annot;
        
        for (copytowindowsphotodisplay.Model.CLASS x : image.getProject().CLASSES) {

            JFXCheckBox box = new JFXCheckBox(x.getName());
            
            box.setMinHeight(50);
            box.setPrefHeight(60);

            for (Annotation a : image.ANNOTATIONS) {

                if (a.getClasse().getName().equals(x.getName())) {

                    box.setSelected(true);
                    box.setDisable(true);
                    
                }
            }
            vbox.getChildren().add(box);
            box.setOnAction((event) -> {
                JFXCheckBox source = (JFXCheckBox) event.getSource();
                annot.ClassSelecor.getItems().add(x);
                new Annotation(x, image);
                source.setDisable(true);
            });
        }
    }

    @FXML
    private void AddClass(ActionEvent event) throws IOException {
        NewClassPopUpFXMLController newController=new NewClassPopUpFXMLController(image.getProject());
            
        FXMLLoader Newload=new FXMLLoader(getClass().getResource("/copytowindowsphotodisplay/NewClassPopUpFXML.fxml"));
        Newload.setController(newController);
        newController.setController(this);
        AnchorPane load = Newload.load();
        JFXDialog  NewProject = new JFXDialog(notePane, load, JFXDialog.DialogTransition.CENTER);    
        NewProject.show();
    }
    public void AddClass(CLASS x) {

        JFXCheckBox box = new JFXCheckBox(x.getName());
            
            box.setMinHeight(50);
            box.setPrefHeight(60);

            for (Annotation a : image.ANNOTATIONS) {

                if (a.getClasse().getName().equals(x.getName())) {

                    box.setSelected(true);
                    box.setDisable(true);
                    
                }
            }
            vbox.getChildren().add(box);
            box.setOnAction((event) -> {
                JFXCheckBox source = (JFXCheckBox) event.getSource();
                annotation.ClassSelecor.getItems().add(x);
                new Annotation(x, image);
                source.setDisable(true);
            });
    }

}
