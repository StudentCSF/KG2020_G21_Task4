/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.task4;

import course2.kg.task4.binary3d.ModelBinaryOperator;
import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Line3D;
import course2.kg.task4.models.Parallelepiped;
import course2.kg.task4.models.Sphere;
import course2.kg.task4.third.PolyLine3D;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Alexey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(new DrawPanel());
        frame.setVisible(true);
//        System.out.println(ModelBinaryOperator.isInside(new PolyLine3D(Arrays.asList(new Vector3(-1, -1, 0), new Vector3(-1, 1, 0), new Vector3(1, -1, 0)), true),  new Vector3(0.4f, 1, 0)));
//        if (v != null) System.out.println(v.getX() + " " + v.getY() + " " + v.getZ());
//        System.out.println(ModelBinaryOperator.isInside(new Sphere(new Vector3(0, 0, 0), 1f, new Color(134, 43, 76, 100)), new Vector3(-2, 0, 0)));
//        System.out.println(ModelBinaryOperator.isInside(new Parallelepiped(
//                new Vector3(-1, 1, -1),
//                new Vector3(1, -1, 1)), new Vector3(-2, 0, 0)));
//        System.out.println(ModelBinaryOperator.intersection(new PolyLine3D(Arr
//        ays.asList(new Vector3(1, 1, 1), new Vector3(1, 1, -1), new Vector3(1, -1, -1), new Vector3(1, -1, 1)), true), new Line3D(new Vector3(1000f, 0f,0f), new Vector3(0, 0, 0))));
//        System.out.println(ModelBinaryOperator.isInside(new PolyLine3D(Arrays.asList(new Vector3(1, 1, 0), new Vector3(1, -1, 0), new Vector3(-1, -1, 0), new Vector3(-1, 1, 0)), true), new Vector3(1, 0, 0)));
//        System.out.println(ModelBinaryOperator.intersection(new Line3D(new Vector3(1, 0, 0), new Vector3(1, 0, -2f)), new Line3D(new Vector3(1, 1, -1), new Vector3(1, -1, -1))));
//        System.out.println(ModelBinaryOperator.intersection(new Line3D(new Vector3(1, 0, 0), new Vector3(1, 1, -2E+1f)), new Line3D(new Vector3(1, 1, -1), new Vector3(1, -1, -1))).getY());
//        System.out.println(ModelBinaryOperator.intersection(new Line3D(new Vector3(1, 0, 0), new Vector3(1, 1, -2E+1f)), new Line3D(new Vector3(1, 1, -1), new Vector3(1, -1, -1))).getZ());
//        System.out.println(ModelBinaryOperator.isInside(new PolyLine3D(Arrays.asList(new Vector3(1, 0, 0), new Vector3(0, 1, 0), new Vector3(0, 0, 1), new Vector3(0, 0, 1)), true), ModelBinaryOperator.intersection(new PolyLine3D(Arrays.asList(new Vector3(1, 0, 0), new Vector3(0, 1, 0), new Vector3(0, 0, 1), new Vector3(0, 0, 1)), true), new Line3D(new Vector3(0, 0, 0), new Vector3(1E+3f, 1E+3f, 1E+3f)))));
//        System.out.println(ModelBinaryOperator.intersection(new Line3D()));
    }
}
