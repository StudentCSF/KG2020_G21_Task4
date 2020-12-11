/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.task4.math;

/**
 * Класс, хранящий трёхмерный вектор / точку в трёхмерном пространстве.
 * @author Alexey
 */
public class Vector3 {
    private float[] values; /*Значения хранятся в виде массива из трёх элементов*/
    
    /**
     * Создаёт экземпляр вектора на основе значений трёх составляющих
     * @param x первая составляющая, описывающая X-координату
     * @param y вторая составляющая, описывающая Y-координату
     * @param z третья составляющая, описывающая Z-координату
     */
    public Vector3(float x, float y, float z) {
        values = new float[]{x, y, z};
    }

    public Vector3(Vector3 v) {
        this(v.getX(), v.getY(), v.getZ());
    }

    private Vector3(float[] values) {
        this.values = values;
    }

    /**
     * X-составляющая вектора
     * @return X-составляющая вектора
     */
    public float getX() {
        return values[0];
    }

    /**
     * Y-составляющая вектора
     * @return Y-составляющая вектора
     */
    public float getY() {
        return values[1];
    }

    /**
     * Z-составляющая вектора
     * @return Z-составляющая вектора
     */
    public float getZ() {
        return values[2];
    }
    
    /**
     * Метод, возвращающий составляющую вектора по порядковому номеру
     * @param idx порядковый номер
     * @return значение составляющей вектора
     */
    public float at(int idx) {
        return values[idx];
    }
    
    private static final float EPSILON = 1e-10f;
    /**
     * Метод, возвращающий длину вектора
     * @return длина вектора
     */
    public float length() {
        float lenSqr = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
        if (lenSqr < EPSILON)
            return 0;
        return (float)Math.sqrt(lenSqr);
    }

    public Vector3 add(Vector3 other) {
        if (other == null) return this;
        float[] res = new float[3];
        for (int i = 0; i < res.length; i++) {
            res[i] = at(i) + other.at(i);
        }
        return new Vector3(res);
    }

    public Vector3 mult(float k) {
        if (Math.abs(k) < EPSILON) return new Vector3(0, 0, 0);
        float[] res = new float[3];
        for (int i = 0; i < res.length; i++) {
            res[i] = at(i) * k;
        }
        return new Vector3(res);
    }

//    @Override
//    public int hashCode() {
//        return (this.getX() + "," + this.getY() + ","+ this.getZ()).hashCode();
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (!(obj instanceof Vector3)) return false;
//        Vector3 v = (Vector3) obj;
//        float eps = 1E-6f;
//        return Math.abs(v.getX() - this.getX()) < eps && Math.abs(v.getY() - this.getY()) < eps && Math.abs(v.getZ() - this.getZ()) < eps;
//    }
}
