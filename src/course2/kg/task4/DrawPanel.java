/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.task4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.JPanel;

import course2.kg.task4.draw.IDrawer;
import course2.kg.task4.draw.SimpleEdgeDrawer;
import course2.kg.task4.draw.SimpleSideDrawer;
import course2.kg.task4.fun.SolarSystem;
import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Sphere;
import course2.kg.task4.screen.ScreenConverter;
import course2.kg.task4.third.Camera;
import course2.kg.task4.third.Scene;
import course2.kg.task4.models.Parallelepiped;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;

    public DrawPanel() {
        super();
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
//        scene = new Scene(Color.BLACK.getRGB());
        scene.showAxes();

//        scene.getModelsList().add(new Parallelepiped(
//                new Vector3(-0.4f, -0.4f, -0.4f),
//                new Vector3(0.4f, 0.4f, 0.4f)
//        ));
//        scene.getModelsList().add(new Parallelepiped(
//                new Vector3(0.1f, 0.8f, -0.2f),
//                new Vector3(0.5f, -0.1f, 0.1f)
//        ));
        Random rnd  = new Random();
        int[] arr = new int[]{-1, 1};
//        scene.getModelsList().add(new Parallelepiped(
//                new Vector3(rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)]),
//                new Vector3(rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)]), new Color(100, 150, 200, 150)
//        ));
//        scene.getModelsList().add(new Parallelepiped(
//                new Vector3(rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)]),
//                new Vector3(rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)], rnd.nextFloat() * arr[rnd.nextInt(2)]), new Color(200, 150, 100, 150)
//        ));
        scene.getModelsList().add(new Sphere(new Vector3(0, 0, 0), 1f, new Color(134, 43, 76)));
//        scene.getModelsList().addAll(SolarSystem.getSolarSystem());
        scene.getModelsList().add(new Sphere(new Vector3(-1, 1, 1), 1f));
        scene.getModelsList().add(new Sphere(new Vector3(1, -1, 1), 1f));
        scene.getModelsList().add(new Sphere(new Vector3(1, 1, -1), 1f));

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
//        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        IDrawer dr = new SimpleSideDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}
