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

/**
 *
 * @author ilies
 */
public class DrawCircle extends Ellipse implements DrawableShape{
    
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
    
    public DrawCircle(double x, double y, double rx, double ry,double xk,double yk,Images image) throws IOException {

        this.image=image;
        xK = xk;
        yK = yk;
        ci = new Circle(6, Color.rgb(114, 137, 218, 0.6));
        
        setLayoutX(0);
        setLayoutY(0);
        
        ci.setEffect(new DropShadow());

        setFill(Color.web("blue", 0.4));
        
        SetPoints(x, y, rx, ry);
        

        RefreshCircle();

        //Rectangle Actions
        
        try {
            FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("RectOption.fxml"));
            this.OptionPopup = new JFXPopup(loaderOption.load());
            RectOptionCTR controller = loaderOption.getController();
            controller.setShape(image.getProject().CLASSES,this);
            
            FXMLLoader loaderAddTo = new FXMLLoader(getClass().getResource("AddShapeTo.fxml"));
            this.AddToPopup = new JFXPopup(loaderAddTo.load());
            AddShapeToController controller2 = loaderAddTo.getController();
            controller2.setImage(image,this);
            
        } catch (IOException e) {}
        
        setOnMouseClicked((event) -> {

            if (event.getButton() == MouseButton.SECONDARY) {

                
                OptionPopup.show(Parent, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, getCenterX(),getCenterY());

            }
            if(event.getClickCount()==2){
                
                AddToPopup.show(Parent, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, getCenterX(),getCenterY());
                
            }

        });

        setOnMousePressed((event) -> {

            if (event.getButton() != MouseButton.SECONDARY) {

                X = event.getScreenX();
                Y = event.getScreenY();
                centerX = getCenterX();
                centerY = getCenterY();
                System.out.println("center X :"+centerX);
                System.out.println("center Y :"+centerY);
            }
            
        });

        setOnMouseDragged((event) -> {

            if (event.getButton() != MouseButton.SECONDARY) {

                double deltaX = event.getScreenX() - X ;
                double deltaY = event.getScreenY() - Y ;
                
                MoveTo(centerX + deltaX, centerY+ deltaY);
                
            }
        });
        setOnMouseReleased((event) -> {

        });

        //Circle Actions
        ci.setOnMousePressed((event) -> {

            ci.setRadius(3);

        });
        ci.setOnMouseDragged((event) -> {

            double Rx = event.getX() + ci.getCenterX();
            double Ry = event.getY() + ci.getCenterY();

            ci.setCenterX(Rx);
            ci.setCenterY(Ry);

            SetPoints(this.getCenterX(), this.getCenterY(), ci.getCenterX(), ci.getCenterY());

        });
        ci.setOnMouseReleased((event) -> {

            if (event.getX() + ci.getLayoutX() < 0 || event.getX() > Parent.getWidth()) {
                return;
            }
            if (event.getY() + ci.getLayoutY() < 0 || event.getY() > Parent.getHeight()) {
                return;
            }

            ci.setRadius(6);
            
            SetPoints(this.getCenterX(), this.getCenterY(), ci.getCenterX(), ci.getCenterY());

            RefreshCircle();
        });
    }

    public void SetPoints(double x, double y, double i, double j) {

        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadiusX(Math.abs(x - i));
        this.setRadiusY(Math.abs(j - y));

    }

    @Override
    public void AddTo(AnchorPane p) {
        
        p.getChildren().add(this);
        p.getChildren().add(ci);
        this.Parent = p;
    }

    @Override
    public void MoveTo(double X, double Y) {

        /*if (X < 0) {
        return;
        }
        if (X + getRadiusX()> Parent.getWidth()) {
        return;
        }
        
        if (Y < 0) {
        return;
        }
        if (Y + getRadiusY() > Parent.getHeight()) {
        return;
        }*/
        
        setCenterX(X);
        setCenterY(Y);
        
        RefreshCircle();

    }

    @Override
    public void ScaleTo(double xK, double yK) {
    
        /* Old_K * ow = W
        new_K * nw = W
        new_K * nw = Old_K * ow

        nw = Old_K  * ow / new_K    */
    
        double DX= this.xK/xK;
        double DY= this.yK/yK;
       
        this.xK =xK;
        this.yK =yK;
        
        setCenterX(getCenterX()* DX);
        setCenterY(getCenterY()* DY);
        setRadiusX(getRadiusX()* DX);
        setRadiusY(getRadiusY()* DY);
        
        
        RefreshCircle();

    }

    @Override
    public void remove(AnchorPane p) {
        p.getChildren().remove(this);
        p.getChildren().remove(ci);

        for (Annotation annotation : image.ANNOTATIONS) {
            
            annotation.Shapes.remove(this);
            
        }
    }

    public void RefreshCircle() {
        
        double centerX = getCenterX();
        double centerY = getCenterY();
        double a = getRadiusX();
        double b = getRadiusY();
        double O = Math.PI/4;
        double Tan_O = Math.tan(O);
        double X;
        double Y;
        if(-Math.PI/2 <O || O<Math.PI/2){
         X= a*b/Math.sqrt((b*b)+(a*a*Tan_O*Tan_O));
         Y= a*b/Math.sqrt((a*a)+((b*b)/(Tan_O*Tan_O)));
            }
        else{
         X= -a*b/Math.sqrt((b*b)+(a*a*Tan_O*Tan_O));
         Y= -a*b/Math.sqrt((a*a)+((b*b)/(Tan_O*Tan_O)));
            }

        ci.setCenterX(X+centerX);
        ci.setCenterY(Y+centerY);
    }
}
