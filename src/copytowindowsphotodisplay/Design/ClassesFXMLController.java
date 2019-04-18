/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import copytowindowsphotodisplay.Model.Project;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.dom4j.Attribute;
import org.dom4j.Element;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class ClassesFXMLController implements Initializable {

    @FXML
    private Label imageNumber;
    @FXML
    private Label Name;
    @FXML
    private FontAwesomeIconView icon;
    @FXML
    private JFXButton Edit;
    @FXML
    private JFXButton Delete;
    @FXML
    private JFXTextField EditTXT;
    @FXML
    private Label Used;
    
    private final Element Class;
    private final JFXDialog notePane;

    /**
     * Initializes the controller class.
     * @param element
     * @param notePane
     */
    
    public ClassesFXMLController(Element element,JFXDialog notePane) {
        
        this.notePane=notePane;
        this.Class=element;
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //imageNumber.setText(Project.getImagesNumberInClass(Class).toString());
        Name.setText(Class.attribute(1).getValue());
    }    

    private void showpop(MouseEvent event) {
        
    }

    @FXML
    private void EditClassName(ActionEvent event) {
        
        if(!icon.getGlyphName().equals("CHECK_SQUARE_ALT")){
            JFXButton btn = (JFXButton) event.getSource();
            btn.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            icon.setGlyphName("CHECK_SQUARE_ALT");
            EditTXT.setVisible(true);
        }else{
            if(!RenameClass(EditTXT.getText())){
                
                Used.setVisible(true);
                return;
            }
            
            JFXButton btn = (JFXButton) event.getSource();
            btn.setBackground(null);
            icon.setGlyphName("EDIT");
            Used.setVisible(false);
            Name.setText(Class.attribute(1).getValue());
            EditTXT.setVisible(false);
            EditTXT.setText("");
        }
    }
    public boolean RenameClass(String n) {

        final String name = n.toLowerCase().replaceAll("\\s+", "");

        if(name.isEmpty())
            return false;
        
        List<Element> elements = Class.getDocument().getRootElement().elements().get(1).elements();
        System.out.println(elements);
        elements.removeIf((t) -> {

            Attribute attribute = t.attribute(1);

            return !attribute.getValue().equals(name); 
        });

        if (elements.isEmpty()) {
            Class.attributes().get(1).setValue(name);
            
            return true;
        }
        return false;

    }

}
