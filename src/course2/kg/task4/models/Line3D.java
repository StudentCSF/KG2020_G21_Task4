/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.task4.models;

import java.awt.*;
import java.util.Arrays;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.third.IModel;
import course2.kg.task4.third.Mesh;
import course2.kg.task4.third.PolyLine3D;

/**
 * Описывает трёхмерный отрезок
 * @author Alexey
 */
public class Line3D implements IModel {
    private Vector3 p1, p2;
    private Color color;

    public Line3D(Vector3 p1, Vector3 p2, Color color) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
    }

    public Line3D(Vector3 p1, Vector3 p2) {
        this(p1, p2, Color.BLACK);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Mesh getMesh() {
        return new Mesh(Arrays.asList(new PolyLine3D(
                Arrays.asList(p1, p2)
            , false, color)), Arrays.asList(p1, p2), Arrays.asList(this));
    }

    public Vector3 getP1() {
        return p1;
    }

    public Vector3 getP2() {
        return p2;
    }
}
