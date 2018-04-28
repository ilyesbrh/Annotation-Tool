/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.VisualShapes;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import strongannotationtool.Shapes.CustomRectangle;

/**
 *
 * @author ilies
 */
public class FocusRect extends CustomRectangle {

    public AnchorPane imgparent;

    Rectangle a = new Rectangle();// a........b...........c
    Rectangle b = new Rectangle();// ......................
    Rectangle c = new Rectangle();// ......................
    Rectangle d = new Rectangle();// h....................d
    Rectangle e = new Rectangle();// ......................
    Rectangle f = new Rectangle();// ......................
    Rectangle g = new Rectangle();// g........f...........e
    Rectangle h = new Rectangle();

    public DrawableShape shapeOnFocus;

    public FocusRect(AnchorPane imgParent) {

        this.imgparent = imgParent;
        setManaged(false);

        moveRectIni(a, Cursor.SE_RESIZE);
        moveRectIni(e, Cursor.SE_RESIZE);
        moveRectIni(b, Cursor.V_RESIZE);
        moveRectIni(f, Cursor.V_RESIZE);
        moveRectIni(c, Cursor.NE_RESIZE);
        moveRectIni(g, Cursor.NE_RESIZE);
        moveRectIni(d, Cursor.H_RESIZE);
        moveRectIni(h, Cursor.H_RESIZE);

        ResizingActions();

        this.setFill(Color.color(0, 0, 0, 0.2));
        this.setStroke(Color.CHARTREUSE);
        this.setStrokeWidth(2);
        setStrokeType(StrokeType.INSIDE);
        setStyle("-fx-stroke-dash-array :5 ;");

    }

    private void ResizingActions() {

        SetOnAction(a, b, c, d, e, f, g, h);

        a.setOnMousePressed((event) -> {

            pressAction(event);
        });
        a.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Rx = event.getX() + S.getLayoutX();
            Double Ry = event.getY() + S.getLayoutY();

            S.setLayoutX(Rx);
            S.setLayoutY(Ry);

            shapeOnFocus.SetPoints(
                    a.getLayoutX(),
                    a.getLayoutY(),
                    e.getLayoutX() + e.getWidth(),
                    e.getLayoutY() + e.getWidth());

        });
        b.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Ry = event.getY() + S.getLayoutY();

            S.setLayoutY(Ry);

            shapeOnFocus.SetPoints(
                    a.getLayoutX(),
                    S.getLayoutY(),
                    e.getLayoutX() + e.getWidth(),
                    e.getLayoutY() + e.getWidth());

        });
        c.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Rx = event.getX() + S.getLayoutX();
            Double Ry = event.getY() + S.getLayoutY();

            S.setLayoutX(Rx);
            S.setLayoutY(Ry);

            shapeOnFocus.SetPoints(
                    a.getLayoutX(),
                    S.getLayoutY(),
                    S.getLayoutX() + e.getWidth(),
                    e.getLayoutY() + e.getWidth());

        });
        d.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Rx = event.getX() + S.getLayoutX();
            Double Ry = event.getY() + S.getLayoutY();

            S.setLayoutX(Rx);

            shapeOnFocus.SetPoints(
                    a.getLayoutX(),
                    a.getLayoutY(),
                    S.getLayoutX() + e.getWidth(),
                    e.getLayoutY() + e.getWidth());

        });
        e.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Rx = event.getX() + S.getLayoutX();
            Double Ry = event.getY() + S.getLayoutY();

            S.setLayoutX(Rx);
            S.setLayoutY(Ry);

            shapeOnFocus.SetPoints(
                    a.getLayoutX(),
                    a.getLayoutY(),
                    e.getLayoutX() + e.getWidth(),
                    e.getLayoutY() + e.getWidth());

        });
        f.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Rx = event.getX() + S.getLayoutX();
            Double Ry = event.getY() + S.getLayoutY();

            S.setLayoutX(Rx);
            S.setLayoutY(Ry);

            shapeOnFocus.SetPoints(
                    a.getLayoutX(),
                    a.getLayoutY(),
                    e.getLayoutX() + e.getWidth(),
                    S.getLayoutY() + e.getWidth());

        });
        g.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Rx = event.getX() + S.getLayoutX();
            Double Ry = event.getY() + S.getLayoutY();

            S.setLayoutX(Rx);
            S.setLayoutY(Ry);

            shapeOnFocus.SetPoints(
                    S.getLayoutX(),
                    a.getLayoutY(),
                    e.getLayoutX() + e.getWidth(),
                    S.getLayoutY() + e.getWidth());

        });
        h.setOnMouseDragged((event) -> {

            Rectangle S = (Rectangle) event.getSource();

            Double Rx = event.getX() + S.getLayoutX();

            S.setLayoutX(Rx);

            shapeOnFocus.SetPoints(
                    S.getLayoutX(),
                    a.getLayoutY(),
                    e.getLayoutX() + e.getWidth(),
                    e.getLayoutY() + e.getWidth());

        });

    }

    private void moveRectIni(Rectangle x, Cursor RESIZE) {

        x.setWidth(5);
        x.setHeight(5);
        x.setFill(Color.WHITE);
        x.setStroke(Color.BLACK);
        x.setCursor(RESIZE);
        x.setManaged(false);
    }

    public void focusOn(DrawCircle shape) {

        shapeOnFocus = shape;

        imgparent.getChildren().remove(shape);

        removeFrom(imgparent);

        addTo(imgparent);

        imgparent.getChildren().add(imgparent.getChildren().size() - 8, shape);

        unbindIt(this);

        this.layoutXProperty().bind(shape.layoutXProperty().subtract(shape.radiusXProperty()));
        this.layoutYProperty().bind(shape.layoutYProperty().subtract(shape.radiusYProperty()));
        this.widthProperty().bind(shape.radiusXProperty().multiply(2));
        this.heightProperty().bind(shape.radiusYProperty().multiply(2));

        ResizingBind(a, b, c, d, e, f, g, h);

    }

    public synchronized void focusOn(DrawRectangle shape) {

        shapeOnFocus = shape;

        imgparent.getChildren().remove(shape);

        removeFrom(imgparent);

        addTo(imgparent);

        imgparent.getChildren().add(imgparent.getChildren().size() - 8, shape);

        unbindIt(this);

        this.layoutXProperty().bind(shape.layoutXProperty());
        this.layoutYProperty().bind(shape.layoutYProperty());
        this.widthProperty().bind(shape.widthProperty());
        this.heightProperty().bind(shape.heightProperty());

        ResizingBind(a, b, c, d, e, f, g, h);

    }

    private void ResizingBind(Rectangle... rectangle) {

        for (Rectangle rect : rectangle) {

            if (rect.equals(a)) {

                a.layoutXProperty().bind(layoutXProperty());
                a.layoutYProperty().bind(layoutYProperty());

            } else if (rect.equals(b)) {

                b.layoutXProperty().bind(layoutXProperty().add(widthProperty().divide(2)));
                b.layoutYProperty().bind(layoutYProperty());

            } else if (rect.equals(c)) {

                c.layoutXProperty().bind(layoutXProperty().add(widthProperty().subtract(c.widthProperty())));
                c.layoutYProperty().bind(layoutYProperty());

            } else if (rect.equals(d)) {

                d.layoutXProperty().bind(layoutXProperty().add(widthProperty().subtract(d.widthProperty())));
                d.layoutYProperty().bind(layoutYProperty().add(heightProperty().divide(2)));

            } else if (rect.equals(e)) {

                e.layoutXProperty().bind(layoutXProperty().add(widthProperty().subtract(e.widthProperty())));
                e.layoutYProperty().bind(layoutYProperty().add(heightProperty().subtract(e.heightProperty())));

            } else if (rect.equals(f)) {

                f.layoutXProperty().bind(layoutXProperty().add(widthProperty().divide(2)));
                f.layoutYProperty().bind(layoutYProperty().add(heightProperty().subtract(f.heightProperty())));

            } else if (rect.equals(g)) {

                g.layoutXProperty().bind(layoutXProperty());
                g.layoutYProperty().bind(layoutYProperty().add(heightProperty().subtract(g.heightProperty())));

            } else if (rect.equals(h)) {

                h.layoutXProperty().bind(layoutXProperty());
                h.layoutYProperty().bind(layoutYProperty().add(heightProperty().divide(2)));
            }
        }
    }

    public boolean isFocused(DrawableShape aThis) {

        return aThis.equals(shapeOnFocus);
    }

    public void addTo(AnchorPane imgParent) {

        AddIt(imgParent, this, a, b, c, d, e, f, g, h);

    }

    private void AddIt(AnchorPane imgParent, Rectangle... rectangle) {
        for (Rectangle rect : rectangle) {
            if (!imgParent.getChildren().contains(rect)) {
                imgParent.getChildren().add(rect);
            }

        }
    }

    public void removeFrom(AnchorPane imgParent) {

        unbindIt(a, b, c, d, e, f, g, h, this);

        RemoveIt(imgParent, this, a, b, c, d, e, f, g, h);

    }

    private void RemoveIt(AnchorPane imgParent, Rectangle... rect) {

        for (Rectangle rectangle : rect) {
            if (imgParent.getChildren().contains(rectangle)) {
                imgParent.getChildren().remove(rectangle);
            }
        }
    }

    private void unbindIt(Rectangle... messages) {
        for (Rectangle message : messages) {

            unbindIt(message);
        }
    }

    private void unbindIt(Rectangle p) {
        p.widthProperty().unbind();
        p.heightProperty().unbind();
        p.layoutXProperty().unbind();
        p.layoutYProperty().unbind();
    }

    private void SetOnAction(Rectangle... rectangles) {

        for (Rectangle rectangle : rectangles) {

            rectangle.addEventFilter(MouseEvent.MOUSE_PRESSED, (event) -> {
                pressAction(event);
            });
            rectangle.addEventFilter(MouseEvent.MOUSE_RELEASED, (event) -> {
                releaseAction(event);
            });
        }
    }

    private void pressAction(MouseEvent event) {
        Rectangle source = (Rectangle) event.getSource();
        System.out.println("here");
        source.layoutXProperty().unbind();
        source.layoutYProperty().unbind();
    }

    private void releaseAction(MouseEvent event) {
        Rectangle source = (Rectangle) event.getSource();

        source.layoutXProperty().unbind();
        source.layoutYProperty().unbind();

        ResizingBind(source);
    }

}
