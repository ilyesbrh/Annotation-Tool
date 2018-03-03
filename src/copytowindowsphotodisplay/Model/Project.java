/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public final ObservableList<CLASS> CLASSES = FXCollections.observableArrayList();

    public final ObservableList<Images> IMAGES = FXCollections.observableArrayList();

    private Image image;

    private String Name;

    private File Directory;
    private File ClassifiedDir;
    private File ImagesDir;

    //next version nchlh
    private Boolean AutoSave;
    private Boolean SaveImages;

    public Project(String Name, Image image, File directory, Boolean AutoSave, Boolean SaveImages) throws IOException {
        
        
        Directory = new File(directory, Name);

        ImagesDir = new File(directory, "Images");

        ClassifiedDir = new File(directory, "Classified");

        this.Name = Name;

        this.image = image;

        for (Project p : LIST_OF_PROJECTS) {

            if (this.Name.equals(p.Name)) {
                return;
            }
        }

        LIST_OF_PROJECTS.add(this);

        /*this.AutoSave = AutoSave;
        this.SaveImages = SaveImages;*/
    }
    public Project(Image image, File directory, Boolean AutoSave, Boolean SaveImages) throws IOException {
        
        Directory = directory;

        ImagesDir = new File(directory, "Images");

        ClassifiedDir = new File(directory, "Classified");

        this.Name = directory.getName();

        this.image = image;

        for (Project p : LIST_OF_PROJECTS) {

            if (this.Name.equals(p.Name)) {
                return;
            }
        }

        LIST_OF_PROJECTS.add(this);

        /*this.AutoSave = AutoSave;
        this.SaveImages = SaveImages;*/
        
    }

    public void Save() throws IOException {

        if (!Directory.exists()) {
            Directory.mkdir();
        }
        // saving Image of project
        saveImageToFile(image, Directory, "Image");

        Project.SavingFile(Directory, "config.xml");

        Project.SavingDir(Directory, "Images");

        Project.SavingDir(Directory, "Classified");

        for (CLASS x : CLASSES) {

            x.Save();

        }
        for (Images y : IMAGES) {

            y.Save();
        }

        // next version
        /*
        FileWriter fw = new FileWriter(configFile, false);
        fw.write(this.AutoSave.toString() + '\n');
        fw.write(this.SaveImages.toString() + '\n');
        fw.close();
         */
    }

    /**
     *
     * @param image
     * @param Directory
     * @param name
     * @return new Image File
     */
    public static File saveImageToFile(final Image image, File Directory, String name) {

        File outputFile = new File(Directory, name + ".jpg");

        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "jpg", outputFile);
        } catch (IOException e) {
            outputFile = null;
            throw new RuntimeException(e);
        }
        return outputFile;
    }

    public static void LoadProject(File ProjectDir) throws FileNotFoundException, IOException {

        if (!isProject(ProjectDir)) {
            return;
        }
        String Name = ProjectDir.getName();
        for (Project project : LIST_OF_PROJECTS) {

            if (project.Name.equals(Name)) {
                return;
            }
        }
        // Loading Image
        Image image = LoadProjectImage(ProjectDir);

        //Creating Project
        Project project = new Project( image, ProjectDir, false, false);
        project.LoadProjectImages();
        project.LoadClasses();
        /*
        //finding config file
        File configFile = new File(ProjectDir, "config.txt");
        
        // 1 -> AUTO SAVE Next version
        
        //extract Project config
        Scanner sc = new Scanner(configFile);
        Boolean autoSave = true;
        Boolean saveImages = true;
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
        */
    }

    public static boolean isProject(File project) {

        File ImagesFile = new File(project, "Images");
        File ClassFile = new File(project, "Classified");
        File Image = new File(project, "Image.jpg");
        File Config = new File(project, "config.xml");
        
        if (!ImagesFile.exists()) {
            return false;
        }
        
        if (!ClassFile.exists()) {
            return false;
        }

        if (!Image.exists()) {
            return false;
        }

        if (!Config.exists()) {
            return false;
        }

        return true;
    }

    private static Image LoadProjectImage(File ProjectDir) {

        //finding Image and config file
        File imageFile = new File(ProjectDir, "Image.jpg");

        // extract Project Image
        return new Image(imageFile.toURI().toString());

    }

    private void LoadProjectImages() {
        
        for (File imgFile : ImagesDir.listFiles()) {

            Images.Load(this, imgFile);

        }

    }

    private void LoadClasses() {

        //loading Classes
        for (File x : ClassifiedDir.listFiles()) {

            CLASS.Load(x.getName(), this);

        }

    }
    
    public File getClassifiedDir() {
        return ClassifiedDir;
    }

    public void setClassifiedDir(File ClassifiedDir) {
        this.ClassifiedDir = ClassifiedDir;
    }

    public File getImagesDir() {
        return ImagesDir;
    }

    public void setImagesDir(File ImagesDir) {
        this.ImagesDir = ImagesDir;
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

    public static File SavingFile(File Dir, String Name) {

        if (!Dir.exists()) {
            return null;
        }
        if (!Dir.isDirectory()) {
            return null;
        }

        File file = new File(Dir, Name);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return file;

    }

    public static File SavingDir(File Dir, String Name) {

        if (!Dir.exists()) {
            return null;
        }
        if (!Dir.isDirectory()) {
            return null;
        }

        File file = new File(Dir, Name);

        if (!file.exists()) {
            file.mkdir();
        }

        return file;

    }
}