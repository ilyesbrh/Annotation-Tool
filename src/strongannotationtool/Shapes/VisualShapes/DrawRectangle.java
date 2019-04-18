/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import com.jfoenix.controls.JFXPopup;
import copytowindowsphotodisplay.Model.CustomResourceBundle;
import copytowindowsphotodisplay.Model.Images;
import copytowindowsphotodisplay.Model.Project;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.dom4j.Element;
import strongannotationtool.Shapes.Model.CustomRectangle;

/**
 *
 * @author ilies
 */
public class DrawRectangle extends CustomRectangle implements DrawableShape {

        
    //Info
    
        //Image
    
        Element shapeElement;
        
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
    AddShapeToController controller2 = null;

    private double centerX;
    private double centerY;
    private Tooltip TT = new Tooltip();
    private FocusRect focus;

    
    public DrawRectangle(double x, double y, double i, double j,double xk,double yk,Images image) throws IOException {

        
        shapeElement = image.imageElement.addElement("Shape")
                .addAttribute("Type", "Rectangle")
                .addAttribute("LayoutX", "")
                .addAttribute("LayoutY", "")
                .addAttribute("Width", "")
                .addAttribute("Height", "");
        xK=xk;
        yK=yk;
        
        ViewINI(x, y, i, j);
        ShapeEventsHandle();
        //CircleEventHandle();
    }
    public DrawRectangle(Element S) {
        
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
        //CircleEventHandle(); 
    }

    private void ViewINI(double x, double y, double i, double j) {
        //ci = new Circle(6, Color.rgb(114, 137, 218, 0.6));
        //ci.setEffect(new DropShadow());

        setFill(Color.web("blue", 0.4));
        SetPoints(x, y, i, j);
        

        //ci.setLayoutX(this.getLayoutX() + this.getWidth());
        //ci.setLayoutY(this.getLayoutY() + this.getHeight());
        
    }   
    public void AddToPopup() {
        if(AddToPopup == null)
            try {
                System.out.println("AddShapeTo");
                
                CustomResourceBundle customResourceBundle = new CustomResourceBundle();
                customResourceBundle.addObject("element", this);
                FXMLLoader loaderAddTo = new FXMLLoader(getClass().getResource("AddShapeTo.fxml"),customResourceBundle);
                controller2 = new AddShapeToController();
                loaderAddTo.setController(controller2);
                this.AddToPopup = new JFXPopup(loaderAddTo.load());
                
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

    private void CircleEventHandle() {
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
    private void ShapeEventsHandle() {
        //Rectangle Actions
        
        setOnMouseClicked((event) -> {
            
            if (event.getButton() == MouseButton.SECONDARY) {
                if(OptionPopup == null)
                    try {
                        FXMLLoader loaderOption = new FXMLLoader(getClass().getResource("RectOption.fxml"));
                        this.OptionPopup = new JFXPopup(loaderOption.load());
                        RectOptionCTR controller = loaderOption.getController();
                        controller.setShape(this);
                        
                    } catch (Exception e) {
                    }
                
                OptionPopup.show(this);
            }
            if(event.getClickCount()==2){
                
                AddToPopup();
                AddToPopup.show(this);
            }else{
                if(!focus.isFocused(this))
                    focus.focusOn(this);
            }
        });
        
        setOnMousePressed((event) -> {
            
            if (event.getButton() != MouseButton.SECONDARY) {

                X = event.getScreenX();
                Y = event.getScreenY();
                centerX = getLayoutX();
                centerY = getLayoutY();

            }
        });
        
        setOnMouseDragged((event) -> {
            
            double deltaX = X - event.getScreenX();
            double deltaY = Y - event.getScreenY();
            
            MoveTo(centerX - deltaX, centerY - deltaY);
            
        });
        setOnMouseReleased((event) -> {
            
        });
        setOnMouseEntered((event) -> {
            TT.setText("");
            for (Element element : shapeElement.elements()) {
                
                TT.setText(TT.getText()+Project.getClassName(element.attribute(0).getValue(), element)+'\n');
            }
            
            if(!TT.getText().isEmpty())
                TT.show(this, event.getScreenX()-event.getX()+getWidth(), event.getScreenY()-event.getY());
        });
        setOnMouseExited((event) -> {
            TT.hide();
        });
    }

    @Override
    public void SetPoints(double x, double y, double i, double j) {

        
        this.setLayoutX(Math.min(x, i));
        this.setWidth(Math.abs(x - i));
        this.setLayoutY(Math.min(y, j));
        this.setHeight(Math.abs(y - j));
        
        
        shapeElement.attribute(1).setValue(String.valueOf(getLayoutX()*xK));
        shapeElement.attribute(2).setValue(String.valueOf(getLayoutY()*yK));
        shapeElement.attribute(3).setValue(String.valueOf(getWidth()*xK));
        shapeElement.attribute(4).setValue(String.valueOf(getHeight()*yK));
        
        //ci.setLayoutX(getLayoutX() + getWidth());
        //ci.setLayoutY(getLayoutY() + getHeight());

    }

    @Override
    public void AddTo(AnchorPane p,FocusRect f) {
        
        if(!p.getChildren().contains(this))
            p.getChildren().add(this);
        //if(!p.getChildren().contains(ci))
        //    p.getChildren().add(ci);
        this.Parent = p;
        this.focus =f;
    }

    @Override
    public void MoveTo(double X, double Y) {

        if (X < 0) {
            X=0;
        }
        if (X + getWidth() > Parent.getWidth()) {
            X= Parent.getWidth() - getWidth()-1;
        }

        if (Y < 0) {
            Y=0;
        }
        if (Y + getHeight() > Parent.getHeight()) {
            Y=Parent.getHeight() - getHeight()-1;
        }

        this.setLayoutX(X);
        this.setLayoutY(Y);
        shapeElement.attribute(1).setValue(String.valueOf(getLayoutX()*xK));
        shapeElement.attribute(2).setValue(String.valueOf(getLayoutY()*yK));
        

        //ci.setLayoutX(getLayoutX() + getWidth()-5);
        //ci.setLayoutY(getLayoutY() + getHeight()-5);

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
        
        shapeElement.attribute(1).setValue(String.valueOf(getLayoutX()*xK));
        shapeElement.attribute(2).setValue(String.valueOf(getLayoutY()*yK));
        shapeElement.attribute(3).setValue(String.valueOf(getWidth()*xK));
        shapeElement.attribute(4).setValue(String.valueOf(getHeight()*yK));
        

        //ci.setLayoutX(getLayoutX() + getWidth());
        //ci.setLayoutY(getLayoutY() + getHeight());

    }

    @Override
    public void remove(AnchorPane p) {
        
        p.getChildren().remove(this);
        //p.getChildren().remove(ci);
        shapeElement.getParent().remove(shapeElement);
        focus.removeFrom(focus.imgparent);
        
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
        return "Rectangle,"+getLayoutX()+","+getLayoutY()+","+getWidth()+","+getHeight()+","+xK+","+yK+"\r\n"; //To change body of generated methods, choose Tools | Templates.
    }
}
