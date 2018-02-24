/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import strongannotationtool.Shapes.CustomRectangle;

/**
 *
 * @author ilies
 */
public class DrawRectangle extends CustomRectangle implements DrawableShape {

    private Circle ci;
    private AnchorPane Parent;
    //state
    private double X;
    private double Y;
    private double LX;
    private double LY;
    //UI
    private JFXPopup fXPopup;
    
    public DrawRectangle(double x, double y, double i, double j) throws IOException {

        ci = new Circle(20, Color.GREENYELLOW);
        ci.setEffect(new DropShadow());

        SetPoints(x, y, i, j);

        ci.setLayoutX(this.getLayoutX() + this.getWidth());
        ci.setLayoutY(this.getLayoutY() + this.getHeight());

        //Rectangle Actions
        
        FXMLLoader FXL=new FXMLLoader(getClass().getResource("SnackBar.fxml"));
        SnackBarController controller = FXL.getController();
        
        this.fXPopup = new JFXPopup(FXL.load());
        
        setOnMouseClicked((event) -> {

            if (event.getButton() == MouseButton.SECONDARY) {

                
                JFXButton source = (JFXButton) event.getSource();
                fXPopup.show(this);

            }

        });

        setOnMousePressed((event) -> {

            X = event.getScreenX();
            Y = event.getScreenY();
            LX = getLayoutX();
            LY = getLayoutY();
        });

        setOnMouseDragged((event) -> {

            double deltaX = X - event.getScreenX();
            double deltaY = Y - event.getScreenY();

            MoveTo(getLayoutX() - deltaX, getLayoutY() - deltaY);

            X = event.getScreenX();
            Y = event.getScreenY();

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

            if (Rx < 0 || Rx > Parent.getWidth()) {
                return;
            }
            if (Ry < 0 || Ry > Parent.getHeight()) {
                return;
            }

            ci.setLayoutX(Rx);
            ci.setLayoutY(Ry);

            SetPoints(this.getLayoutX(), this.getLayoutY(), ci.getLayoutX(), ci.getLayoutY());

        });
        ci.setOnMouseReleased((event) -> {

            if (event.getX() + ci.getLayoutX() < 0 || event.getX() > Parent.getWidth()) {
                return;
            }
            if (event.getY() + ci.getLayoutY() < 0 || event.getY() > Parent.getHeight()) {
                return;
            }

            ci.setRadius(10);
            ci.setLayoutX(this.getLayoutX() + this.getWidth());
            ci.setLayoutY(this.getLayoutY() + this.getHeight());

            SetPoints(this.getLayoutX(), this.getLayoutY(), ci.getLayoutX(), ci.getLayoutY());
        });
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
        p.getChildren().add(this);
        p.getChildren().add(ci);
        this.Parent = p;
    }

    @Override
    public void MoveTo(double X, double Y) {

        if (X < 0) {
            return;
        }
        if (X + getWidth() > Parent.getWidth()) {
            return;
        }

        if (Y < 0) {
            return;
        }
        if (Y + getHeight() > Parent.getHeight()) {
            return;
        }

        this.setLayoutX(X);
        this.setLayoutY(Y);

        ci.setLayoutX(getLayoutX() + getWidth());
        ci.setLayoutY(getLayoutY() + getHeight());

    }

    @Override
    public void ScaleTo(double xK, double yK) {

        setLayoutX(getLayoutX() * xK);
        setLayoutY(getLayoutY() * yK);
        setWidth(getWidth() * xK);
        setHeight(getHeight() * yK);

        ci.setLayoutX(getLayoutX() + getWidth());
        ci.setLayoutY(getLayoutY() + getHeight());

    }

    @Override
    public void remove(AnchorPane p) {
        p.getChildren().remove(this);
        p.getChildren().remove(ci);
    }

}
