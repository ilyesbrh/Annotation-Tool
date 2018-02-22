/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPopup;
import strongannotationtool.Shapes.CustomGrid;
import copytowindowsphotodisplay.Model.Annotation;
import copytowindowsphotodisplay.Model.Images;
import copytowindowsphotodisplay.Model.Class;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import jfxtras.labs.scene.control.Magnifier;
import jfxtras.scene.menu.CirclePopupMenu;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class AnnotationPaneController implements Initializable {

    //FXML
    @FXML
    private AnchorPane root;
    @FXML
    private AnchorPane imgParent;
    @FXML
    public JFXComboBox<Class> ClassSelecor;
    @FXML
    private Magnifier magnifier;

    //DATA
    Images image;

    //design
    private final ImageView img;
    private final CustomGrid grid;
    private final AnchorPane MagnifierPane;
    private  JFXPopup fXPopup;

    CirclePopupMenu CirclePopUpMenu;
    CirclePopupMenu CirclePopUpMenu1;
    CirclePopupMenu CirclePopUpMenu2;

    MenuItem menuItem[];
    MenuItem menuItem1[];
    MenuItem menuItem2[];

    public AnnotationPaneController() {

        this.img = new ImageView();
        this.grid = new CustomGrid();
        this.MagnifierPane = new AnchorPane(img);
        
        //this.grid.addGrid(MagnifierPane);
        

        // circle menu X
        menuItem = new MenuItem[1];
        menuItem[0] = createItem(MaterialDesignIcon.PLUS, Color.ALICEBLUE);

        // circle menu X
        menuItem1 = new MenuItem[1];
        menuItem1[0] = createItem(MaterialDesignIcon.PLUS, Color.ALICEBLUE);

        // circle menu X
        menuItem2 = new MenuItem[1];
        menuItem2[0] = createItem(MaterialDesignIcon.PLUS, Color.ALICEBLUE);

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CirclePopUpMenu = new CirclePopupMenu(magnifier, MouseButton.NONE);

        img.fitWidthProperty().bind(magnifier.widthProperty());
        img.fitHeightProperty().bind(magnifier.heightProperty());

        MagnifierPane.prefWidthProperty().bind(magnifier.widthProperty());
        MagnifierPane.prefHeightProperty().bind(magnifier.heightProperty());

        magnifier.setContent(MagnifierPane);
        magnifier.setOnMouseClicked((event) -> {
            System.err.println(event.getX());
        });

        GridINI();
        imageParentINI();
        

    }

    private MenuItem createItem(MaterialDesignIcon PLUS, Paint p) {
        MaterialDesignIconView materialDesignIconView;
        JFXButton jfxButton;
        materialDesignIconView = new MaterialDesignIconView(PLUS);
        jfxButton = new JFXButton("", materialDesignIconView);
        materialDesignIconView.setGlyphSize(30);
        materialDesignIconView.setFill(p);
        jfxButton.setAlignment(Pos.CENTER);
        jfxButton.setPrefSize(50, 50);
        jfxButton.setMinSize(50, 50);
        jfxButton.setShape(new Circle(49));
        jfxButton.setBackground(new Background(new BackgroundFill(p, CornerRadii.EMPTY, Insets.EMPTY)));
        jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
        MenuItem menuItem = new MenuItem("", jfxButton);
        return menuItem;
    }

    private void imageParentINI() {
        imgParent.widthProperty().addListener((observable, oldValue, newValue) -> {

            if(GridOn) RefreshGrid();
        });

        imgParent.heightProperty().addListener((observable, oldValue, newValue) -> {

            if(GridOn) RefreshGrid();

        });
    }

    private void GridINI() {
        // Grid INI

        grid.setFillColor(Color.TRANSPARENT);
        grid.setHrectNumber(25);
        grid.setVrectNumber(40);
        grid.setStartX(img.getLayoutX());
        grid.setStartY(img.getLayoutY());
        grid.setWidth(img.getFitWidth());
        grid.setHeight(img.getFitHeight());
        
    }

    public void RefreshGrid() {
        grid.setStartX(img.getLayoutX());
        grid.setStartY(img.getLayoutY());
        grid.setWidth(img.getFitWidth());
        grid.setHeight(img.getFitHeight());
        grid.update();
    }

    @FXML
    private void NextImage(ActionEvent event) {
    }


    @FXML
    private void OptionPop(ActionEvent event) {
    }

    @FXML
    private void SettingPop(ActionEvent event) {
    }
    @FXML
    private void remove(ActionEvent event) {
        Class selectedItem = ClassSelecor.getSelectionModel().getSelectedItem();
        ClassSelecor.getItems().remove(selectedItem);
        Annotation findAnnotation = Annotation.findAnnotation(selectedItem, image);
        findAnnotation.remove();
    }

    @FXML
    private void AddClassPop(ActionEvent event) throws IOException {
        
        final AnnotationPaneController x=this;

        ResourceBundle rs = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {

                return x;
            }

            @Override
            public Enumeration<String> getKeys() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        this.fXPopup = new JFXPopup(FXMLLoader.load(getClass().getResource("AddToFXML.fxml"), rs));
        
        JFXButton source = (JFXButton) event.getSource();
        fXPopup.show(root,JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, 50, 50);
    }


    @FXML
    private void prevImage(ActionEvent event) {
    }
    
    private boolean GridOn= false;
    @FXML
    private void GridMode(ActionEvent event) {
        
        GridOn= !GridOn;
        
        JFXButton source = (JFXButton) event.getSource();
        if (GridOn) {
            grid.addGrid(MagnifierPane);
            RefreshGrid();
            source.setStyle("-fx-background-color: blue;");
        } else {
            grid.removeGrid();
            source.setStyle("-fx-background-color: red;");
        }
        
        
    }

    private boolean loopOn = false;

    @FXML
    private void LOOP(ActionEvent event) {

        loopOn = !loopOn;

        JFXButton source = (JFXButton) event.getSource();
        if (loopOn) {
            source.setStyle("-fx-background-color: blue;");
        } else {
            source.setStyle("-fx-background-color: red;");
        }
            magnifier.setActive(loopOn);

    }
    /**
     * ***************creation du obj******************
     */
    public void setimage(Images image) {

        this.image = image;

        Image im = new Image(image.getDir().toURI().toString(), 0, 0, false, false);

        img.setImage(im);
        
        if (!image.ANNOTATIONS.isEmpty()) {
            for (Annotation annotation : image.ANNOTATIONS) {
                
                if (!ClassSelecor.getItems().contains(annotation.getClasse())) {
                    ClassSelecor.getItems().add(annotation.getClasse());
                }
            }
        }
    }


}
