/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool;

import strongannotationtool.Shapes.VisualShapes.DrawRectangle;
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
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import jfxtras.labs.scene.control.Magnifier;
import jfxtras.scene.menu.CirclePopupMenu;
import strongannotationtool.Shapes.VisualShapes.DrawableShape;

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
    ObjectProperty<Images> image = new SimpleObjectProperty<>();

    //State
    private boolean GridOn = false;
    private boolean loopOn = false;

    //design
    private final ImageView img;
    private final CustomGrid grid;
    private final AnchorPane MagnifierPane;
    private JFXPopup fXPopup;

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
        // circle menu GridOff
        menuItem = new MenuItem[5];
        menuItem[0] = createItem(MaterialDesignIcon.RECORD, Color.web("#96ceb4"), "Point",CirclePopUpMenu);
        menuItem[1] = createItem(MaterialDesignIcon.CHECKBOX_BLANK_CIRCLE_OUTLINE, Color.web("#96ceb4"), "Circle",CirclePopUpMenu);
        menuItem[2] = createItem(MaterialDesignIcon.OCTAGON_OUTLINE, Color.web("#96ceb4"), "Polygone",CirclePopUpMenu);
        menuItem[3] = createItem(MaterialDesignIcon.PENCIL, Color.web("#96ceb4"), "Pen",CirclePopUpMenu);
        menuItem[4] = createItem(MaterialDesignIcon.CHECKBOX_BLANK_OUTLINE, Color.web("#96ceb4"), "Rectangle",CirclePopUpMenu);

        // circle menu X
        //menuItem1 = new MenuItem[1];
        //menuItem1[0] = createItem(MaterialDesignIcon.PLUS, Color.ALICEBLUE, "test");

    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    //.......... events
    
    private DrawableShape onCreate;
    private DrawableShape onMouse;
    private String ShapeMode ="Rectangle";
    
    EventHandler<MouseEvent> MouseEvents = (MouseEvent event) -> {

        if(event.getX()<0 || event.getX()> imgParent.getWidth()) return;
        if(event.getY()<0 || event.getY()> imgParent.getHeight()) return;
        
        if (GridOn) {
            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {
                    CirclePopUpMenu.show(event);
                }
                if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                    CirclePopUpMenu.hide();
                }
            }

        } else {

            if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
                if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {
                    CirclePopUpMenu.show(event);
                }
                if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                    CirclePopUpMenu.hide();
                }
            } else if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                
                if(onCreate == null){
                    
                    if("Rectangle".equals(ShapeMode)){
                        
                        try {
                            onCreate = new DrawRectangle(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1);
                            onCreate.AddTo(imgParent);
                        } catch (IOException iOException) {
                        }
                        
                    }
                    
                }

            }
            else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                
                if(onCreate != null){
                    
                   DrawRectangle DRect=null;
                   if("Rectangle".equals(ShapeMode)){
                        
                       if(onCreate instanceof DrawRectangle)
                            DRect = (DrawRectangle) onCreate;
                       else
                            System.err.println("not instance of drawable rectangle");
                       DRect.SetPoints(DRect.getLayoutX(), DRect.getLayoutY(), event.getX(), event.getY());
                    }
                    
                }
                

            }
            else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

                
                onCreate=null;
            }
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {

        CirclePopUpMenu = new CirclePopupMenu(magnifier, MouseButton.NONE);
        CirclePopUpMenu.getItems().addAll(menuItem);

        img.fitWidthProperty().bind(magnifier.widthProperty());
        img.fitHeightProperty().bind(magnifier.heightProperty());

        MagnifierPane.prefWidthProperty().bind(magnifier.widthProperty());
        MagnifierPane.prefHeightProperty().bind(magnifier.heightProperty());

        magnifier.setContent(MagnifierPane);
        magnifier.addEventFilter(MouseEvent.ANY, MouseEvents);

        GridINI();
        imageParentINI();

    }

    private MenuItem createItem(MaterialDesignIcon PLUS, Paint p, String S,CirclePopupMenu cpu) {
        MaterialDesignIconView materialDesignIconView;
        JFXButton jfxButton;
        materialDesignIconView = new MaterialDesignIconView(PLUS);
        jfxButton = new JFXButton("", materialDesignIconView);
        materialDesignIconView.setGlyphSize(30);
        materialDesignIconView.setFill(Color.WHITE);
        jfxButton.setAlignment(Pos.CENTER);
        jfxButton.setPrefSize(50, 50);
        jfxButton.setMinSize(50, 50);
        jfxButton.setShape(new Circle(49));
        jfxButton.setBackground(new Background(new BackgroundFill(p, CornerRadii.EMPTY, Insets.EMPTY)));
        jfxButton.setButtonType(JFXButton.ButtonType.RAISED);
        MenuItem menuItem= new MenuItem(S, jfxButton);
        menuItem.setOnAction((event) -> {
            ShapeMode=S;
            cpu.hide();
        });
        return menuItem;
    }

    private void imageParentINI() {
        imgParent.widthProperty().addListener((observable, oldValue, newValue) -> {

            if (GridOn) {
                RefreshGrid();
            }
        });

        imgParent.heightProperty().addListener((observable, oldValue, newValue) -> {

            if (GridOn) {
                RefreshGrid();
            }

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
    private void prevImage(ActionEvent event) {
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
        if (!ClassSelecor.getItems().isEmpty()) {
            Class selectedItem = ClassSelecor.getSelectionModel().getSelectedItem();
            ClassSelecor.getItems().remove(selectedItem);
            Annotation findAnnotation = Annotation.findAnnotation(selectedItem, image.get());
            findAnnotation.remove();
        }
    }

    @FXML
    private void AddClassPop(ActionEvent event) throws IOException {

        final AnnotationPaneController x = this;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddToFXML.fxml"));
        AddToFXMLController controller = loader.getController();
        controller.setAnnotationControler(this);
        this.fXPopup = new JFXPopup(loader.load());

        JFXButton source = (JFXButton) event.getSource();
        fXPopup.show(root, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.RIGHT, 50, 50);
    }

    @FXML
    private void GridMode(ActionEvent event) {

        GridOn = !GridOn;

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
     * ***************creation du obj
     *
     ******************
     * @param image
     */
    public void setimage(Images image) {

        this.image.set(image);

        Image im = new Image(image.getDir().toURI().toString(), 0, 0, false, false);

        img.setImage(im);

        ClassSelecor.getItems().clear();

        if (!image.ANNOTATIONS.isEmpty()) {
            for (Annotation annotation : image.ANNOTATIONS) {

                if (!ClassSelecor.getItems().contains(annotation.getClasse())) {
                    ClassSelecor.getItems().add(annotation.getClasse());
                }
            }
        }
    }

}
