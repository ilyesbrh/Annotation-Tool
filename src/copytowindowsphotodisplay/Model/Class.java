/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author DaMi
 */
public class Class {

    public final ArrayList<Images> IMAGES=new ArrayList<>();
    public final ArrayList<Annotation> ANNOTATIONS =new ArrayList<>();
    
    private String Name;
    private Project project;
    private File ClassDir;

    public File getClassDir() {
        return ClassDir;
    }

    public void setClassDir(File ClassDir) {
        this.ClassDir = ClassDir;
    }
    
    public Class(String name,Project parent) {
        
        Name=name;
        
        project=parent;
        
        ClassDir=new File(project.getDirectory(),"Classified");
        
        parent.CLASSES.add(this);
        
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Integer getLabledNumber() {
        
        int cpt=0;
        for(Images x:IMAGES){
            if(x.isLabled()) cpt++;
        }
        return cpt;
    }
    
    public static Class Load(String name,Project project) {

        Class x=new Class(name, project);
        
        x.LoadClassImages();
        
        return x;
    }
    public void LoadClassImages() {
        
        for (File Img : ClassDir.listFiles()) {

            String[] name = Img.getName().split(".");

            if (name[1].equals("txt")) {
                
                Images.LoadImageFromClass(this, name[0]);   
            }
        }
    }
    

    public void save() {
        
        File ClassesDir= new File(project.getDirectory(),"Classified");
        
        File dir= new File(ClassesDir,Name);
        if(!dir.exists()) dir.mkdir();
        
        for(Annotation x: ANNOTATIONS){
            
            x.save();
        }
    }
}
