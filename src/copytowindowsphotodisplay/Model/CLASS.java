/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.io.File;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author DaMi
 */
public class CLASS {

    public final ArrayList<Images> IMAGES = new ArrayList<>();
    public final ArrayList<Annotation> ANNOTATIONS = new ArrayList<>();

    public StringProperty Name;
    private Project project;
    private File ClassDir;

    public CLASS(String name, Project parent) {

        Name =new SimpleStringProperty(name);

        project = parent;

        setClassDir(new File(parent.getClassifiedDir(), name));
       
        parent.CLASSES.add(this);

    }

    public void Save() {

        Project.SavingDir(project.getClassifiedDir(), Name.get());

        for (Annotation x : ANNOTATIONS) {

            x.save();
        }
    }

    public static CLASS Load(String name, Project project) {

        CLASS x = new CLASS(name, project);
        x.loadClassAnnotations();
        
        return x;
    }


    public File getClassDir() {
        return ClassDir;
    }

    public void setClassDir(File ClassDir) {
        this.ClassDir = ClassDir;
    }

    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
        this.Name.set(Name);
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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
    public void loadClassAnnotations() {
        
        for (File Img : ClassDir.listFiles()) {

            int pos = Img.getName().lastIndexOf(".");
            if (pos == -1) {
                return;
            }
            String ext = Img.getName().substring(pos + 1);
            String name = Img.getName().substring(0, pos);
            
            Annotation.LoadAnnotationFromClass(this, name);

        }
    }

    @Override
    public String toString() {
        return Name.get() ;
    }
    
}
