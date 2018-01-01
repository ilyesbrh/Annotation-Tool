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

    public final ArrayList<Shape> Pixels = new ArrayList<>();

    private Class classe;
    private Images image;
    private File AnnotationFile;

    public Annotation(Class x, Images y) {

        this.classe = x;
        this.image = y;
        AnnotationFile = new File(x.getClassDir(), image.getName() + ".txt");

        if (!x.IMAGES.contains(y)) {
            x.IMAGES.add(y);
        }
        y.ANNOTATIONS.add(this);
        x.ANNOTATIONS.add(this);

    }
    
    public void save() {

        try {

            Project.SavingFile(classe.getClassDir(), image.getName() + ".txt");

            FileWriter w = new FileWriter(AnnotationFile);
            w.append("test");
            w.close();
        } catch (IOException ex) {
        }
    }
    public void Load() {
        
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
        return AnnotationFile;
    }

    public void setAnnotationDir(File AnnotationDir) {
        this.AnnotationFile = AnnotationDir;
    }

    public boolean isLabled() {
        
        return !Pixels.isEmpty();
    }
    public static void LoadAnnotationFromClass(Class x, String name) {

        Images targetImage = null;
        for (Images i : x.getProject().IMAGES) {

            if (i.getName().equals(name)) {

                targetImage = i;
                break;
            }
        }

        if (targetImage == null) {

            File imageFile = new File(x.getProject().ImagesDir, name + ".jpg");
            targetImage = new Images(imageFile, x.getProject());
        }

        Annotation annotation = new Annotation(x, targetImage);
        annotation.Load();

    }

}
