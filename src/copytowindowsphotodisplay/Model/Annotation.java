/*
 * To Changeable this license header, choose License Headers in Project Properties.
 * To Changeable this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.shape.Shape;

/**
 *
 * @author DaMi
 */
public class Annotation {
    
    public final ArrayList<Shape> Pixels=new ArrayList<>();
    
    private Class classe;
    private Images image;
    private File AnnotationDir;
    
    

    public Annotation(Class x,Images y) {
      
        
        this.classe=x;
        this.image=y;
        AnnotationDir = new File(x.getClassDir(),image.getName()+".txt");
        
        if(!x.IMAGES.contains(y)) x.IMAGES.add(y);
        y.ANNOTATIONS.add(this);
        x.ANNOTATIONS.add(this);
        
    }
    
    public Class getClasse() {
        return classe;
    }

    public void setClasse(Class classe) {
        this.classe = classe;
    }

    public Images getImage() {
        return image;
    }

    public void setImage(Images image) {
        this.image = image;
    }

    public File getAnnotationDir() {
        return AnnotationDir;
    }

    public void setAnnotationDir(File AnnotationDir) {
        this.AnnotationDir = AnnotationDir;
    }

    public void save() {
        
        try {
            FileWriter w=new FileWriter(AnnotationDir);
            
           
            
        } catch (IOException ex) {
        }
    }
    public void Load(){
        
    }

    public boolean isLabled() {
        
        return false;
    }

}
