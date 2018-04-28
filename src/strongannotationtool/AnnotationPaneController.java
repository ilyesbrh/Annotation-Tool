/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool;

import strongannotationtool.Shapes.VisualShapes.DrawRectangle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXPopup;
import strongannotationtool.Shapes.CustomGrid;
import copytowindowsphotodisplay.Model.Images;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import jfxtras.labs.scene.control.Magnifier;
import org.controlsfx.control.GridView;
import org.dom4j.Element;
import strongannotationtool.Shapes.VisualShapes.DrawCircle;
import strongannotationtool.Shapes.VisualShapes.DrawableShape;
import strongannotationtool.Shapes.VisualShapes.FocusRect;

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
    private Magnifier magnifier;
    @FXML
    private JFXButton N;
    @FXML
    private JFXButton P;
    @FXML
    private JFXButton Zoom;
    @FXML
    private JFXButton RectBtn;
    @FXML
    private JFXButton PolyBtn;
    @FXML
    private JFXButton CircleBtn;
    @FXML
    private ScrollPane scrollPane;

    JFXDialog jfxDialog1;
    //DATA
    public Images image;

    //State
    private boolean GridOn = false;
    private boolean loopOn = false;
    private DrawableShape onCreate;
    private String ShapeMode = "Rectangle";

    //design
    private FocusRect focus;
    private final ImageView img;
    private final CustomGrid grid;
    private final AnchorPane MagnifierPane;
    private final GridView<Images> ImagesGrid;

    public AnnotationPaneController(JFXDialog jfxDialog, GridView<Images> grid) {

        this.ImagesGrid = grid;
        jfxDialog1 = jfxDialog;
        this.img = new ImageView();

        this.grid = new CustomGrid();
        this.MagnifierPane = new AnchorPane(img);

        //this.grid.addGrid(MagnifierPane);
        /*
        // circle menu GridOff
        menuItem = new MenuItem[3];
        //menuItem[] = createItem(MaterialDesignIcon.RECORD, Color.web("#96ceb4"), "Point", CirclePopUpMenu);
        menuItem[0] = createItem(MaterialDesignIcon.CHECKBOX_BLANK_CIRCLE_OUTLINE, Color.web("#96ceb4"), "Circle");
        menuItem[1] = createItem(MaterialDesignIcon.OCTAGON_OUTLINE, Color.web("#96ceb4"), "Polygone");
        //menuItem[] = createItem(MaterialDesignIcon.PENCIL, Color.web("#96ceb4"), "Pen", CirclePopUpMenu);
        menuItem[2] = createItem(MaterialDesignIcon.CHECKBOX_BLANK_OUTLINE, Color.web("#66B032"), "Rectangle");
         */
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
    EventHandler<ScrollEvent> ScrollEvents = (ScrollEvent event) -> {

        if (event.isControlDown()) {
            System.out.println(event.getDeltaY());
        }

    };
    EventHandler<MouseEvent> MouseEvents = (MouseEvent event) -> {

        if (GridOn) {
            GridOn(event);
        }
        if (!GridOn) {
            try {

                GridOff(event);

            } catch (IOException iOException) {
            }
        }
    };

    private void GridOn(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {
                SecendBtnPressed_GridOff(event);
            }
            if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            }
        }
    }

    private void GridOff(MouseEvent event) throws IOException {

        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

            MousePressed_GridOff(event);

        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

            MouseDragged_GridOff(event);

        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

            MouseRelease_GriddOff(event);
        }
    }

    private void MousePressed_GridOff(MouseEvent event) throws IOException {

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

    private void MouseRelease_GriddOff(MouseEvent event) throws IOException {

        if (event.getButton().compareTo(MouseButton.PRIMARY) == 0) {
            PrimaryBtnRelease_GridOff(event);
        }
        if (event.getButton().compareTo(MouseButton.SECONDARY) == 0) {

        }
    }

    private void PrimaryBtnPressed_GridOff(MouseEvent event) throws IOException {
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

    private void PrimaryBtnRelease_GridOff(MouseEvent event) throws IOException {

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

    }

    private void RectOnPress(MouseEvent event) {
        try {
            double xk = img.getImage().getWidth() / img.getFitWidth();
            double yk = img.getImage().getHeight() / img.getFitHeight();

            onCreate = new DrawRectangle(event.getX(), event.getY(), event.getX() + 1, event.getY() + 1, xk, yk, this.image);
            onCreate.AddTo(imgParent, focus);
            focus.focusOn((DrawRectangle) onCreate);
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

        DrawRectangle rect = (DrawRectangle) onCreate;
        if (rect.getWidth() > 10 && rect.getHeight() > 10) {
            rect.AddToPopup();
            rect.AddToPopup.show(rect);
        } else {
            onCreate.remove(imgParent);
        }
        onCreate = null;
    }

    private void CircleOnPress(MouseEvent event) throws IOException {

        try {
            double xk = img.getImage().getWidth() / img.getFitWidth();
            double yk = img.getImage().getHeight() / img.getFitHeight();

            onCreate = new DrawCircle(event.getX(), event.getY(), event.getX() + 15, event.getY() + 15, xk, yk, this.image);
            onCreate.AddTo(imgParent, focus);
            focus.focusOn((DrawCircle) onCreate);

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

        double x, y;
        if (event.getX() < 0) {
            x = 0;
        } else if (event.getX() > MagnifierPane.getWidth()) {
            x = MagnifierPane.getWidth();
        } else {
            x = event.getX();
        }
        if (event.getY() < 0) {
            y = 0;
        } else if (event.getY() > MagnifierPane.getHeight()) {
            y = MagnifierPane.getHeight();
        } else {
            y = event.getY();
        }
        DCircle.SetPoints(DCircle.getLayoutX() - DCircle.getRadiusX(), DCircle.getLayoutY() - DCircle.getRadiusY(), x, y);
        //DCircle.RefreshCircle();

    }

    private void CIrcleOnRelease(MouseEvent event) throws IOException {

        DrawCircle DCircle = (DrawCircle) onCreate;
        if (DCircle.getRadiusX() > 10 && DCircle.getRadiusY() > 10) {
            DCircle.AddToPopUpSetup();
            DCircle.AddToPopup.show(imgParent, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, DCircle.getLayoutX(), DCircle.getLayoutY());
        } else {
            onCreate.remove(imgParent);
        }
        onCreate = null;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        this.focus = new FocusRect(imgParent);
        jfxDialog1.setOnDialogClosed((jevent) -> {
            System.out.println(jfxDialog1.getDialogContainer().getChildren());
        });
        N.setTooltip(new Tooltip("Next"));
        P.setTooltip(new Tooltip("Previous"));
        Zoom.setTooltip(new Tooltip("Zoom"));

        img.fitWidthProperty().bind(scrollPane.widthProperty());
        img.fitHeightProperty().bind(scrollPane.heightProperty());

        MagnifierPane.prefWidthProperty().bind(magnifier.widthProperty());
        MagnifierPane.prefHeightProperty().bind(magnifier.heightProperty());

        magnifier.prefWidthProperty().bind(scrollPane.widthProperty());
        magnifier.prefHeightProperty().bind(scrollPane.heightProperty());

        imgParent.prefWidthProperty().bind(scrollPane.widthProperty());
        imgParent.prefHeightProperty().bind(scrollPane.heightProperty());

        magnifier.setContent(MagnifierPane);
        magnifier.addEventFilter(MouseEvent.ANY, MouseEvents);
        magnifier.addEventFilter(ScrollEvent.SCROLL, ScrollEvents);

        root.sceneProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null) {
                newValue.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {

                    try {
                        if (event.getCode().equals(KeyCode.DELETE)&& focus != null) {
                            
                            focus.removeFrom(imgParent);
                            focus.shapeOnFocus.remove(imgParent);
                        }
                    } catch (Exception e) {
                    }
                    if (event.getCode().equals(KeyCode.LEFT)) {

                        Element parent = image.imageElement.getParent();
                        int index = parent.elements().indexOf(image.imageElement);

                        int indexOf = index - 1;

                        if (indexOf < 0) {
                            indexOf = parent.elements().size() - 1;
                        }

                        Element img = parent.elements().get(indexOf);
                        setimage(new Images(img));
                    }
                    if (event.getCode().equals(KeyCode.RIGHT)) {

                        Element parent = image.imageElement.getParent();
                        int index = parent.elements().indexOf(image.imageElement);

                        int indexOf = index + 1;
                        
                        if (indexOf > parent.elements().size() - 1) {
                            indexOf = 0;
                        }

                        Element img = parent.elements().get(indexOf);
                        setimage(new Images(img));
                    }
                });
            }
        });
        GridINI();
        imageParentINI();

        root.widthProperty().addListener((observable, oldValue, newValue) -> {

            P.setTranslateX((newValue.doubleValue() - 155) / 2);
            N.setTranslateX(P.getTranslateX() + 155);

        });

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
            /*
            for (MenuItem mi : this.menuItem) {
            //reset all items Color
            ((JFXButton)mi.getGraphic()).setBackground(new Background(new BackgroundFill(Color.web("#96ceb4"), CornerRadii.EMPTY, Insets.EMPTY)));
            //change clicked item color
            jfxButton.setBackground(new Background(new BackgroundFill(Color.web("#66B032"), CornerRadii.EMPTY, Insets.EMPTY)));
            }*/
        });
        return menuItem;

    }

    private void imageParentINI() {
        imgParent.widthProperty().addListener((observable, oldValue, newValue) -> {

            if (GridOn) {
                RefreshGrid();
            }
            if (!GridOn) {
                RefreshShapes();
            }
        });

        imgParent.heightProperty().addListener((observable, oldValue, newValue) -> {

            if (GridOn) {
                RefreshGrid();
            }
            if (!GridOn) {
                RefreshShapes();
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

    /**
     * ***************creation du obj
     *
     ******************
     * @param image
     */
    public void setimage(Images image) {

        imgParent.getChildren().clear();
        imgParent.getChildren().add(magnifier);
        magnifier.prefWidthProperty().bind(imgParent.widthProperty());
        magnifier.prefHeightProperty().bind(imgParent.heightProperty());

        this.image = image;

        Image im = image.getImage(0, 0, false, true, false);

        img.setImage(im);

        LoadShapes();
        RefreshShapes();

    }

    @FXML
    private void prevImage(ActionEvent event) {

        Element parent = image.imageElement.getParent();
        int index = parent.elements().indexOf(image.imageElement);

        int indexOf = index - 1;

        if (indexOf < 0) {
            indexOf = parent.elements().size() - 1;
        }

        Element img = parent.elements().get(indexOf);
        setimage(new Images(img));
    }

    @FXML
    private void NextImage(ActionEvent event) {

        Element parent = image.imageElement.getParent();
        int index = parent.elements().indexOf(image.imageElement);

        int indexOf = index + 1;
        if (indexOf > parent.elements().size() - 1) {
            indexOf = 0;
        }

        Element img1 = parent.elements().get(indexOf);
        setimage(new Images(img1));

    }

    @FXML
    private void SettingPop(ActionEvent event) {
    }

    @FXML
    private void ChangeMode(ActionEvent event) {

        if (event.getSource().equals(CircleBtn)) {

            CircleBtn.setStyle("-fx-background-color : #96ceb4 ");
            RectBtn.setStyle("");
            ShapeMode = "Circle";
        }
        if (event.getSource().equals(RectBtn)) {

            RectBtn.setStyle("-fx-background-color : #96ceb4 ");
            CircleBtn.setStyle("");
            ShapeMode = "Rectangle";
        }
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

        onfocus();

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

        DrawableShape shape = null;
        for (Node node : imgParent.getChildren()) {

            if (node instanceof DrawableShape) {
                shape = (DrawableShape) node;
                double xk = img.getImage().getWidth() / img.getFitWidth();
                double yk = img.getImage().getHeight() / img.getFitHeight();
                System.err.println("X scale :" + xk + " Y scale :" + yk);
                if (xk != Double.POSITIVE_INFINITY && xk != 0
                        && yk != Double.POSITIVE_INFINITY && yk != 0) {
                    shape.ScaleTo(xk, yk);
                }
            }
        }
    }

    private void LoadShapes() {

        for (Element S : image.imageElement.elements()) {

            if (S.attribute(0).getValue().equals("Rectangle")) {
                new DrawRectangle(S).AddTo(imgParent, focus);
            } else if (S.attribute(0).getValue().equals("Circle")) {
                new DrawCircle(S).AddTo(imgParent, focus);
            }
        }

    }

    private void onfocus() {

    }

    @FXML
    private void ClosePopUp(ActionEvent event) {

        focus.removeFrom(imgParent);
        jfxDialog1.close();

    }

    @FXML
    private void OnZoom(ZoomEvent event) {

    }

}
