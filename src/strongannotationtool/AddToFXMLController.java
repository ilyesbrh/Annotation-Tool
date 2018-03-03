/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool;

import com.jfoenix.controls.JFXCheckBox;
import copytowindowsphotodisplay.Model.Annotation;
import copytowindowsphotodisplay.Model.Images;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
    }

    public void setAnnotationControler(AnnotationPaneController annot) {
        
        this.image = annot.image.get();

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
                
            });
        }
    }

}
