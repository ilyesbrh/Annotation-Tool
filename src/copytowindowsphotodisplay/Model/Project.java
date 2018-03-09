 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

/**
 *
 * @author DaMi
 */
public class Project {

    public static final ObservableList<Project> LIST_OF_PROJECTS = FXCollections.observableArrayList();
    
    private Element Root;

    public Attribute Name;

    private Attribute Path;
    
    public Attribute ImagePath;
    
    private Attribute ImagesDir;
    
    private Attribute AutoSave;
    
    private Attribute SaveImages;

    public Project(File inputFile) throws DocumentException {
        
        try {
            SAXReader sa = new SAXReader();
            Document document = sa.read(inputFile);
            
            System.out.println("Root element :" + document.getRootElement().getName());
            
            Root = document.getRootElement();
            
            List<Attribute> attributes = Root.attributes();
            
            Name = attributes.get(0);
            Path = attributes.get(1);
            ImagePath = attributes.get(2);
            ImagesDir = attributes.get(3);
            AutoSave = attributes.get(4);
            SaveImages = attributes.get(5);
            
            LIST_OF_PROJECTS.add(this);
        } catch (DocumentException documentException) {
            System.err.println("Error :') ");
        }
        
        
    }

    public Project(String name , File path , File imagePath , Boolean autoSave , Boolean saveImages , File imagesDir ) throws Exception {
        
         for (Project p : LIST_OF_PROJECTS) {

            if (name.equals(p.Name.getValue())) {
                throw new Exception("exist");
            }
        }
        Document document = DocumentHelper.createDocument();
        
        Root = document.addElement("Project")
                .addAttribute("Name", name)
                .addAttribute("Path", path.toURI().toString())
                .addAttribute("ImagePath", imagePath.toURI().toString())
                .addAttribute("ImagesDir", imagesDir.toURI().toString())
                .addAttribute("AutoSave", String.valueOf(autoSave))
                .addAttribute("SaveImages", String.valueOf(saveImages))
                ;

        List<Attribute> attributes = Root.attributes();
        
        Name = attributes.get(0);
        Path = attributes.get(1);
        ImagePath = attributes.get(2);
        ImagesDir = attributes.get(3);
        AutoSave = attributes.get(4);
        SaveImages = attributes.get(5);
        
        for (Node node : Root.content()) {
            
        }
        
        LIST_OF_PROJECTS.add(this);
        
    }
   
    public void AddImage(File url) throws IOException{
        
        SimpleImageInfo simpleImageInfo = new SimpleImageInfo(url);
        
        Element addElement = Root.addElement("Image")
                
                .addAttribute("Name", url.getName().split(".")[0])
                .addAttribute("Path", url.toURI().toString())
                .addAttribute("Type", simpleImageInfo.getMimeType())
                .addAttribute("Width", String.valueOf(simpleImageInfo.getWidth()))
                .addAttribute("Height", String.valueOf(simpleImageInfo.getHeight()))
                ;
        
    }
    
    public Integer getClassNumber() {

        Set<String> ret = new HashSet<>();
        
        for (Node I : Root.content()) {
            
            for (Node S : ((Element)I).content()) {
                
                for (Node C : ((Element)S).content()) {
                    
                    ret.add(((Element) C).attributeValue("Name"));
                }
            }
        }
        return ret.size();
    }
    public Integer getImagesNumber() {

        return Root.nodeCount();
    }
    public Integer getLabledNumber() {

        int cpt = 0;

        return cpt;
    }
    public Integer getClassifiedNumber() {

        int cpt = 0;

        
        return cpt;
    }
    
    public void Save() throws IOException {   
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
    
    public void ProjectCSV(File url) {
        
        try {
            // Pretty print the document to System.out
            url.createNewFile();
            FileWriter fileWriter = new FileWriter(url);

            for (Node node : Root.content()) {

                
                Element e = (Element) node;
                String name = e.attributeValue("name");
                String type = e.attributeValue("Type");
                String W = e.attributeValue("Width");
                String H = e.attributeValue("Height");

                for (Node childs : e.content()) {

                    Element eChild = (Element) childs;
                    fileWriter.write(name + "," + type + "," + W + "," + H + "," + eChild.getName());

                    for (Attribute attribute : eChild.attributes()) {

                        fileWriter.write("," + attribute.getValue());
                    }
                    fileWriter.write("\n");
                }

            }

            fileWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}