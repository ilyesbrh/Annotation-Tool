/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool.Shapes.Model;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 *
 * @author brhilyes
 */
public class CustomGrid{

    private AnchorPane parent;

    private CustomRectangle RectGrid[][];

    private double StartX = 0;
    private double StartY = 0;

    private int VrectNumber = 25;
    private int HrectNumber = 15;

    private double Width = 0;
    private double Height = 0;

    private Color StrokeColor = Color.BLACK;
    private Color FillColor = Color.gray(0.6, 0.6);
    
    private boolean changed = false;

    public CustomRectangle[][] getRectGrid() {
        return RectGrid;
    }

    public void setRectGrid(CustomRectangle[][] RectGrid) {
        this.RectGrid = RectGrid;
    }

    public double getStartX() {
        return StartX;
    }

    public void setStartX(double StartX) {
        this.StartX = StartX;
    }

    public double getStartY() {
        return StartY;
    }

    public void setStartY(double StartY) {
        this.StartY = StartY;
    }

    public int getVrectNumber() {
        return VrectNumber;
    }

    public void setVrectNumber(int VrectNumber) {
        this.VrectNumber = VrectNumber;
        this.changed = true;
    }

    public int getHrectNumber() {
        return HrectNumber;
    }

    public void setHrectNumber(int HrectNumber) {
        this.HrectNumber = HrectNumber;
        this.changed = true;
    }

    public Double getWidth() {
        return Width;
    }

    public void setWidth(double Width) {
        this.Width = Width;
    }

    public double getHeight() {
        return Height;
    }

    public void setHeight(double Height) {
        this.Height = Height;
    }

    public Color getStrokeColor() {
        return StrokeColor;
    }

    public void setStrokeColor(Color StrokeColor) {
        this.StrokeColor = StrokeColor;
    }

    public Color getFillColor() {
        return FillColor;
    }

    public void setFillColor(Color FillColor) {
        this.FillColor = FillColor;
    }

    public void addGrid(AnchorPane parent) {

        this.parent = parent;

        RectGrid = new CustomRectangle[HrectNumber][VrectNumber];

        for (int i = 0; i < RectGrid.length; i++) {
            for (int j = 0; j < RectGrid[0].length; j++) {

                double rectWidth = Width / VrectNumber;
                double rectHeight = Height / HrectNumber;

                RectGrid[i][j] = new CustomRectangle(StartX + rectWidth * j, StartY + rectHeight * i, rectWidth, rectHeight, FillColor, false);

                RectGrid[i][j].setStroke(StrokeColor);
                
                RectGrid[i][j].setMouseTransparent(true);
                
                RectGrid[i][j].setOnMouseClicked( e-> {
                    System.err.println(e.getX());
                });

                parent.getChildren().add(RectGrid[i][j]);

            }
        }

    }

    public void update() {

        if (changed) {
            FullUpdate();
        } else {
            SemiUpdate();
        }
        changed = false;

    }

    public void SemiUpdate() {

        for (int i = 0; i < RectGrid.length; i++) {
            for (int j = 0; j < RectGrid[0].length; j++) {

                double rectWidth = Width / VrectNumber;
                double rectHeight = Height / HrectNumber;

                RectGrid[i][j].setLayoutX(StartX + rectWidth * j);
                RectGrid[i][j].setLayoutY(StartY + rectHeight * i);
                RectGrid[i][j].setWidth(rectWidth);
                RectGrid[i][j].setHeight(rectHeight);
                RectGrid[i][j].setRectFill(FillColor);
                RectGrid[i][j].setStroke(StrokeColor);

            }
        }

    }

    public void FullUpdate() {

        for (int i = 0; i < RectGrid.length; i++) {
            for (int j = 0; j < RectGrid[0].length; j++) {

                parent.getChildren().remove(RectGrid[i][j]);

            }
        }

        RectGrid = new CustomRectangle[HrectNumber][VrectNumber];

        int index= 1;
        for (int i = 0; i < RectGrid.length; i++) {
            for (int j = 0; j < RectGrid[0].length; j++) {

                double rectWidth = Width / VrectNumber;
                double rectHeight = Height / HrectNumber;

                RectGrid[i][j] = new CustomRectangle(StartX + rectWidth * j, StartY + rectHeight * i, rectWidth, rectHeight);

                RectGrid[i][j].setRectFill(FillColor);
                RectGrid[i][j].setStroke(StrokeColor);

                parent.getChildren().add( index, RectGrid[i][j] );

            }
        }
    }

    public void removeGrid() {
        for (CustomRectangle[] RectGrid1 : RectGrid) {
            for (CustomRectangle item : RectGrid1) {
                parent.getChildren().remove(item);
            }
        }
        parent = null;
    }

}
