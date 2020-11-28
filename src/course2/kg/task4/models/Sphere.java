package course2.kg.task4.models;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.third.IModel;
import course2.kg.task4.third.PolyLine3D;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Sphere implements IModel {
    private Vector3 center;
    private float r;
    private Color color;

    public Sphere(Vector3 center, float radius, Color color) {
        this.center = center;
        this.r = radius;
        this.color = color;
    }

    public Sphere(Vector3 center, float radius) {
        this(center, radius, Color.BLACK);
    }

    public Vector3 getCenter() {
        return center;
    }

    public float getR() {
        return r;
    }


    @Override
    public List<PolyLine3D> getLines() {
        List<PolyLine3D> res = new LinkedList<>();
        float step = (float) Math.PI / 10;
        float d = 2 * r;
        for (float alpha = 0; alpha <= 2 * Math.PI; alpha += step) {
            float sinA = (float) Math.sin(alpha);
            PolyLine3D p1 = new PolyLine3D(new LinkedList<>(), false);
            PolyLine3D p2 = new PolyLine3D(new LinkedList<>(), false);
            for (float beta = 0; beta <= 2 * Math.PI; beta += step) {
                float sinB = (float) Math.sin(beta);
                float x = center.getX() + r * sinA * (float) Math.sqrt(1 - sinB * sinB);
                float y = center.getY() + r * sinA * sinB;
                float z = center.getZ() + r * (float) Math.sqrt(1 - sinA * sinA);
                p1.getPoints().add(new Vector3(x, y, z));
                float z2 = center.getZ() > 0 ? d - z : -d - z;
                p2.getPoints().add(new Vector3(x, y, z2));
            }
            res.add(p1);
            res.add(p2);
        }
        for (int i = 0; i < res.get(0).getPoints().size(); i++) {
            PolyLine3D p1 = new PolyLine3D(new LinkedList<>(), false);
            PolyLine3D p2 = new PolyLine3D(new LinkedList<>(), false);
            int k = 0;
            for (PolyLine3D curr : res) {
                if (k % 2 == 0) p1.getPoints().add(curr.getPoints().get(i));
                else p2.getPoints().add(curr.getPoints().get(i));
                k++;
            }
            res.add(p1);
            res.add(p2);
        }
        return res;
    }
}
