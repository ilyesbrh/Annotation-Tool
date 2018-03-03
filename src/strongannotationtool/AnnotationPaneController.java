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
import copytowindowsphotodisplay.Model.CLASS;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import javafx.scene.shape.Shape;
import jfxtras.labs.scene.control.Magnifier;
import jfxtras.scene.menu.CirclePopupMenu;
import strongannotationtool.Shapes.VisualShapes.DrawCircle;
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
    public JFXComboBox<CLASS> ClassSelecor;
    @FXML
    private Magnifier magnifier;

    //DATA
    public ObjectProperty<Images> image = new SimpleObjectProperty<>();

    //State
    private boolean GridOn = false;
    private boolean loopOn = false;
    private DrawableShape onCreate;
    private String ShapeMode = "Rectangle";
    private double EventX;
    private double EventY;

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
        menuItem = new MenuItem[3];
        //menuItem[] = createItem(MaterialDesignIcon.RECORD, Color.web("#96ceb4"), "Point", CirclePopUpMenu);
        menuItem[0] = createItem(MaterialDesignIcon.CHECKBOX_BLANK_CIRCLE_OUTLINE, Color.web("#96ceb4"), "Circle");
        menuItem[1] = createItem(MaterialDesignIcon.OCTAGON_OUTLINE, Color.web("#96ceb4"), "Polygone");
        //menuItem[] = createItem(MaterialDesignIcon.PENCIL, Color.web("#96ceb4"), "Pen", CirclePopUpMenu);
        menuItem[2] = createItem(MaterialDesignIcon.CHECKBOX_BLANK_OUTLINE, Color.web("#96ceb4"), "Rectangle");

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
    EventHandler<MouseEvent> MouseEvents = (MouseEvent event) -> {
        if (!isEventIn(event)) {
            //return;
        }
        if (GridOn) {
            GridOn(event);
        }
        if (!GridOn) {
            GridOff(event);
        }
    };

    private boolean isEventIn(MouseEvent event) {
        if (event.getX() < 0 || event.getX() > imgParent.getWidth()) {
            return false;
        }
        if (event.getY() < 0 || event.getY() > imgParent.getHeight()) {
            return false;
        }
        return true;
    }

    private void GridOn(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {
                SecendBtnPressed_GridOff(event);
            }
            if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
                CirclePopUpMenu.hide();
            }
        }
    }

    private void GridOff(MouseEvent event) {

        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

            MousePressed_GridOff(event);

        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

            MouseDragged_GridOff(event);

        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

            MouseRelease_GriddOff(event);
        }
    }

    private void MousePressed_GridOff(MouseEvent event) {

        if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            PrimaryBtnPressed_GridOff(event);
        }
        if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {
            SecendBtnPressed_GridOff(event);
        }
    }

    private void MouseDragged_GridOff(MouseEvent event) {

        if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            PrimaryBtnDragged_GridOff(event);
        }
        if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {

        }
    }

    private void MouseRelease_GriddOff(MouseEvent event) {

        if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            PrimaryBtnRelease_GridOff(event);
        }
        if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {

        }
    }

    private void PrimaryBtnPressed_GridOff(MouseEvent event) {
        CirclePopUpMenu.hide();
        if (onCreate == null) {

            if ("Rectangle".equals(ShapeMode)) {
                System.out.println(ShapeMode);
                RectOnPress(event);
            }
            if ("Circle".equals(ShapeMode)) {
                System.out.println(ShapeMode);
                CircleOnPress(event);
            }
            
        }
    }

    private void PrimaryBtnDragged_GridOff(MouseEvent event) {

        if (onCreate != null) {

            if ("Rectangle".equals(ShapeMode)) {

                RectOnDrag(event);
            }
            if ("Circle".equals(ShapeMode)) {

                CircleOnDrag(event);
            }
        }
    }

    private void PrimaryBtnRelease_GridOff(MouseEvent event) {
        
        if (onCreate != null) {

            if ("Rectangle".equals(ShapeMode)) {

                RectOnRelease(event);
            }
            if ("Circle".equals(ShapeMode)) {

                CIrcleOnRelease(event);
            }
        }
    }

    private void SecendBtnPressed_GridOff(MouseEvent event) {
        CirclePopUpMenu.show(event);
    }

    private void RectOnPress(MouseEvent event) {
        try {
            double xk= img.getImage().getWidth()/img.getFitWidth();
            double yk= img.getImage().getHeight()/img.getFitHeight();
            
            onCreate = new DrawRectangle(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1,xk,yk,this.image.getValue());
            onCreate.AddTo(imgParent);
        } catch (IOException iOException) {
        }
    }

    private void RectOnDrag(MouseEvent event) {
        DrawRectangle DRect = null;
        if (onCreate instanceof DrawRectangle) {
            DRect = (DrawRectangle) onCreate;
        } else {
            System.err.println("not instance of drawable rectangle");
        }
        double x = 0, y = 0;
        if (event.getX() < 0) {
            x = 0;
        } else if (event.getX() > imgParent.getWidth()) {
            x = imgParent.getWidth();
        } else {
            x = event.getX();
        }
        if (event.getY() < 0) {
            y = 0;
        } else if (event.getY() > imgParent.getHeight()) {
            y = imgParent.getHeight();
        } else {
            y = event.getY();
        }
        DRect.SetPoints(DRect.getLayoutX(), DRect.getLayoutY(), x, y);
    }
    
    private void RectOnRelease(MouseEvent event) {
        
        DrawRectangle rect = (DrawRectangle)onCreate;
        if(rect.getWidth()>10 && rect.getHeight() >10)
            rect.AddToPopup.show(rect);
        else
            onCreate.remove(imgParent);
        onCreate = null;
    }
    
    
    private void CircleOnPress(MouseEvent event) {
        
        try {
            double xk= img.getImage().getWidth()/img.getFitWidth();
            double yk= img.getImage().getHeight()/img.getFitHeight();
            
            onCreate = new DrawCircle(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1,xk,yk,this.image.getValue());
            onCreate.AddTo(imgParent);
        } catch (IOException iOException) {
        }
    }

    private void CircleOnDrag(MouseEvent event) {
        
        DrawCircle DCircle = null;
        if (onCreate instanceof DrawCircle) {
            DCircle = (DrawCircle) onCreate;
        } else {
            System.err.println("not instance of drawable rectangle");
        }
        double x = 0, y = 0;
        if (event.getX() < 0) {
            x = 0;
        } else if (event.getX() > imgParent.getWidth()) {
            x = imgParent.getWidth();
        } else {
            x = event.getX();
        }
        if (event.getY() < 0) {
            y = 0;
        } else if (event.getY() > imgParent.getHeight()) {
            y = imgParent.getHeight();
        } else {
            y = event.getY();
        }
        DCircle.SetPoints(DCircle.getCenterX(), DCircle.getCenterY(), x, y);
        DCircle.RefreshCircle();
        
    }

    private void CIrcleOnRelease(MouseEvent event) {
    
        DrawCircle DCircle = (DrawCircle)onCreate;
        if(DCircle.getRadiusX()>10 && DCircle.getRadiusY()>10)
            DCircle.AddToPopup.show(imgParent, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, DCircle.getCenterX(),DCircle.getCenterY());
        else
            onCreate.remove(imgParent);
        onCreate = null;
    
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        CirclePopUpMenu = new CirclePopupMenu(magnifier, MouseButton.NONE);
        CirclePopUpMenu.getItems().addAll(menuItem);

        img.fitWidthProperty().bind(imgParent.widthProperty());
        img.fitHeightProperty().bind(imgParent.heightProperty());

        MagnifierPane.prefWidthProperty().bind(imgParent.widthProperty());
        MagnifierPane.prefHeightProperty().bind(imgParent.heightProperty());

        magnifier.prefWidthProperty().bind(imgParent.widthProperty());
        magnifier.prefHeightProperty().bind(imgParent.heightProperty());
        
        magnifier.setContent(MagnifierPane);
        magnifier.addEventFilter(MouseEvent.ANY, MouseEvents);

        GridINI();
        imageParentINI();
    }
    private MenuItem createItem(MaterialDesignIcon PLUS, Paint p, String S) {
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
        MenuItem menuItem = new MenuItem(S, jfxButton);
        jfxButton.setOnAction((event) -> {
            ShapeMode = S;
            for (MenuItem mi : this.menuItem) {
                //reset all items Color
                ((JFXButton)mi.getGraphic()).setBackground(new Background(new BackgroundFill(p, CornerRadii.EMPTY, Insets.EMPTY)));
                //change clicked item color
                jfxButton.setBackground(new Background(new BackgroundFill(Color.web("#66B032"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
        });
        return menuItem;
        
    }
    private void imageParentINI() {
        imgParent.widthProperty().addListener((observable, oldValue, newValue) -> {

            if (GridOn) {
                RefreshGrid();
            }
            if (!GridOn)
                RefreshShapes();
        });

        imgParent.heightProperty().addListener((observable, oldValue, newValue) -> {

            if (GridOn) {
                RefreshGrid();
            }
            if (!GridOn)
                RefreshShapes();

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
    /**
     * ***************creation du obj
     *
     ******************
     * @param image
     */
    public void setimage(Images image) {

        for (Node x : imgParent.getChildren()) {
            
            if(x instanceof Shape)
                imgParent.getChildren().remove(x);
        }
        
        this.image.set(image);

        Image im = new Image(image.getDir().toURI().toString(), 0, 0, false, true);

        img.setImage(im);

        ClassSelecor.getItems().clear();

        if (!image.ANNOTATIONS.isEmpty()) {
            for (Annotation annotation : image.ANNOTATIONS) {

                if (!ClassSelecor.getItems().contains(annotation.getClasse())) {
                     ClassSelecor.getItems().add(annotation.getClasse());
                }
            }
        }
        
        for (Annotation note : image.ANNOTATIONS) {
            
            System.out.println("annotation "+note.getClasse());
            
            for(Shape shape : note.Shapes){
                
                DrawableShape Dshape= (DrawableShape) shape;
                Dshape.AddTo(imgParent);   
            }
        }
        
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
            CLASS selectedItem = ClassSelecor.getSelectionModel().getSelectedItem();
            ClassSelecor.getItems().remove(selectedItem);
            Annotation findAnnotation = Annotation.findAnnotation(selectedItem, image.get());
            findAnnotation.remove();
        }
    }
    @FXML
    private void AddClassPop(ActionEvent event) throws IOException {

        final AnnotationPaneController x = this;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddToFXML.fxml"));
        this.fXPopup = new JFXPopup(loader.load());
        AddToFXMLController controller = loader.getController();
        controller.setAnnotationControler(this);

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
            source.setStyle("-fx-background-color: #40E0D0;");
        } else {
            source.setStyle("-fx-background-color: white;");
        }
        magnifier.setActive(loopOn);

    }

    public void RefreshShapes() {
        
        DrawableShape shape = null ;
        for (Node node : imgParent.getChildren()) {
            
            if(node instanceof DrawableShape){
                shape = (DrawableShape) node;
                double xk= img.getImage().getWidth()/img.getFitWidth();
                double yk= img.getImage().getHeight()/img.getFitHeight();
                System.err.println("X scale :"+xk+" Y scale :"+yk);
                if(xk != Double.POSITIVE_INFINITY && xk != 0
                        &&
                   yk != Double.POSITIVE_INFINITY && yk != 0)
                    
                shape.ScaleTo( xk, yk);
            } 
        }   
    }
}
