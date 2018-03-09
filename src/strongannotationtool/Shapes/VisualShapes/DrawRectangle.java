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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.dom4j.Element;
import strongannotationtool.Shapes.CustomRectangle;

/**
 *
 * @author ilies
 */
public class DrawRectangle extends CustomRectangle implements DrawableShape {

        
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
    //UI
    public JFXPopup OptionPopup;
    public JFXPopup AddToPopup;
    private double CenterX;
    private double CenterY;

    
    public DrawRectangle(double x, double y, double i, double j,double xk,double yk,Images image) throws IOException {

        this.image=image;
        xK=xk;
        yK=yk;
        
        ci = new Circle(6, Color.rgb(114, 137, 218, 0.6));
        ci.setEffect(new DropShadow());

        setFill(Color.web("blue", 0.4));
        SetPoints(x, y, i, j);
        

        ci.setLayoutX(this.getLayoutX() + this.getWidth());
        ci.setLayoutY(this.getLayoutY() + this.getHeight());

        //Rectangle Actions
        
        setOnMouseClicked((event) -> {

            if (event.getButton() == MouseButton.SECONDARY) {
                if(OptionPopup == null)
                try {
                    FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("RectOption.fxml"));
                    this.OptionPopup = new JFXPopup(loaderOption.load());
                    RectOptionCTR controller = loaderOption.getController();
                    controller.setShape(image.getProject().CLASSES,this);

                } catch (Exception e) {
                }

                OptionPopup.show(this);
            }
            if(event.getClickCount()==2){
                
                AddToPopup();
                    AddToPopup.show(this);
                
            }

        });

        setOnMousePressed((event) -> {

            X = event.getScreenX();
            Y = event.getScreenY();
            CenterX = getLayoutX();
            CenterY = getLayoutY();
        });

        setOnMouseDragged((event) -> {

            double deltaX = X - event.getScreenX();
            double deltaY = Y - event.getScreenY();

            MoveTo(CenterX - deltaX, CenterY - deltaY);

        });
        setOnMouseReleased((event) -> {

        });

        //Circle Actions
        ci.setOnMousePressed((event) -> {

            ci.setRadius(5);

        });
        ci.setOnMouseDragged((event) -> {

            double Rx = event.getX() + ci.getLayoutX();
            double Ry = event.getY() + ci.getLayoutY();

            ci.setLayoutX(Rx);
            ci.setLayoutY(Ry);

            SetPoints(this.getLayoutX(), this.getLayoutY(), ci.getLayoutX(), ci.getLayoutY());

        });
        ci.setOnMouseReleased((event) -> {

            ci.setRadius(6);
            ci.setLayoutX(this.getLayoutX() + this.getWidth());
            ci.setLayoutY(this.getLayoutY() + this.getHeight());

            SetPoints(this.getLayoutX(), this.getLayoutY(), ci.getLayoutX(), ci.getLayoutY());
        });
    }

    public void AddToPopup() {
        if(AddToPopup == null)
            try {
                System.out.println("AddShapeTo");
                FXMLLoader loaderAddTo = new FXMLLoader(getClass().getResource("AddShapeTo.fxml"));
                this.AddToPopup = new JFXPopup(loaderAddTo.load());
                AddShapeToController controller2 = loaderAddTo.getController();
                controller2.setImage(image,this);
                
            } catch (IOException ex) {
                Logger.getLogger(DrawRectangle.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public void SetPoints(double x, double y, double i, double j) {

        if (x > i) {
            this.setLayoutX(i);
            this.setWidth(x - i);
        } else {
            this.setLayoutX(x);
            this.setWidth(i - x);
        }
        if (y > j) {
            this.setLayoutY(j);
            this.setHeight(y - j);
        } else {
            this.setLayoutY(y);
            this.setHeight(j - y);
        }
        ci.setLayoutX(getLayoutX() + getWidth());
        ci.setLayoutY(getLayoutY() + getHeight());

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
    public void MoveTo(double X, double Y) {

        if (X < 0) {
            X=0;
        }
        if (X + getWidth() > Parent.getWidth()) {
            X= Parent.getWidth() - getWidth();
        }

        if (Y < 0) {
            Y=0;
        }
        if (Y + getHeight() > Parent.getHeight()) {
            Y=Parent.getHeight() - getHeight();
        }

        this.setLayoutX(X);
        this.setLayoutY(Y);

        ci.setLayoutX(getLayoutX() + getWidth());
        ci.setLayoutY(getLayoutY() + getHeight());

    }

    @Override
    public void ScaleTo(double xK, double yK) {
    
    /*  Old_K * ow = W
        new_K * nw = W
        new_K * nw = Old_K * ow

        nw = Old_K  * ow / new_K    */
    
        double DX= this.xK/xK;
        double DY= this.yK/yK;
       
        this.xK =xK;
        this.yK =yK;
        
        setLayoutX(getLayoutX() * DX);
        setLayoutY(getLayoutY() * DY);
        setWidth(getWidth() * DX);
        setHeight(getHeight() * DY);

        ci.setLayoutX(getLayoutX() + getWidth());
        ci.setLayoutY(getLayoutY() + getHeight());

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
    public String toString() {
        return "Rectangle,"+getLayoutX()+","+getLayoutY()+","+getWidth()+","+getHeight()+","+xK+","+yK+"\r\n"; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addElement(Element addAttribute , Annotation note) {
        
        addAttribute.addElement("Rectangle")
                .addAttribute("Class", note.getClasse().getName())
                .addAttribute("LayoutX", String.valueOf(getLayoutX()*xK))
                .addAttribute("LayoutY", String.valueOf(getLayoutY()*yK))
                .addAttribute("Width", String.valueOf(getWidth()*xK))
                .addAttribute("Height", String.valueOf(getHeight()*yK));
    }
}
