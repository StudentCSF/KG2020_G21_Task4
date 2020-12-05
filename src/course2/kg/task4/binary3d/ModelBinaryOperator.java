package course2.kg.task4.binary3d;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Line3D;
import course2.kg.task4.third.IModel;
import course2.kg.task4.third.Mesh;
import course2.kg.task4.third.PolyLine3D;
import course2.kg.task4.utils.MathUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ModelBinaryOperator {
    private static final float EPS = 1E-9f;

    private static class CustomModel implements IModel {
        private List<PolyLine3D> pols;

        public CustomModel(List<PolyLine3D> pols) {
            this.pols = pols;
        }

        public List<PolyLine3D> getPols() {
            return pols;
        }

        @Override
        public Mesh getMesh() {
            return new Mesh(getPols(), null, null);
        }
    }

    public static IModel union(IModel m1, IModel m2) {
        return null;
    }

    public static IModel subtraction(IModel from, IModel that) {
        return null;
    }

    public static IModel intersection(IModel m1, IModel m2) {
        return null;
    }

    private static PartitionModel getPolyLines(IModel m1, IModel m2) {
        List<PolyLine3D> o1 = new LinkedList<>();
        List<PolyLine3D> i1 = new LinkedList<>();
        List<PolyLine3D> o2 = new LinkedList<>();
        List<PolyLine3D> i2 = new LinkedList<>();
        for (PolyLine3D p : m1.getMesh().getPolygons()) {
            for (PolyLine3D p2 : m2.getMesh().getPolygons()) {

            }
        }
        return null;
    }

    public static Line3D intersection(PolyLine3D p1, PolyLine3D p2) {
        List<Vector3> lp1 = p1.getPoints();
        float[][] bas = new float[][]{
                {lp1.get(0).getX(), lp1.get(0).getY(), lp1.get(0).getZ()},
                {lp1.get(1).getX() - lp1.get(0).getX(), lp1.get(1).getY() - lp1.get(0).getY(), lp1.get(1).getZ() - lp1.get(0).getZ()},
                {lp1.get(2).getX() - lp1.get(0).getX(), lp1.get(2).getY() - lp1.get(0).getY(), lp1.get(2).getZ() - lp1.get(0).getZ()}
        };
        for (Vector3 curr : p2.getPoints()) {
            System.out.println(2);
        }
        return null;
    }

    public static Vector3 intersection(PolyLine3D p, Line3D l) {
        float [] pts = new float[]{
                l.getP1().getX(),
                l.getP1().getY(),
                l.getP1().getZ(),
                l.getP2().getX(),
                l.getP2().getY(),
                l.getP2().getZ(),
                p.getPoints().get(0).getX(),//x3-6
                p.getPoints().get(0).getY(),//y3-7
                p.getPoints().get(0).getZ(),//z3-8
                p.getPoints().get(1).getX(),//x4-9
                p.getPoints().get(1).getY(),//y4-10
                p.getPoints().get(1).getZ(),//z4-11
                p.getPoints().get(2).getX(),//x5-12
                p.getPoints().get(2).getY(),//y5-13
                p.getPoints().get(2).getZ()//z5-14
        };

        float k1 = pts[3] - pts[0];
        float k2 = pts[4] - pts[1];
        float k3 = pts[5] - pts[2];
        float a00 = pts[0] - pts[6];
        float a01 = pts[1] - pts[7];
        float a02 = pts[2] - pts[8];
        float a10 = pts[9] - pts[6];
        float a11 = pts[10] - pts[7];
        float a12 = pts[11] - pts[8];
        float a20 = pts[12] - pts[6];
        float a21 = pts[13] - pts[7];
        float a22 = pts[14] - pts[8];

        float k = -(a11 * a22 * k1 + a12 * a20 * k2 + a10 * a21 * k3-a20 * a11 * k3 - a21 * a12 * k1 - a10 * a22 * k2);
        if (Math.abs(k) < EPS) return null;
        float t = (a11 * a22 * a00 + a12 * a20 * a01 + a10 * a21 * a02 - a20 * a11 * a02 - a21 * a12 * a00 - a10 * a22 * a01) / k;

        System.out.println(t);
        return new Vector3(k1 * t + pts[0], k2 * t + pts[1], k3 * t + pts[2]);
    }

    public static Vector3 intersection(Line3D l1, Line3D l2) {
        Vector3 p11 = l1.getP1();
        Vector3 p12 = l1.getP2();
        Vector3 p21 = l2.getP1();
        Vector3 p22 = l2.getP2();
        float[][] matrix = new float[][]{
                {p12.getX() - p11.getX(), p12.getY() - p11.getY(), p12.getZ() - p11.getZ()},
                {p21.getX() - p11.getX(), p21.getY() - p11.getY(), p21.getZ() - p11.getZ()},
                {p22.getX() - p11.getX(), p22.getY() - p11.getY(), p22.getZ() - p11.getZ()}

        };
        if (Math.abs(MathUtils.determinant(matrix)) < EPS) {
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
            if (uv != null && uv[0] <= 1 && uv[1] <= 1 && uv[0] >= 0 && uv[1] >= 0)
                return new Vector3(
                        uv[0] * (p11.getX() - p12.getX()) + p12.getX(),
                        uv[0] * (p11.getY() - p12.getY()) + p12.getY(),
                        uv[0] * (p11.getZ() - p12.getZ()) + p12.getZ()
                );
        }
        return null;
    }

    public static boolean isInside(IModel model, Vector3 v) {

        return false;
    }

    public static boolean isInside(PolyLine3D p, Vector3 v) {
        List<Vector3> pts = p.getPoints();
        Line3D ray = new Line3D(v, new Vector3(
                pts.get(0).getX() + (pts.get(1).getX() - pts.get(0).getX()) * 1E+12f,
                pts.get(0).getY() + (pts.get(1).getY() - pts.get(0).getY()) * 1E+12f,
                pts.get(0).getZ() + (pts.get(1).getZ() - pts.get(0).getZ()) * 1E+12f));
        List<Vector3> inters = new ArrayList<>();
        int i = 1;
        for (; i < pts.size(); i++) {
            Vector3 curr = intersection(ray, new Line3D(pts.get(i), pts.get(i - 1)));
            if (curr != null) {
                inters.add(curr);
            }
        }
        if (intersection(ray, new Line3D(pts.get(0), pts.get(i - 1))) != null)
            inters.add(null);
        return inters.size() % 2 == 1;
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

    private static class PartitionModel {
        private List<PolyLine3D> om1, om2, im1, im2;

        public List<PolyLine3D> getOm1() {
            return om1;
        }

        public List<PolyLine3D> getOm2() {
            return om2;
        }

        public List<PolyLine3D> getIm1() {
            return im1;
        }

        public List<PolyLine3D> getIm2() {
            return im2;
        }

        public PartitionModel(List<PolyLine3D> om1, List<PolyLine3D> om2, List<PolyLine3D> im1, List<PolyLine3D> im2) {
            this.om1 = om1;
            this.om2 = om2;
            this.im1 = im1;
            this.im2 = im2;
        }

    }
}
