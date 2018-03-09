/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import com.jfoenix.controls.JFXPopup;
import copytowindowsphotodisplay.Model.Annotation;
import copytowindowsphotodisplay.Model.Images;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import org.dom4j.Element;

/**
 *
 * @author ilies
 */
public class DrawCircle extends Ellipse implements DrawableShape {

    //Info
    //Image
    Images image;

    //Scale
    public double xK;
    public double yK;

    //Nodes
    private Circle ci;
    public AnchorPane Parent;
    //state
    private double X;
    private double Y;
    double centerX;
    double centerY;
    //UI
    public JFXPopup OptionPopup;
    public JFXPopup AddToPopup;

    public DrawCircle(double x, double y, double rx, double ry, double xk, double yk, Images image) throws IOException {

        this.image = image;
        xK = xk;
        yK = yk;
        ci = new Circle(6, Color.rgb(114, 137, 218, 0.6));

        setLayoutX(0);
        setLayoutY(0);

        ci.setEffect(new DropShadow());

        setFill(Color.web("blue", 0.4));

        SetPoints(x, y, rx, ry);
        RefreshCircle();

        //Circle Actions
        setOnMouseClicked((event) -> {

            if (event.getButton() == MouseButton.SECONDARY) {
                
                if(OptionPopup == null)
                    OptionPopUpSetup();

                OptionPopup.show(Parent, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, getCenterX(), getCenterY());

            }
            if (event.getClickCount() == 2) {

                if(AddToPopup == null)
                    AddToPopUpSetup();
                
                AddToPopup.show(Parent, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, getCenterX(), getCenterY());

            }

        });

        setOnMousePressed((event) -> {

            if (event.getButton() != MouseButton.SECONDARY) {

                X = event.getScreenX();
                Y = event.getScreenY();
                centerX = getCenterX();
                centerY = getCenterY();

            }

        });

        setOnMouseDragged((event) -> {

            if (event.getButton() != MouseButton.SECONDARY) {

                double deltaX = event.getScreenX() - X;
                double deltaY = event.getScreenY() - Y;

                MoveTo(centerX + deltaX, centerY + deltaY);

            }
        });
        setOnMouseReleased((event) -> {

            
        });

        //Circle Actions
        ci.setOnMousePressed((event) -> {

            ci.setRadius(3);
            X = event.getScreenX();
            Y = event.getScreenY();
            centerX = ci.getCenterX();
            centerY = ci.getCenterY();

        });
        ci.setOnMouseDragged((event) -> {

            double deltaX = X - event.getScreenX();
            double deltaY = Y - event.getScreenY();

            ci.setCenterX(centerX - deltaX);
            ci.setCenterY(centerY - deltaY);

            SetPoints(getCenterX(), getCenterY(), ci.getCenterX(), ci.getCenterY());

        });
        ci.setOnMouseReleased((event) -> {

            ci.setRadius(6);

            RefreshCircle();
        });
    }

    private void OptionPopUpSetup() {
        try {
            FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("RectOption.fxml"));
            this.OptionPopup = new JFXPopup(loaderOption.load());
            RectOptionCTR controller = loaderOption.getController();
            controller.setShape(image.getProject().CLASSES, this);
            
        } catch (IOException e) {
        }
    }

    public void AddToPopUpSetup() {
        try {
            FXMLLoader loaderAddTo = new FXMLLoader(getClass().getResource("AddShapeTo.fxml"));
            this.AddToPopup = new JFXPopup(loaderAddTo.load());
            AddShapeToController controller2 = loaderAddTo.getController();
            controller2.setImage(image, this);
        } catch (IOException iOException) {
        }
    }

    public void SetPoints(double x, double y, double i, double j) {

        MoveTo(x, y);
        ResizeTo(Math.abs(x - i), Math.abs(j - y));

    }

    private void ResizeTo(double w, double h) {

        try {
            if (getRadiusX() + w > Parent.getWidth()) {
                w = Parent.getWidth() - getRadiusX();
                System.out.println("a");
            }
            if (getRadiusY() + w > Parent.getHeight()) {
                h = Parent.getHeight() - getRadiusY();
                System.out.println("b");
            }
            if (getCenterX() - w < 0) {
                w = getRadiusX();
            }
            if (getCenterY() - h < 0) {
                h = getRadiusY();
            }

        } catch (Exception e) {
        }
        this.setRadiusX(w);
        this.setRadiusY(h);
    }

    @Override
    public void MoveTo(double X, double Y) {

        try {
            if (X - getRadiusX() < 0) {
                X = getRadiusX();
            }
            if (X + getRadiusX() > Parent.getWidth()) {
                X = Parent.getWidth() - getRadiusX();
            }

            if (Y - getRadiusY()< 0) {
                Y = getRadiusY();
            }
            if (Y + getRadiusY() > Parent.getHeight()) {
                Y = Parent.getHeight() - getRadiusY();
            }

            setCenterX(X);
            setCenterY(Y);
        } catch (Exception e) {

            setCenterX(X);
            setCenterY(Y);
        }

        RefreshCircle();

    }

    @Override
    public void AddTo(AnchorPane p) {

        if(!p.getChildren().contains(this))
            p.getChildren().add(this);
        if(!p.getChildren().contains(ci))
            p.getChildren().add(ci);
        this.Parent = p;
    }

    @Override
    public void remove(AnchorPane p) {
        p.getChildren().remove(this);
        p.getChildren().remove(ci);

        for (Annotation annotation : image.ANNOTATIONS) {

            annotation.Shapes.remove(this);

        }
    }

    @Override
    public void ScaleTo(double xK, double yK) {

        /* Old_K * ow = W
        new_K * nw = W
        new_K * nw = Old_K * ow

        nw = Old_K  * ow / new_K    */
        double DX = this.xK / xK;
        double DY = this.yK / yK;

        this.xK = xK;
        this.yK = yK;

        setCenterX(getCenterX() * DX);
        setCenterY(getCenterY() * DY);
        setRadiusX(getRadiusX() * DX);
        setRadiusY(getRadiusY() * DY);

        RefreshCircle();

    }

    public void RefreshCircle() {

        double centerX = getCenterX();
        double centerY = getCenterY();
        double a = getRadiusX();
        double b = getRadiusY();
        double O = Math.PI / 4;
        double Tan_O = Math.tan(O);
        double X;
        double Y;
        if (-Math.PI / 2 < O || O < Math.PI / 2) {
            X = a * b / Math.sqrt((b * b) + (a * a * Tan_O * Tan_O));
            Y = a * b / Math.sqrt((a * a) + ((b * b) / (Tan_O * Tan_O)));
        } else {
            X = -a * b / Math.sqrt((b * b) + (a * a * Tan_O * Tan_O));
            Y = -a * b / Math.sqrt((a * a) + ((b * b) / (Tan_O * Tan_O)));
        }

        ci.setCenterX(X + centerX);
        ci.setCenterY(Y + centerY);
    }

    @Override
    public String toString() {
        return "Circle," + getCenterX() + "," + getCenterY() + "," + getRadiusX() + "," + getRadiusY() + "," + xK + "," + yK + "\n";  //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addElement(Element addAttribute , Annotation note) {
        
        addAttribute.addElement("Circle")
                .addAttribute("Class", note.getClasse().getName())
                .addAttribute("CenterX", String.valueOf(getCenterX()*xK))
                .addAttribute("CenterY", String.valueOf(getCenterY()*yK))
                .addAttribute("RadiusX", String.valueOf(getRadiusX()*xK))
                .addAttribute("RadiusY", String.valueOf(getRadiusY()*yK));
    }

}
