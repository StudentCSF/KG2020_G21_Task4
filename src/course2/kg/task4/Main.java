/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.task4;

import course2.kg.task4.binary3d.ModelBinaryOperator;
import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Line3D;
import course2.kg.task4.utils.MathUtils;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.*;

/**
 * @author Alexey
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        JFrame frame = new JFrame();
//        frame.setSize(600, 600);
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.add(new DrawPanel());
//        frame.setVisible(true);
        Vector3 v = ModelBinaryOperator.intersection(new Line3D(new Vector3(0, -1, 0), new Vector3(0, 1, 0)), new Line3D(new Vector3(0, 0, 1), new Vector3(0, 0, -1)));
        if (v != null) System.out.println(v.getX() + " " + v.getY() + " " + v.getZ());
    }
}
