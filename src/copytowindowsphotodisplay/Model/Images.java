
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.io.File;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

/**
 *
 * @author DaMi
 */
public class Images {

    public Element imageElement;

    public Attribute Name;

    public Attribute Path;

    private Attribute Type;

    private Attribute Width;
    
    private Attribute Height;
    
    public BooleanProperty Labled;

    public Images(Element item) {
        
        imageElement = item;
        
        Name = item.attribute(0);
        Path = item.attribute(1);
        Type = item.attribute(2);
        Width = item.attribute(3);
        Height = item.attribute(4);
        
    }
   
    public void Save() {

        
        Element rootElement = imageElement.getDocument().getRootElement();
        
        if(Boolean.valueOf(rootElement.attributeValue("SaveImages"))){
            
            String dir = rootElement.attributeValue("ImagesDir");
            
            File Dir=new File(dir);
            if(!Dir.exists())
                Dir.mkdir();
            
        Project.saveImageToFile(new Image(Path.getValue()),Dir, Name.getValue());
        }
        
    }
    public boolean isClassified(String Class) {
        
        List<Node> content = imageElement.content();
        
        if(content.isEmpty()) 
            return false ;
        
        for (Node node : content) {
            
            Element DrawShape = (Element)node;
            
            for (Node C : DrawShape.content()) {
                
                if(((Element)C).attributeValue("Name").equals(Class)) 
                    return true;
            }
        }
        return false ;
    }

    public boolean isClassified() {

        return ! imageElement.content().isEmpty();
    }

}
