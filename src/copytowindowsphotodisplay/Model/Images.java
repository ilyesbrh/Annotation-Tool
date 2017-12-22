/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.io.File;
import java.util.ArrayList;
import javafx.scene.image.Image;

/**
 *
 * @author DaMi
 */
public class Images {

    public final ArrayList<Annotation> ANNOTATIONS = new ArrayList<>();

    String Name;

    File Dir;

    Project project;

    public Images(File Dir, Project project) {

        this.Name = Dir.getName().split(".")[0];
        this.Dir = Dir;
        this.project = project;

        if (project.getSaveImages()) {

            File ImagesDir = new File(project.getDirectory(), "Images");

            if (ImagesDir.toURI().toString() != Dir.getParentFile().toURI().toString()) {
                this.Dir = Project.saveImageToFile(new Image(Dir.toURI().toString()), ImagesDir, Name);
            }

        }
        project.IMAGES.add(this);

    }
    public Images(Project project , String name) {

        for (Images i : project.IMAGES) {
            
            if(i.Name == name) return;
            
        }
        
        this.Name = name;
        
        File ImagesDir = new File(project.getDirectory(), "Images");
        this.Dir = new File(ImagesDir, name) ;
        
        this.project = project;
        
        project.IMAGES.add(this);

    }

    boolean isLabled() {

        for (Annotation x : ANNOTATIONS) {
            if (x.isLabled()) {
                return true;
            }
        }
        return false;
    }

    boolean isClassified(Class x) {

        for (Annotation Y : ANNOTATIONS) {
            if (Y.getClasse().equals(x)) {
                return true;
            }
        }

        return false;
    }

    boolean isClassified() {

        return !ANNOTATIONS.isEmpty();
    }

    //Supposed to exists in Images of Project
    public static void LoadImageFromClass(Class x, String name) {


        Images targetImage = null;
        for (Images i : x.getProject().IMAGES) {

            if (i.Name.equals(name)) {

                targetImage = i;
                break;
            }
        }


        if (targetImage == null) {
            
            File imagesDir = new File(x.getProject().getDirectory(), "Images");
            File imageDir = new File(imagesDir, name + ".jpg");
            targetImage = new Images(imageDir, x.getProject());
        }

        File Anotation = new File(x.getClassDir(), name + ".txt");
        Annotation note = new Annotation(x, targetImage);

    }
    public void SaveImage() {

        if (project.getSaveImages()) {

            File images = new File(project.getDirectory(), "Images");

            for (Images x : project.IMAGES) {

                Project.saveImageToFile(new Image(Dir.toURI().toString()), images, x.Name);
            }

        }
    }
}
