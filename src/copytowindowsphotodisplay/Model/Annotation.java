/*
 * To Changeable this license header, choose License Headers in Project Properties.
 * To Changeable this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copytowindowsphotodisplay.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import strongannotationtool.Shapes.VisualShapes.DrawCircle;
import strongannotationtool.Shapes.VisualShapes.DrawRectangle;
import strongannotationtool.Shapes.VisualShapes.DrawableShape;

/**
 *
 * @author DaMi
 */
public class Annotation {

    public final ArrayList<DrawableShape> Shapes = new ArrayList<>();

    private CLASS classe;
    private Images image;
    private File AnnotationFile;

    public Annotation(CLASS x, Images y) {

        this.classe = x;
        this.image = y;
        AnnotationFile = new File(x.getClassDir(), image.getName() + ".txt");

        if (!x.IMAGES.contains(y)) {
            x.IMAGES.add(y);
        }
        y.ANNOTATIONS.add(this);
        x.ANNOTATIONS.add(this);

    }

    public void remove() {

        classe.ANNOTATIONS.remove(this);
        image.ANNOTATIONS.remove(this);
        AnnotationFile.delete();

    }

    public void save() {

        try {

            Project.SavingFile(classe.getClassDir(), image.getName() + ".txt");
            SimpleImageInfo sm = new SimpleImageInfo(image.getDir());
            FileWriter w = new FileWriter(AnnotationFile);
            w.write(sm.toString());
            for (DrawableShape S : Shapes) {
                w.write(S.toString());
            }
            w.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public void Load() {

        Scanner scanner = null;
        try {
            scanner = new Scanner(AnnotationFile);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        }
        while (scanner.hasNextLine()) {

            String text = scanner.nextLine();
            String[] split = text.split(",");
            if ("Rectangle".equals(split[0])) {
                Double ax = new Double(split[1]);
                Double ay = new Double(split[2]);
                Double Width = new Double(split[3]);
                Double Height = new Double(split[4]);
                Double Xk = new Double(split[5]);
                Double Yk = new Double(split[6]);

                System.out.println(ax);
                System.out.println(ay);
                System.out.println(Width);
                System.out.println(Height);

                double cx = ax + Width;
                double cy = ay + Height;

                DrawRectangle Drect = null;
                try {
                    Drect = new DrawRectangle(ax, ay, cx, cy, Xk, Yk, image);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
                Shapes.add(Drect);

                for (DrawableShape Shape : Shapes) {
                    System.err.println(Shape);
                }

            }
            if ("Circle".equals(split[0])) {
                
                DrawCircle drawCircle = null;
                Double ax = new Double(split[1]);
                Double ay = new Double(split[2]);
                Double Width = new Double(split[3]);
                Double Height = new Double(split[4]);
                Double Xk = new Double(split[5]);
                Double Yk = new Double(split[6]);
                try {
                    drawCircle = new DrawCircle(ax, ay, ax+Width, ay+Height, Xk, Yk, image);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
                Shapes.add(drawCircle);
            }
        }
    }

    public CLASS getClasse() {
        return classe;
    }

    public void setClasse(CLASS classe) {
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

        return !Shapes.isEmpty();
    }

    public static void LoadAnnotationFromClass(CLASS x, String name) throws IOException {

        Images targetImage = null;
        for (Images i : x.getProject().IMAGES) {

            if (i.getName().equals(name)) {

                targetImage = i;
                break;
            }
        }

        if (targetImage == null) {

            File imageFile = new File(x.getProject().getImagesDir(), name + ".jpg");
            targetImage = new Images(imageFile, x.getProject());
        }

        Annotation annotation = new Annotation(x, targetImage);
        annotation.Load();

    }

    public static Annotation findAnnotation(CLASS x, Images y) {

        for (Annotation i : x.ANNOTATIONS) {

            if (i.image.equals(y)) {

                return i;
            }
        }
        return null;
    }

}
