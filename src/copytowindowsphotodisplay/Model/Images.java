
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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

    public BooleanProperty Labled = new SimpleBooleanProperty(false);

    public Images(Element item) {

        imageElement = item;

        Name = item.attribute(0);
        Path = item.attribute(1);
        Type = item.attribute(2);
        Width = item.attribute(3);
        Height = item.attribute(4);

    }

    public void Save() throws URISyntaxException {

        Element rootElement = imageElement.getDocument().getRootElement();

        if (Boolean.valueOf(rootElement.attributeValue("SaveImages"))) {

            String dir = rootElement.attributeValue("ImagesDir");

            File Dir = new File(new URI(dir));
            if (!Dir.exists()) {
                Dir.mkdir();
            }
            new Thread(() -> {
                Project.saveImageToFile(new Image(Path.getValue()), Dir, Name.getValue());
            }).run();
        }

    }

    public boolean isClassified(String Class) {

        List<Node> content = imageElement.content();

        if (content.isEmpty()) {
            
            return false;
        }

        for (Node node : content) {

            Element DrawShape = (Element) node;

            for (Node C : DrawShape.content()) {

                if (((Element) C).attributeValue("Name").equals(Class)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isClassified() {

        Labled.setValue(!imageElement.content().isEmpty());
        return Labled.getValue();
    }

    public Image getImage( int i, int i0, boolean b, boolean b0, boolean b1) {

            File f = null;
        try {
            f = new File(new URI(Path.getValue()));
        } catch (URISyntaxException ex) {
            Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!f.exists())
        {
                String dir = imageElement.getParent().attributeValue("ImagesDir");
                try {
                    f = new File(new File(new URI(dir)),Name.getValue());
                } catch (URISyntaxException ex) {
                    Logger.getLogger(Images.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        if(!f.exists())
        {
            return null;
        }
        
        return new Image(f.toURI().toString(),i, i0, b, b0, b1);

    }

}
