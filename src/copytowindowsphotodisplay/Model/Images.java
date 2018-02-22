
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

    private String Name;

    private File Dir;

    private Project project;

    public Images(File Dir, Project project) {
        System.out.println(Dir.toURI().toString());
        int pos = Dir.getName().lastIndexOf(".");
        System.out.println(pos);
        if (pos == -1) {
            return;
        }
        String name = Dir.getName().substring(0, pos);
        System.out.println(name);    
        for (Images i : project.IMAGES) {

            if (i.Name.equals(name)) {
                return;
            }

        }

        this.Name = name;
        this.Dir = Dir;
        this.project = project;

        project.IMAGES.add(this);
    }

    public Images(Project project, String name) {

        for (Images i : project.IMAGES) {

            if (i.Name.equals(name)) {
                return;
            }

        }

        this.Name = name;
        this.project = project;
        this.Dir = new File(project.getImagesDir(), name+".jpg");

        project.IMAGES.add(this);
    }

    public void Save() {

        Project.saveImageToFile(new Image(Dir.toURI().toString()), project.getImagesDir(), Name);
    }
    static void Load(Project aThis, File imgFile) {
        
        int pos = imgFile.getName().lastIndexOf(".");
            if (pos == -1) {
                return;
            }
        new Images(aThis, imgFile.getName().substring(0 , pos ));
    }
    public boolean isLabled() {

        for (Annotation x : ANNOTATIONS) {
            if (x.isLabled()) {
                return true;
            }
        }
        return false;
    }

    public boolean isClassified(Class x) {

        for (Annotation Y : ANNOTATIONS) {
            if (Y.getClasse().equals(x)) {
                return true;
            }
        }

        return false;
    }

    public boolean isClassified() {

        return !ANNOTATIONS.isEmpty();
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public File getDir() {
        return Dir;
    }

    public void setDir(File Dir) {
        this.Dir = Dir;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return this.Name; //To change body of generated methods, choose Tools | Templates.
    }

    
}
