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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
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
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 *
 * @author DaMi
 */
public class Project {

    public static final ObservableList<Project> LIST_OF_PROJECTS = FXCollections.observableArrayList();

    public Element Root;

    public Attribute Name;

    private Attribute Path;

    public Attribute ImagePath;

    private Attribute ImagesDir;

    private Attribute AutoSave;

    private Attribute SaveImages;
    
    public HashSet<String> ClassArrays;
        
    public Project(File inputFile) throws DocumentException, Exception {
        
        this.ClassArrays = new HashSet<>();

        SAXReader sa = new SAXReader();
        Document document = sa.read(inputFile);

        System.out.println("Root element :" + document.getRootElement().getName());

        Root = document.getRootElement();
        
        List<Attribute> attributes = Root.attributes();

        Name = attributes.get(0);
        for (Project List : LIST_OF_PROJECTS) {

            if (Name.getValue().equals(List.Name.getValue())) {
                throw new Exception("exist");
            }

        }
        Path = attributes.get(1);
        ImagePath = attributes.get(2);
        ImagesDir = attributes.get(3);
        AutoSave = attributes.get(4);
        SaveImages = attributes.get(5);

        LIST_OF_PROJECTS.add(this);
        Root.setData(this);
        getClasses(Root);

    }

    public Project(String name, File path, File imagePath, Boolean autoSave, Boolean saveImages, File imagesDir) throws Exception {
        
        this.ClassArrays = new HashSet<>();
        
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
                .addAttribute("SaveImages", String.valueOf(saveImages));

        List<Attribute> attributes = Root.attributes();

        Name = attributes.get(0);
        Path = attributes.get(1);
        ImagePath = attributes.get(2);
        ImagesDir = attributes.get(3);
        AutoSave = attributes.get(4);
        SaveImages = attributes.get(5);

        LIST_OF_PROJECTS.add(this);
        Root.setData(this);
        
        System.out.println(document.asXML());
    }

    public Element AddImage(File url) throws IOException {

        SimpleImageInfo simpleImageInfo = new SimpleImageInfo(url);

        Element addElement = Root.addElement("Image")
                .addAttribute("Name", url.getName().split("\\.")[0])
                .addAttribute("Path", url.toURI().toString())
                .addAttribute("Type", simpleImageInfo.getMimeType())
                .addAttribute("Width", String.valueOf(simpleImageInfo.getWidth()))
                .addAttribute("Height", String.valueOf(simpleImageInfo.getHeight()));

        return addElement;

    }

    public Integer getClassNumber() {

        Set<String> ret = new HashSet<>();

        for (Element I : Root.elements()) {

            for (Element S : I.elements()) {

                for (Element C : S.elements()) {

                    ret.add(C.attributeValue("Name"));
                }
            }
        }
        return ret.size();
    }

    public Integer getImagesNumber() {

        return Root.elements().size();
    }

    public Integer getLabledNumber() {

        HashSet<Node> hashSet = new HashSet<>(Root.selectNodes("//Image/Shape/Class"));
        HashSet<String> hashSetS = new HashSet<>();
        
        for (Iterator<Node> it = hashSet.iterator(); it.hasNext();) {
            
            Element node = (Element) it.next();
            hashSetS.add(node.getParent().getParent().attribute(0).getValue());
        }
        
        return hashSetS.size();
    }

    public Integer getClassifiedNumber() {

        int cpt = 0;

        return cpt;
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

    public static HashSet<String> getClasses(Element root) {

        List<Node> selectNodes = root.selectNodes("//Class");
        
        HashSet<String> hashSet = new HashSet<String>();
        
        for (Iterator<Node> it = selectNodes.iterator(); it.hasNext();) {
            Element img = (Element) it.next();
            hashSet.add(img.attribute(0).getValue().toLowerCase().replaceAll("\\s+",""));
        }
        System.out.println("selected nodes hashset ="+hashSet);
        return hashSet;

    }
    
    public void save() {

        new Thread(() -> {

            try {
                File file = new File(new URI(Path.getValue()));
                file.createNewFile();
                FileWriter os = new FileWriter(file);
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer = new XMLWriter(os, format);
                writer.write(Root.getDocument());
                writer.close();
                for (Element element : Root.elements()) {
                    new Images(element).Save();
                    
                }
            } catch (URISyntaxException uRISyntaxException) {
            } catch (IOException iOException) {
            }
        }).run();

    }

    public void SaveAs(File file, String type) {
        
        if(type.equals("XML"))
            this.saveXML(file);
        else if(type.equals("CSV"))
            this.saveCSV(file);
        
    }
    public void saveXML(File file) {
        
        new Thread(() -> {

            try {
                file.createNewFile();
                FileWriter os = new FileWriter(file);
                OutputFormat format = OutputFormat.createPrettyPrint();
                XMLWriter writer = new XMLWriter(os, format);
                writer.write(Root.getDocument());
                writer.close();
                
            } catch (IOException iOException) {
            }
        }).run();
        
    }
    public void saveCSV(File file) {
        
        new Thread(() -> {

            try {
                file.createNewFile();
                FileWriter os = new FileWriter(file);
                String STR="";
                for (Element I : Root.elements()) {
                    for (Element S : I.elements()) {
                        for (Element C : S.elements()) {
                            for(Attribute T : I.attributes()){
                                
                                STR= STR+T.getValue()+",";
                            }
                            for(Attribute T : S.attributes()){
                                
                                STR= STR+T.getValue()+",";       
                            }
                            STR= STR+C.attribute(0).getValue()+"\n";    
                        }
                    }                    
                }
                os.write(STR);
                os.close();
            } catch (IOException iOException) {
            }
        }).run();
        
    }

}
