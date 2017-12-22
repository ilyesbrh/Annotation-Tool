/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool;

import strongannotationtool.Shapes.CustomGrid;
import com.jfoenix.controls.JFXSlider;
import copytowindowsphotodisplay.Model.Annotation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author DaMi
 */
public class AnnotationPaneController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView img;
    @FXML
    private AnchorPane imgParent;
    @FXML
    private JFXSlider Slider;

    Annotation annotation;
    
    private  final CustomGrid grid=new CustomGrid();
    
    double ZoomFactor;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Image image = new Image(new FileChooser().showOpenDialog(new Stage()).toURI().toString(),0,0,false,false);
        
        img.setImage(image);
        
        Slider.valueProperty().addListener((o,old,New) -> {
            
            if (Slider.getValue() == 0) {
                double width = imgParent.getWidth() / image.getWidth();
                double height = imgParent.getHeight() / image.getHeight();
                
                if (width < height) {
                    
                    ZoomFactor = width;
                    
                } else {
                    
                    ZoomFactor = height;
                    
                }
            }else
                ZoomFactor = New.doubleValue()/100;
            
            refreshImage(ZoomFactor);
            RefreshGrid();
        });
        
        imgParent.widthProperty().addListener((observable, oldValue, newValue) -> {
            
            if (Slider.getValue() == 0) {
                double width = newValue.doubleValue() / image.getWidth();
                double height = imgParent.getHeight() / image.getHeight();
                
                if (width < height) {
                    
                    ZoomFactor = width;
                    
                } else {
                    
                    ZoomFactor = height;
                    
                }
            }

            refreshImage(ZoomFactor);
            RefreshGrid();
        });
        
        imgParent.heightProperty().addListener((observable, oldValue, newValue) -> {
            
            if (Slider.getValue() == 0) {
                double width = imgParent.getWidth() / image.getWidth();
                double height = newValue.doubleValue() / image.getHeight();
                
                if (width < height) {
                    
                    ZoomFactor = width;
                    
                } else {
                    
                    ZoomFactor = height;
                    
                }
            }
            
            refreshImage(ZoomFactor);
            RefreshGrid();
            
        });
        
        // Grid INI
        
        grid.setFillColor(Color.TRANSPARENT);
        grid.setHrectNumber(25);
        grid.setVrectNumber(40);
        grid.setStartX(img.getLayoutX());
        grid.setStartY(img.getLayoutY());
        grid.setWidth(img.getFitWidth());
        grid.setHeight(img.getFitHeight());
        grid.addGrid(imgParent);
        
        //Slider INI 
        
        Slider.setValue(0);
        
    }    

    public void RefreshGrid() {
        grid.setStartX(img.getLayoutX());
        grid.setStartY(img.getLayoutY());
        grid.setWidth(img.getFitWidth());
        grid.setHeight(img.getFitHeight());
        grid.update();
    }
    
    public void refreshImage(double k) {
        
        double newWidth = k*img.getImage().getWidth();
        double newHeight = k*img.getImage().getHeight();
        
        if(newWidth > imgParent.getWidth() && newHeight > imgParent.getHeight()){
            
            img.setLayoutX(0);
            img.setFitWidth(imgParent.getWidth());
            
            img.setLayoutY(0);
            img.setFitHeight(imgParent.getHeight());
            
            img.setViewport(new Rectangle2D(0, 0, img.getFitWidth()/k, img.getFitHeight()/k));
            
        }else if(newWidth <= imgParent.getWidth() && newHeight <= imgParent.getHeight()){
            
            img.setFitWidth(newWidth);
            double x=(imgParent.getWidth()/2)-(img.getFitWidth()/2);
            img.setLayoutX(x);
            
            img.setFitHeight(newHeight);
            double y=(imgParent.getHeight()/2)-(img.getFitHeight()/2);
            img.setLayoutY(y);
            
            img.setViewport(new Rectangle2D(0, 0, img.getImage().getWidth(), img.getImage().getHeight()));
            
        }else if(newWidth > imgParent.getWidth() && newHeight <= imgParent.getHeight()){
            
            img.setLayoutX(0);
            img.setFitWidth(imgParent.getWidth());
            
            img.setFitHeight(newHeight);
            double y=(imgParent.getHeight()/2)-(img.getFitHeight()/2);
            img.setLayoutY(y);
            
            img.setViewport(new Rectangle2D(0, 0, img.getFitWidth()/k, img.getImage().getHeight()));
            
            
        }else if(newWidth <= imgParent.getWidth() && newHeight > imgParent.getHeight()){
            
            img.setLayoutY(0);
            img.setFitHeight(imgParent.getHeight());
            
            img.setFitWidth(newWidth);
            double x=(imgParent.getWidth()/2)-(img.getFitWidth()/2);
            img.setLayoutX(x);
            
            img.setViewport(new Rectangle2D(0, 0,img.getImage().getWidth(),img.getFitHeight()/k));
            
        }
    }    

    @FXML
    private void ImgMouseReleased(MouseEvent event) {
    }

    @FXML
    private void ImgMouseDragged(MouseEvent event) {
    }

    @FXML
    private void ImgMousePressed(MouseEvent event) {
    }
}
