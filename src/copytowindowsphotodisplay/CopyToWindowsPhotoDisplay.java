/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author DaMi
 */
public class CopyToWindowsPhotoDisplay extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("ProjectUI.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(getClass().getResource("Annotation.png").toURI().toString()));
        stage.setTitle("Anotation Tool");
        stage.setScene(scene);
        stage.setWidth(650);
        stage.setHeight(600);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
