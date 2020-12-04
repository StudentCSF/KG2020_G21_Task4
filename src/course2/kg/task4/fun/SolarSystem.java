package course2.kg.task4.fun;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Sphere;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public final class SolarSystem {

    public static List<Sphere> getSolarSystem() {
        Sphere sun = new Sphere(new Vector3(0, 0, 0), 49.68f, Color.YELLOW);
        Sphere mercury = new Sphere(new Vector3(4.143f + sun.getR(), 0, 0), 0.1742f, new Color(255, 255, 224));
        Sphere venus = new Sphere(new Vector3(7.714f + sun.getR(), 0, 0), 0.4323f, new Color(222, 184, 83));
        Sphere earth = new Sphere(new Vector3(10.714f + sun.getR(), 0, 0), 0.455f, new Color(65, 105, 225));
        Sphere mars = new Sphere(new Vector3(16.28f + sun.getR(), 0, 0), 0.25f, new Color(255, 69, 0));
        Sphere jupiter = new Sphere(new Vector3(55.57f + sun.getR(), 0, 0), 5.093f, new Color(255, 228, 181));
        Sphere saturn = new Sphere(new Vector3(102.143f + sun.getR(), 0, 0), 4.293f, new Color(240, 230, 140));
        Sphere uranus = new Sphere(new Vector3(200f + sun.getR(), 0, 0), 1.893f, new Color(175, 238, 238));
        Sphere neptune = new Sphere(new Vector3(325f + sun.getR(), 0, 0), 1.768f, new Color(30, 144, 255));
        Sphere pluto = new Sphere(new Vector3(421.43f + sun.getR(), 0, 0), 0.143f, new Color(160, 82, 45));
        return Arrays.asList(sun, mercury, venus, earth, mars, jupiter, saturn, uranus, neptune, pluto);
    }
}
