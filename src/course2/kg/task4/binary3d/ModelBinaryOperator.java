package course2.kg.task4.binary3d;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Line3D;
import course2.kg.task4.third.IModel;
import course2.kg.task4.third.PolyLine3D;
import course2.kg.task4.utils.MathUtils;

import java.util.List;


public class ModelBinaryOperator {

    public static List<PolyLine3D> union(IModel m1, IModel m2) {

        return null;
    }

    public static List<PolyLine3D> subtraction(IModel from, IModel that) {
        return null;
    }

    public static List<PolyLine3D> intersection(IModel m1, IModel m2) {
        return null;
    }

    public static boolean isInside(IModel model, Vector3 v) {

        return false;
    }

    public static Vector3 cutsIntersection(Line3D l1, Line3D l2) {
        Vector3 p11 = l1.getP1();
        Vector3 p12 = l1.getP2();
        Vector3 p21 = l2.getP1();
        Vector3 p22 = l2.getP2();
        float[][] matrix = new float[][]{
                {p12.getX() - p11.getX(), p12.getY() - p11.getY(), p12.getZ() - p11.getZ()},
                {p21.getX() - p11.getX(), p21.getY() - p11.getY(), p21.getZ() - p11.getZ()},
                {p22.getX() - p11.getX(), p22.getY() - p11.getY(), p22.getZ() - p11.getZ()}

        };
        if (Math.abs(MathUtils.determinant(matrix)) < 1E-9) {
            float[] uv = null;
            if ((p11.getX() != 0 || p12.getX() != 0 || p21.getX() != 0 || p22.getX() != 0)
                    && (p11.getY() != 0 || p12.getY() != 0 || p21.getY() != 0 || p22.getY() != 0)) {
                uv = getUV(new float[]{p11.getX(), p12.getX(), p21.getX(), p22.getX(), p11.getY(), p12.getY(), p21.getY(), p22.getY()});
            } else if ((p11.getY() != 0 || p12.getY() != 0 || p21.getY() != 0 || p22.getY() != 0)
                    && (p11.getZ() != 0 || p12.getZ() != 0 || p21.getZ() != 0 || p22.getZ() != 0)) {
                uv = getUV(new float[]{p11.getZ(), p12.getZ(), p21.getZ(), p22.getZ(), p11.getY(), p12.getY(), p21.getY(), p22.getY()});
            } else if ((p11.getX() != 0 || p12.getX() != 0 || p21.getX() != 0 || p22.getX() != 0)
                    && (p11.getZ() != 0 || p12.getZ() != 0 || p21.getZ() != 0 || p22.getZ() != 0)) {
                uv = getUV(new float[]{p11.getX(), p12.getX(), p21.getX(), p22.getX(), p11.getZ(), p12.getZ(), p21.getZ(), p22.getZ()});
            }
            if (uv != null && uv[0] < 1 && uv[1] < 1 && uv[0] > 0 && uv[1] > 0)
                return new Vector3(
                        uv[0] * (p11.getX() - p12.getX()) + p12.getX(),
                        uv[0] * (p11.getY() - p12.getY()) + p12.getY(),
                        uv[0] * (p11.getZ() - p12.getZ()) + p12.getZ()
                );
        }
        return null;
    }

    private static float[] getUV(float[] arr) {
        float[] res = new float[2];
        float[][] m = {
                {arr[0] - arr[1], arr[3] - arr[2]},
                {arr[4] - arr[5], arr[7] - arr[6]}
        };
        float det = MathUtils.determinant(m);
        float[][] m1 = {
                {arr[3] - arr[1], arr[3] - arr[2]},
                {arr[7] - arr[5], arr[7] - arr[6]}
        };
        float det1 = MathUtils.determinant(m1);
        res[0] = det1 / det;
        float[][] m2 = {
                {arr[0] - arr[1], arr[3] - arr[1]},
                {arr[4] - arr[5], arr[7] - arr[5]}
        };
        float det2 = MathUtils.determinant(m2);
        res[1] = det2 / det;
        return res;
    }
}
