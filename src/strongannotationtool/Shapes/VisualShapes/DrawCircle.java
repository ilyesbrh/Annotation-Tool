/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import com.jfoenix.controls.JFXPopup;
import copytowindowsphotodisplay.Model.CustomResourceBundle;
import copytowindowsphotodisplay.Model.Images;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
    private Element shapeElement;

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
    AddShapeToController controller2 = null;


    public DrawCircle(double x, double y, double rx, double ry, double xk, double yk , Images image) throws IOException {

        shapeElement = image.imageElement.addElement("Shape")
                .addAttribute("Type", "Circle")
                .addAttribute("LayoutX", "")
                .addAttribute("LayoutY", "")
                .addAttribute("Width", "")
                .addAttribute("Height", "");
       
        xK = xk;
        yK = yk;
       
        ViewINI(x, y, rx, ry);
        ShapeEventsHandle();
        CircleEventHandle();
        
    }
    public DrawCircle(Element S) {
        
        shapeElement=S;
        
        String x = S.attribute(1).getValue();
        String y = S.attribute(2).getValue();
        String w = S.attribute(3).getValue();
        String h = S.attribute(4).getValue();
        
        double xx = Double.valueOf(S.attribute(1).getValue());
        double yy = Double.valueOf(S.attribute(2).getValue());
        double ww = Double.valueOf(S.attribute(3).getValue());
        double hh = Double.valueOf(S.attribute(4).getValue());
        
        xK=1;
        yK=1;
        
        ViewINI(xx, yy, xx+ww, yy+hh);
        ShapeEventsHandle();
        CircleEventHandle();
        
    }

    public void AddToPopup() {
        if(AddToPopup == null)
            try {
                System.out.println("AddShapeTo");
                CustomResourceBundle customResourceBundle = new CustomResourceBundle();
                customResourceBundle.addObject("element", this);
                FXMLLoader loaderAddTo = new FXMLLoader(getClass().getResource("AddShapeTo.fxml"),customResourceBundle);
                this.AddToPopup = new JFXPopup(loaderAddTo.load());
                controller2 = loaderAddTo.getController();
                
            } catch (IOException ex) {
                Logger.getLogger(DrawRectangle.class.getName()).log(Level.SEVERE, null, ex);
            }
        else{
            if(controller2 == null) {
                System.err.println("Null Controller Add TO POPUp");
            } else
            controller2.refresh();
        }
    }


    private void ViewINI(double x, double y, double rx, double ry) {
        ci = new Circle(6, Color.rgb(114, 137, 218, 0.6));
        
        setLayoutX(0);
        setLayoutY(0);

        ci.setEffect(new DropShadow());

        setFill(Color.web("blue", 0.4));

        SetPoints(x, y, rx, ry);
        RefreshCircle();
    }
    private void CircleEventHandle() {
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
    private void ShapeEventsHandle() {
        //Circle Actions
        setOnMouseClicked((event) -> {
            
            if (event.getButton() == MouseButton.SECONDARY) {
                
                if(OptionPopup == null)
                    OptionPopUpSetup();

                OptionPopup.show(Parent, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, getCenterX(), getCenterY());

            }
            if (event.getClickCount() == 2) {

                AddToPopup();
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
    }

    private void OptionPopUpSetup() {
        try {
            FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("RectOption.fxml"));
            this.OptionPopup = new JFXPopup(loaderOption.load());
            RectOptionCTR controller = loaderOption.getController();
            controller.setShape(this);
            
        } catch (IOException e) {
        }
    }
    public AddShapeToController AddToPopUpSetup() {
        try {
            FXMLLoader loaderAddTo = new FXMLLoader(getClass().getResource("AddShapeTo.fxml"));
            this.AddToPopup = new JFXPopup(loaderAddTo.load());
            AddShapeToController controller2 = loaderAddTo.getController();
            controller2.setElement(this);
            return controller2;
        } catch (IOException iOException) {
        }
        return null;
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
        
        shapeElement.attribute(3).setValue(String.valueOf(getRadiusX()*xK));
        shapeElement.attribute(4).setValue(String.valueOf(getRadiusY()*yK));
        

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
        shapeElement.attribute(1).setValue(String.valueOf(getCenterX()*xK));
        shapeElement.attribute(2).setValue(String.valueOf(getCenterY()*yK));
        shapeElement.attribute(3).setValue(String.valueOf(getRadiusX()*xK));
        shapeElement.attribute(4).setValue(String.valueOf(getRadiusY()*yK));
        

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
        shapeElement.getParent().remove(shapeElement);

       
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

        shapeElement.attribute(1).setValue(String.valueOf(getCenterX()*xK));
        shapeElement.attribute(2).setValue(String.valueOf(getCenterY()*yK));
        shapeElement.attribute(3).setValue(String.valueOf(getRadiusX()*xK));
        shapeElement.attribute(4).setValue(String.valueOf(getRadiusY()*yK));
        
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
    public Parent getParentNode() {
        return Parent;
    }
    @Override
    public Element getElement() {
        return shapeElement;
    }
    @Override
    public String toString() {
        return "Circle," + getCenterX() + "," + getCenterY() + "," + getRadiusX() + "," + getRadiusY() + "," + xK + "," + yK + "\n";  //To change body of generated methods, choose Tools | Templates.
    }
}
