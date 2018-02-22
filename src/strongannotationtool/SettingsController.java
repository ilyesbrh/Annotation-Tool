/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strongannotationtool;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import org.controlsfx.control.SnapshotView;
import strongannotationtool.Shapes.CustomGrid;
import strongannotationtool.Shapes.CustomRectangle;

/**
 * FXML Controller class
 *
 * @author ilies
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTabPane JFXSwitchPane;
    @FXML
    private Tab mouseSettings;
    @FXML
    private Tab Grid;
    @FXML
    private Tab Grid1;
    @FXML
    private Tab Grid11;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Initializes the controller class.
     */
    /*@Override
    public void initialize(URL url, ResourceBundle rb) {
    
    mouseSettings.setContent(createVisualizationControl(MainCTR.main.ShowArea));
    Grid.setContent(createVisualizationControl());
    
    }
    
    private static Node createVisualizationControl(SnapshotView snapshotView) {
    GridPane grid = new GridPane();
    grid.setVgap(5);
    grid.setHgap(5);
    grid.setPadding(new Insets(5));
    
    int row = 0;
    
    // selection fill color
    JFXColorPicker selectionFillPicker = new JFXColorPicker((javafx.scene.paint.Color) (Paint) snapshotView.getSelectionAreaFill());
    snapshotView.selectionAreaFillProperty().bind(selectionFillPicker.valueProperty());
    grid.addRow(row++, new Label("Fill Color:"), selectionFillPicker);
    
    // selection border color
    JFXColorPicker selectionBorderPaintPicker = new JFXColorPicker((javafx.scene.paint.Color) (Paint) snapshotView.getSelectionBorderPaint());
    snapshotView.selectionBorderPaintProperty().bind(selectionBorderPaintPicker.valueProperty());
    grid.addRow(row++, new Label("Stroke Color:"), selectionBorderPaintPicker);
    
    // selection border width
    Slider selectionStrokeWidth = new Slider(0, 25, snapshotView.getSelectionBorderWidth());
    snapshotView.selectionBorderWidthProperty().bindBidirectional(selectionStrokeWidth.valueProperty());
    grid.addRow(row++, new Label("Stroke Width:"), selectionStrokeWidth);
    
    // unselected area fill color
    JFXColorPicker unselectedAreaFillPicker = new JFXColorPicker((javafx.scene.paint.Color) (Paint) snapshotView.getUnselectedAreaFill());
    snapshotView.unselectedAreaFillProperty().bind(unselectedAreaFillPicker.valueProperty());
    grid.addRow(row++, new Label("Outer Color:"), unselectedAreaFillPicker);
    
    return grid;
    }
    private static Node createVisualizationControl(CustomGrid rectangle) {
    
    GridPane grid = new GridPane();
    grid.setVgap(5);
    grid.setHgap(5);
    grid.setPadding(new Insets(5));
    
    int row = 0;
    
    // selection fill color
    JFXColorPicker selectionFillPicker = new JFXColorPicker(rectangle.GetSlectedColor());
    selectionFillPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
    
    rectangle.setSelectedColor(newValue);
    
    });
    grid.addRow(row++, new Label("Fill Color:"), selectionFillPicker);
    
    // selection border color
    JFXColorPicker selectionBorderPaintPicker = new JFXColorPicker(rectangle.GetStrockColor());
    selectionBorderPaintPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
    
    rectangle.setStrockColor(newValue);
    
    });
    grid.addRow(row++, new Label("Stroke Color:"), selectionBorderPaintPicker);
    
    // selection X number width
    Slider selectionStrokeWidth = new Slider(0, 20, 6);
    selectionStrokeWidth.setShowTickMarks(true);
    selectionStrokeWidth.setShowTickLabels(true);
    
    selectionStrokeWidth.valueProperty().addListener((observable, oldValue, newValue) -> {
    
    int X = newValue.intValue();
    rectangle.DeletGrid(GridCellArray, anchorPane);
    rectangle.CreatGrid(anchorPane, imageview, X, GridHeight);
    
    });
    grid.addRow(row++, new Label("width:"), selectionStrokeWidth);
    
    // selection Y number width
    Slider selectionStrokeHeight = new Slider(0, 20, 6);
    selectionStrokeHeight.setShowTickMarks(true);
    selectionStrokeHeight.setShowTickLabels(true);
    
    selectionStrokeHeight.valueProperty().addListener((observable, oldValue, newValue) -> {
    
    int Y = newValue.intValue();
    rectangle.DeletGrid(GridCellArray, anchorPane);
    rectangle.CreatGrid(anchorPane, imageview,GridWidth,Y);
    
    });
    grid.addRow(row++, new Label("Height:"), selectionStrokeHeight);
    
    
    return grid;
    }
    */
}
