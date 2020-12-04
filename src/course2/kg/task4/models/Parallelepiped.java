/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.task4.models;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import course2.kg.task4.math.Vector3;
import course2.kg.task4.third.IModel;
import course2.kg.task4.third.Mesh;
import course2.kg.task4.third.PolyLine3D;

/**
 * Описывает параллелипипед по двум диагональным точкам
 * @author Alexey
 */
public class Parallelepiped implements IModel {
    private Vector3 LTF, RBN;
    private Color color;

    /**
     * Создаёт экземпляр параллелипипеда
     * @param LTF Левая Верхняя Дальняя точка (Left Top Far)
     * @param RBN Правая Нижняя Ближняя точка (Right Bottom Near)
     */
    public Parallelepiped(Vector3 LTF, Vector3 RBN, Color color) {
        this.LTF = LTF;
        this.RBN = RBN;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public Mesh getMesh() {
        List<PolyLine3D> lines = new LinkedList<>();
        List<Vector3> vertexes = Arrays.asList(
                new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ())
        );
        /*Дальняя сторона (Z фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(
                vertexes.get(0),
                vertexes.get(1),
                vertexes.get(2),
                vertexes.get(3)
                ), true, color));
        /*Ближняя сторона  (Z фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(
                vertexes.get(4),
                vertexes.get(5),
                vertexes.get(6),
                vertexes.get(7)
        ), true, color));
        
        /*Верхняя сторона (Y фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(
                vertexes.get(0),
                vertexes.get(4),
                vertexes.get(7),
                vertexes.get(3)
        ), true, color));
        /*Нижняя сторона (Y фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(
                vertexes.get(1),
                vertexes.get(5),
                vertexes.get(6),
                vertexes.get(2)), true, color));
        
        /*Левая сторона (X фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(
                vertexes.get(0),
                vertexes.get(4),
                vertexes.get(5),
                vertexes.get(1)), true, color));
        /*Правая сторона (X фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(
                vertexes.get(3),
                vertexes.get(7),
                vertexes.get(6),
                vertexes.get(2)), true, color));
        
        return new Mesh(lines, vertexes);
    }
}
