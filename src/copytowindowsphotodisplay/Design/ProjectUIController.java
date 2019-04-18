/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Design;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import copytowindowsphotodisplay.Model.Project;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.Notifications;
import org.dom4j.DocumentException;

/**
 *
 * @author DaMi
 */
public class ProjectUIController implements Initializable {

    @FXML
    private AnchorPane MainPane;
    @FXML
    private StackPane NoteStackPane;
    @FXML
    private GridView<Project> Gridview;
    @FXML
    private AnchorPane RightPane;
    @FXML
    private AnchorPane LeftPane;
    @FXML
    private JFXButton OpenBtn;
    @FXML
    private JFXButton NewBtn;
    @FXML
    private JFXButton HelpBtn;
    @FXML
    private JFXButton SettingsBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Binding NotePane and Main Anchor
        MainPane.prefWidthProperty().bind(NoteStackPane.widthProperty());
        MainPane.prefHeightProperty().bind(NoteStackPane.heightProperty());

        GridViewINI(Gridview);

    }

    @FXML
    private void NewProject(ActionEvent event) {

        new Thread(() -> {
            try {
                // New Project PopUp INI
                
                final JFXDialog NewProject = new JFXDialog();
                AnchorPane load = FXMLLoader.load(getClass().getResource("NewFXML.fxml"),new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        
                        if(key=="p")
                            return NewProject;
                        if(key=="t")
                            return new Thread(() -> {
                                
                                ShowSidePane();
                            });
                        
                        return null;
                    }
                    
                    @Override
                    public Enumeration<String> getKeys() {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                
                NewProject.setDialogContainer(NoteStackPane);
                NewProject.setContent(load);
                NewProject.setTransitionType(JFXDialog.DialogTransition.TOP);
                
                NewProject.show();
                
                Notifications.create()
                        .position(Pos.BOTTOM_RIGHT)
                        .hideAfter(Duration.seconds(3))
                        .title("New")
                        .text("New Project is created")
                        .showInformation();
            } catch (IOException ex) {
                Logger.getLogger(ProjectUIController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).run();

    }

    @FXML
    private void Open(ActionEvent event) throws IOException, DocumentException {

        new Thread(() -> {
            Stage stage = new Stage();
            stage.setAlwaysOnTop(true);
            FileChooser fileChooser;
            // filechooser
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setInitialDirectory(
                    new File(System.getProperty("user.home"))
            );
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("Project filter", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            
            File URL = fileChooser.showOpenDialog(new Stage());
            
            if (URL == null) {
                return;
            }
            
            try {
                Project project = new Project(URL);
                
                ShowSidePane();
                
                Notifications.create()
                        .position(Pos.BOTTOM_RIGHT)
                        .hideAfter(Duration.seconds(3))
                        .title("Open")
                        .text("Project loaded")
                        .showInformation();
            } catch (Exception ex) {
                
                /*StackPane pane = new StackPane();
                pane.setPrefSize(200, 100);
                pane.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));
                Label label = new Label("existe");
                label.setFont(new Font(60));
                pane.getChildren().add(label);
                JFXDialog NewProject = new JFXDialog(NoteStackPane, pane, JFXDialog.DialogTransition.CENTER);
                
                NewProject.show();*/
            }
        }).run();
    }

    @FXML
    private void Settings(ActionEvent event) {

    }

    @FXML
    private void Help(ActionEvent event) {

    }

    private void GridViewINI(GridView<Project> Gridview) {

        Gridview.setItems(Project.LIST_OF_PROJECTS);
        Gridview.setVerticalCellSpacing(20);
        Gridview.setHorizontalCellSpacing(20);
        Gridview.setCellHeight(200);
        Gridview.setCellWidth(200);

        Gridview.setCellFactory((GridView<Project> arg0) -> new cell(Gridview, NoteStackPane));

    }

    public void ShowSidePane() {

        OpenBtn.setVisible(false);
        OpenBtn.setDisable(true);
        NewBtn.setVisible(false);
        NewBtn.setDisable(true);
        SettingsBtn.setVisible(false);
        SettingsBtn.setDisable(true);
        HelpBtn.setVisible(false);
        HelpBtn.setDisable(true);

        LeftPane.setVisible(true);
        LeftPane.setDisable(false);

        AnchorPane.setLeftAnchor(RightPane, LeftPane.getWidth());
    }

    public class cell extends GridCell<Project> {

        private final GridView<Project> Grid;

        private final StackPane NoteStack;

        private cell(GridView<Project> arg0, StackPane NoteStack) {

            Grid = arg0;
            this.NoteStack = NoteStack;
        }

        @Override
        protected void updateItem(Project item, boolean empty) {

            try {

                if (empty == true) {
                    return;
                }

                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                ProjectViewFXMLController controller;
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ProjectViewFXML.fxml"));
                    controller = new ProjectViewFXMLController(item, NoteStack);
                    loader.setController(controller);

                    AnchorPane root = loader.load();

                    /*
                    root.setPrefHeight(Grid.getCellHeight());
                    root.setPrefWidth(Grid.getCellWidth());
                     */
                    setGraphic(root);
                } catch (URISyntaxException ex) {
                    Logger.getLogger(ProjectUIController.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (IOException ex) {
            }
        }

    }

}
