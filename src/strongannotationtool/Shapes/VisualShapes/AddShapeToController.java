/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import copytowindowsphotodisplay.Model.Project;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class AddShapeToController implements Initializable {

    private Element shape;
    @FXML
    private StackPane notePane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private JFXListView<String> listeView;
    @FXML
    private AnchorPane NewClassPane;
    @FXML
    private JFXTextField NameTextField;
    @FXML
    private Label WarmingLabel;
    @FXML
    private JFXButton CreateBTN;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setElement((DrawableShape) rb.getObject("element"));
        listeView.setCellFactory((param) -> {

            return new CustomJFXListCell(); //To change body of generated lambdas, choose Tools | Templates.
        });

        NameTextField.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.equals("")) {
                CreateBTN.setDisable(true);
            } else {
                CreateBTN.setDisable(false);
            }
        });
    }

    public void setElement(DrawableShape Athis) {

        this.shape = Athis.getElement();
        refresh();

    }

    @FXML
    private void AddTo(ActionEvent event) throws IOException {

        NewClassPane.setVisible(true);

    }

    public boolean AddClass(String x) {

        x = x.toLowerCase().replaceAll("\\s+", "");

        System.out.print("Add CLass");
        
        states();

        return listeView.getItems().add(x);
    }

    void refresh() {
        listeView.getItems().clear();

        HashSet<String> classes = Project.getClasses(shape.getDocument().getRootElement());

        listeView.getItems().addAll(classes);

    }

    private void states() {
        System.out.println("state..");
        for (Element element : shape.elements()) {
            String value = element.attribute(0).getValue();
            System.out.print(" [" + value + "] ");
        }
        System.out.println("");
    }

    public boolean CreateClass(String n) {

        final String name = n.toLowerCase().replaceAll("\\s+", "");

        if(name.isEmpty())
            return false;
        
        List<Element> elements = shape.getDocument().getRootElement().elements().get(1).elements();
        System.out.println(elements);
        elements.removeIf((t) -> {

            Attribute attribute = t.attribute(1);

            return !attribute.getValue().equals(name); 
        });

        if (elements.isEmpty()) {
            Element rootElement = shape.getDocument().getRootElement();
            String id=Project.GenerateId(rootElement,n,0);
            rootElement.element("Classes").addElement("Class").addAttribute("id", id).addAttribute("Name", name);
            
            states();
            return true;
        }
        return false;

    }

    @FXML
    private void CreateClass(ActionEvent event) {

        if (CreateClass(NameTextField.getText())) {

            NewClassPane.setVisible(false);
            
        } else {

            WarmingLabel.setText("Used Class Name");
            WarmingLabel.setVisible(true);

        }
        refresh();
    }

    @FXML
    private void Hide(ActionEvent event) {

        WarmingLabel.setVisible(false);
        NewClassPane.setVisible(false);
    }

    int count = 0;

    private class CustomJFXListCell extends ListCell<String> {

        JFXCheckBox jfxCheckBox;

        public CustomJFXListCell() {

            jfxCheckBox = new JFXCheckBox();

        }

        @Override
        public void updateItem(String id, boolean empty) {
            super.updateItem(id, empty); //To change body of generated methods, choose Tools | Templates.
            
            if(id == null) return;
            String name = Project.getClassName(id,shape);
            if(name == null) return;
            
            if (!empty && !name.isEmpty()) {
                setGraphic(jfxCheckBox);

                jfxCheckBox.setText(name);

                for (Element E : shape.elements()) {
                    if (E.attribute(0).getValue().equals(id)) {

                        jfxCheckBox.selectedProperty().set(true);
                        break;
                    } else {
                        jfxCheckBox.selectedProperty().set(false);
                    }
                }
                jfxCheckBox.setOnAction((newValue) -> {
                    System.out.println(count);
                    count++;
                    if (jfxCheckBox.isSelected()) {
                        List<Element> elements = shape.elements();
                        
                        elements.removeIf((t) -> {
                            
                            Attribute attribute = t.attribute(0);
                            
                            return !attribute.getValue().equals(id); 
                        });
                        
                        if (elements.isEmpty()) {
                            
                            shape.addElement("Class").addAttribute("Name", id);
                            
                            states();
                        }
                    } else {
                        List<Element> elements = shape.elements();
                        for (Element element : elements) {

                            if ((element).attribute(0).getValue().equals(name)) {
                                System.out.println(shape.remove((Element) element)+"Deleted !");
                            }
                        }
                        
                        states();
                    }
                });
            }
        }

    }

}
