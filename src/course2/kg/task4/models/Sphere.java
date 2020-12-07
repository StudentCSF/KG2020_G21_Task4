package course2.kg.task4.models;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.third.IModel;
import course2.kg.task4.third.Mesh;
import course2.kg.task4.third.PolyLine3D;

import java.awt.*;
import java.util.*;
import java.util.List;

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
    public Mesh getMesh() {
        List<PolyLine3D> res = new LinkedList<>();
        float step = (float) Math.PI / 12;
        List<Vector3> l = null;
        List<Vector3> l2 = null;
        float eps = step / 1000;
        for (float alpha = -0.5f * (float)Math.PI; alpha <= 0.5f * Math.PI + eps; alpha += step) {
            float sinA = (float) Math.sin(alpha);
            List<Vector3> main = new ArrayList<>();
            List<Vector3> main2 = new ArrayList<>();
            for (float beta = -0.5f * (float)Math.PI; beta <= 0.5f * Math.PI + eps; beta += step) {
                float sinB = (float) Math.sin(beta);
                float x = center.getX() + r * sinA * (float) Math.sqrt(1 - sinB * sinB);
                float y = center.getY() + r * sinA * sinB;
                float cosA = (float) Math.sqrt(1 - sinA * sinA);
                float z = center.getZ() + r * cosA;
                main.add(new Vector3(x, y, z));
                float z2 = center.getZ() - r * cosA;
                main2.add(new Vector3(x, y, z2));
            }
            if (l != null)
                for (int k = 0; k < main.size() - 1; k++) {
                    res.add(new PolyLine3D(Arrays.asList(l.get(k), l.get(k + 1), main.get(k + 1), main.get(k)), true, color));
                    res.add(new PolyLine3D(Arrays.asList(l2.get(k), l2.get(k + 1), main2.get(k + 1), main2.get(k)), true, color));
                }
            l = main;
            l2 = main2;
        }
        return new Mesh(res, null, null);
    }
}
