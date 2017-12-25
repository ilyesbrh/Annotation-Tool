/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;

/**
 *
 * @author DaMi
 */
public class Project {

   
    public static final ObservableList<Project> LIST_OF_PROJECTS = FXCollections.observableArrayList();

    public final ObservableList<Class> CLASSES = FXCollections.observableArrayList();

    public final ObservableList<Images> IMAGES = FXCollections.observableArrayList();

    private Image image;

    private String Name;

    private File Directory;

    private Boolean AutoSave;

    private Boolean SaveImages;

    public Project(String Name, Image image, File directory, Boolean AutoSave, Boolean SaveImages) throws IOException {

        Directory = directory;

        this.Name = Name;

        this.image = image;

        this.AutoSave = AutoSave;

        this.SaveImages = SaveImages;

        LIST_OF_PROJECTS.add(this);

        Save();
        
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public File getDirectory() {
        return Directory;
    }

    public void setDirectory(File Directory) {
        this.Directory = Directory;
    }

    public Boolean getAutoSave() {
        return AutoSave;
    }

    public void setAutoSave(Boolean AutoSave) {
        this.AutoSave = AutoSave;
    }

    public Boolean getSaveImages() {
        return SaveImages;
    }

    public void setSaveImages(Boolean SaveImages) {
        this.SaveImages = SaveImages;
    }

    private void Save() throws IOException {

        Directory = new File(Directory, Name);

        if (!Directory.exists()) {
            Directory.mkdir();
        }

        File images = new File(Directory, "Images");
        if (!images.exists()) {
            images.mkdir();
        }

        File Classified = new File(Directory, "Classified");
        if (!Classified.exists()) {
            Classified.mkdir();
        }

        File Settings = new File(Directory, "Settings");
        if (!Settings.exists()) {
            Settings.mkdir();
        }

        // saving Image of project
        saveImageToFile(image, Settings, "Image.jpg");

        File configFile = new File(Settings, "config.txt");
        if (!configFile.exists()) {
            configFile.createNewFile();
        }

        FileWriter fw = new FileWriter(configFile, false);
        fw.write(this.AutoSave.toString() + '\n');
        fw.write(this.SaveImages.toString() + '\n');
        fw.close();

        for(Class x:CLASSES){
            
            x.save();
            
        }
        for(Images y:IMAGES){
            
            y.SaveImage();
        }
        
    }
    
    public static File saveImageToFile(Image image, File Directory, String name) {

        File outputFile = new File(Directory, name);

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return outputFile;
    }

    public static void LoadProject(File ProjectDir) throws FileNotFoundException, IOException {

        if (!isProject(ProjectDir)) {
            return ;
        }
        String Name = ProjectDir.getName();

        for (Project project : LIST_OF_PROJECTS) {
            
            if(project.Name == Name) return ;
        }
        // Loading Image
        Image image = LoadProjectImage(ProjectDir);

        Boolean autoSave = true;
        Boolean saveImages = true;

        // extracting project files Directory
        File SettingFile = new File(ProjectDir, "Settings");

        //finding config file
        File configFile = new File(SettingFile, "config.txt");

        //extract Project config
        Scanner sc = new Scanner(configFile);

        // 1 -> AUTO SAVE
        
        try {
            if (sc.hasNextLine()) {
                
                final String nextLine = sc.nextLine();
                autoSave = Boolean.getBoolean(nextLine);
            }

            // 2 -> SAVE IMAGES
            if (sc.hasNextLine()) {
                
                final String nextLine = sc.nextLine();
                saveImages = Boolean.getBoolean(nextLine);
            }
        } catch (Exception e) {
        }

        //Creating Project
        Project project = new Project(Name, image, ProjectDir, autoSave, saveImages);

        project.LoadProjectImages();
        project.LoadClasses();

    }

    public static boolean isProject(File project) {

        int cpt = 0;
        File SettingFile = new File(project,"Settings");
        File ImagesFile = new File(project,"Images");
        File ClassFile = new File(project,"Classified");
        
        if(!SettingFile.exists()) return false;
        System.out.println("setings");
        if(!ImagesFile.exists()) return false;
        System.out.println("Images");
        if(!ClassFile.exists()) return false;
        System.out.println("Classified");

        File Image = new File(SettingFile,"Image.jpg");
        File Config = new File(SettingFile,"config.txt");
        
        if(!Image.exists()) return false;
        if(!Config.exists()) return false;
        
        return true;
    }

    private static Image LoadProjectImage(File ProjectDir) {

        // extracting project files Directory
        File SettingFile = new File(ProjectDir, "Settings");

        //finding Image and config file
        File imageFile = new File(SettingFile, "Image.jpg");

        // extract Project Image
        return new Image(imageFile.toURI().toString());

    }
    private void LoadProjectImages(){
        
        for(File imgFime: new File(Directory,"Images").listFiles() ){
            
            new Images(this, imgFime.getName().split(".")[1]);
            
        }
        
        
    }

    private void LoadClasses() {

        //loading Classes
        File ClassesFile = new File(Directory, "Classified");

        for (File x : ClassesFile.listFiles()) {

            Class.Load(x.getName(), this);
            
        }

    }

    public Integer getClassNumber() {

        return CLASSES.size();
    }

    public Integer getImagesNumber() {

        return IMAGES.size();
    }

    public Integer getLabledNumber() {

        int cpt = 0;

        for (Images x : IMAGES) {

            if (x.isLabled()) {
                cpt++;
            }
        }
        return cpt;
    }

    public Integer getClassifiedNumber() {

        int cpt = 0;

        for (Images x : IMAGES) {

            if (x.isClassified()) {
                cpt++;
            }

        }
        return cpt;
    }

    public void Open() {
        

    }

}
