package course2.kg.task4.binary3d;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Line3D;
import course2.kg.task4.third.IModel;
import course2.kg.task4.third.Mesh;
import course2.kg.task4.third.PolyLine3D;
import course2.kg.task4.utils.MathUtils;

import java.util.*;


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
//            System.out.println(2);
        }
        return null;
    }

    public static Vector3 intersection(PolyLine3D p, Line3D l) {
        float[] pts = new float[]{
                l.getP1().getX(),
                l.getP1().getY(),
                l.getP1().getZ(),
                l.getP2().getX(),
                l.getP2().getY(),
                l.getP2().getZ(),
                p.getPoints().get(0).getX(),//x3-6
                p.getPoints().get(0).getY(),//y3-7
                p.getPoints().get(0).getZ(),//z3-8
                p.getPoints().get(3).getX(),//x4-9
                p.getPoints().get(3).getY(),//y4-10
                p.getPoints().get(3).getZ(),//z4-11
                p.getPoints().get(2).getX(),//x5-12
                p.getPoints().get(2).getY(),//y5-13
                p.getPoints().get(2).getZ()//z5-14
        };

        if (MathUtils.determinant(new float[][]{
                {pts[0] - pts[6], pts[1] - pts[7], pts[2] - pts[8]},
                {pts[9] - pts[6], pts[10] - pts[7], pts[11] - pts[8]},
                {pts[12] - pts[6], pts[13] - pts[7], pts[14] - pts[8]},
        }) * MathUtils.determinant(new float[][]{
                {pts[3] - pts[6], pts[4] - pts[7], pts[5] - pts[8]},
                {pts[9] - pts[6], pts[10] - pts[7], pts[11] - pts[8]},
                {pts[12] - pts[6], pts[13] - pts[7], pts[14] - pts[8]},
        }) >= 0) return null;

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
//        System.out.println(k1);
//        System.out.println(k2);
//        System.out.println(k3);
//        System.out.println(pts[0]);
//        System.out.println(pts[1]);
//        System.out.println(pts[2]);

        float k = -(a11 * a22 * k1 + a12 * a20 * k2 + a10 * a21 * k3 - a20 * a11 * k3 - a21 * a12 * k1 - a10 * a22 * k2);
//        System.out.println(k);
        if (Math.abs(k) < EPS) return null;
        float t = (a11 * a22 * a00 + a12 * a20 * a01 + a10 * a21 * a02 - a20 * a11 * a02 - a21 * a12 * a00 - a10 * a22 * a01) / k;
//        System.out.println(t);
        Vector3 v = new Vector3(k1 * t + pts[0], k2 * t + pts[1], k3 * t + pts[2]);
//        System.out.println(v.getX() + " " + v.getY() + " " + v.getZ());
//        System.out.println(v.getX() + " " + v.getY() + " " + v.getZ());
//        System.out.println(v.getX() + " " + v.getY() + " " + v.getZ());
//        System.out.println("check   -   " + MathUtils.determinant(new float[][]{
//                {v.getX() - pts[6], v.getY() - pts[7], v.getZ() - pts[8]},
//                {pts[9] - pts[6], pts[10] - pts[7], pts[11] - pts[8]},
//                {pts[12] - pts[6], pts[13] - pts[7], pts[14] - pts[8]}
//        }));
//        System.out.println("da  " + v.getX() + " " + v.getY() + " " + v.getZ());
//        System.out.println(pts[9] + " " + pts[10] + " " + pts[11]);
//        System.out.println(v.getX() + " " + v.getY() + " " + v.getZ());
        return isInside(p, v) ? v : null;
//        return v;
    }

    public static Vector3 intersection(Line3D l1, Line3D l2) {
        Vector3 p11 = l1.getP1();
        Vector3 p12 = l1.getP2();
        Vector3 p21 = l2.getP1();
        Vector3 p22 = l2.getP2();
//        System.out.println("-----------------------");
//        System.out.println(p11.getX() + " " + p11.getY()+ ", " + p11.getZ());
//        System.out.println(p12.getX() + " " + p12.getY()+ ", " + p12.getZ());
//        System.out.println(p21.getX() + " " + p21.getY()+ ", " + p21.getZ());
//        System.out.println(p22.getX() + " " + p22.getY()+ ", " + p22.getZ());
//        System.out.println("**********************************");
        float[][] matrix = new float[][]{
                {p12.getX() - p11.getX(), p12.getY() - p11.getY(), p12.getZ() - p11.getZ()},
                {p21.getX() - p11.getX(), p21.getY() - p11.getY(), p21.getZ() - p11.getZ()},
                {p22.getX() - p11.getX(), p22.getY() - p11.getY(), p22.getZ() - p11.getZ()}

        };
        if (Math.abs(MathUtils.determinant(matrix)) < EPS) {
            float[] uv = null;
            if ((p11.getX() != 0 || p12.getX() != 0 || p21.getX() != 0 || p22.getX() != 0)
                    && (p11.getY() != 0 || p12.getY() != 0 || p21.getY() != 0 || p22.getY() != 0)
                    && (p11.getX() != p12.getX() || p21.getX() != p22.getX())
                    && (p11.getY() != p12.getY() || p21.getY() != p22.getY())) {
//                System.out.println("p " + p12.getY());
                uv = getUV(new float[]{p11.getX(), p12.getX(), p21.getX(), p22.getX(), p11.getY(), p12.getY(), p21.getY(), p22.getY()});
//                System.out.println("f");
            } else if ((p11.getY() != 0 || p12.getY() != 0 || p21.getY() != 0 || p22.getY() != 0)
                    && (p11.getZ() != 0 || p12.getZ() != 0 || p21.getZ() != 0 || p22.getZ() != 0)
                    && (p11.getZ() != p12.getZ() || p21.getZ() != p22.getZ())
                    && (p11.getY() != p12.getY() || p21.getY() != p22.getY())) {
                uv = getUV(new float[]{p11.getZ(), p12.getZ(), p21.getZ(), p22.getZ(), p11.getY(), p12.getY(), p21.getY(), p22.getY()});
//                System.out.println("g");

            } else if ((p11.getX() != 0 || p12.getX() != 0 || p21.getX() != 0 || p22.getX() != 0)
                    && (p11.getZ() != 0 || p12.getZ() != 0 || p21.getZ() != 0 || p22.getZ() != 0)
                    && (p11.getX() != p12.getX() || p21.getX() != p22.getX())
                    && (p11.getZ() != p12.getZ() || p21.getZ() != p22.getZ())) {
                uv = getUV(new float[]{p11.getX(), p12.getX(), p21.getX(), p22.getX(), p11.getZ(), p12.getZ(), p21.getZ(), p22.getZ()});
//                System.out.println("h");

            }
//            System.out.println("uv " + uv[0] + " " + uv[1]);
//            System.out.println(p12.getY());
//            System.out.println(Arrays.toString(uv));
            if (uv != null && uv[0] < 1 && uv[1] < 1 && uv[0] > 0 && uv[1] > 0) {
//                System.out.println("gr");
                return new Vector3(
                        uv[0] * (p11.getX() - p12.getX()) + p12.getX(),
                        uv[0] * (p11.getY() - p12.getY()) + p12.getY(),
                        uv[0] * (p11.getZ() - p12.getZ()) + p12.getZ()
                );
            }
        }
        return null;
    }

    public static boolean isInside(IModel model, Vector3 v) {
        Line3D ray = new Line3D(v, new Vector3(1E+3f, 1E+3f, 1E+3f));
        Set<Vector3> inters = new HashSet<>();
//        int counter = 0;
        for (PolyLine3D p : model.getMesh().getPolygons()) {
            Vector3 curr = intersection(p, ray);
//            System.out.println("curr - " + curr);
            if (curr != null) {
                boolean flag = true;
                for (Vector3 setv : inters) {
                    if (similar(setv, curr)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) inters.add(curr);
//                System.out.println(curr.getX() + " " + curr.getY() + " " + curr.getZ());
//                for (Vector3 c : p.getPoints()) {
//                    System.out.println("vs - " + c.getX() + " " + c.getY() + " " + c.getZ());
//                }
//                System.out.println("c = " + counter);
            }
//            counter++;
        }
        System.out.println(inters.size());
        return inters.size() % 2 == 1;
    }

    private static boolean similar(Vector3 v1, Vector3 v2) {
        return Math.abs(v1.getX() - v2.getX()) < 0.1f && Math.abs(v1.getY() - v2.getY()) < 0.1f && Math.abs(v1.getZ() - v2.getZ()) < 0.1f;
    }

    /*public static boolean isInside(PolyLine3D p, Vector3 v) {
        List<Vector3> pts = p.getPoints();
        String x = String.valueOf(v.getX());
        String y = String.valueOf(v.getY());
        String z = String.valueOf(v.getZ());
        Line3D ray = new Line3D(v, new Vector3(
                pts.get(0).getX() + (pts.get(1).getX() - pts.get(0).getX()) * 2E+0f,
                pts.get(0).getY() + (pts.get(1).getY() - pts.get(0).getY()) * 2E+0f,
                pts.get(0).getZ() + (pts.get(1).getZ() - pts.get(0).getZ()) * 2E+0f));
//        System.out.println(ray.getP2().getX() + " " + ray.getP2().getY() + " " + ray.getP2().getZ());
        Set<Vector3> inters = new HashSet<>();
        int i = 1;
        for (; i < pts.size(); i++) {
            Vector3 curr = intersection(ray, new Line3D(pts.get(i), pts.get(i - 1)));
            if (curr != null) inters.add(curr);
        }
        Vector3 last = intersection(ray, new Line3D(pts.get(0), pts.get(i - 1)));
        if (last != null) inters.add(last);
        System.out.println(inters.size());
        return inters.size() % 2 == 1;
    }*/

    public static boolean isInside(PolyLine3D pol, Vector3 v) {
        if (pol.getPoints().size() != 4) System.out.println("isInside error");
        Vector3 v0 = pol.getPoints().get(0);
        Vector3 v1 = pol.getPoints().get(1);
        Vector3 v2 = pol.getPoints().get(2);
        Vector3 v3 = pol.getPoints().get(3);
//        System.out.println(MathUtils.determinant(new float[][]{
//                {v.getX() - v0.getX(), v.getY() - v0.getY(), v.getZ() - v0.getZ()},
//                {v1.getX() - v0.getX(), v1.getY() - v0.getY(), v1.getZ() - v0.getZ()},
//                {v2.getX() - v0.getX(), v2.getY() - v0.getY(), v2.getZ() - v0.getZ()}
//        }));
        if (Math.abs(MathUtils.determinant(new float[][]{
                {v.getX() - v0.getX(), v.getY() - v0.getY(), v.getZ() - v0.getZ()},
                {v1.getX() - v0.getX(), v1.getY() - v0.getY(), v1.getZ() - v0.getZ()},
                {v2.getX() - v0.getX(), v2.getY() - v0.getY(), v2.getZ() - v0.getZ()}
        })) > 1E-6f) return false;
        float a = (float)Math.sqrt(Math.pow(v0.getX() - v1.getX(), 2) + Math.pow(v0.getY() - v1.getY(), 2) + Math.pow(v0.getZ() - v1.getZ(), 2));
        float b = (float)Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(), 2) + Math.pow(v2.getZ() - v1.getZ(), 2));
        float c = (float)Math.sqrt(Math.pow(v2.getX() - v3.getX(), 2) + Math.pow(v2.getY() - v3.getY(), 2) + Math.pow(v2.getZ() - v3.getZ(), 2));
        float d = (float)Math.sqrt(Math.pow(v0.getX() - v3.getX(), 2) + Math.pow(v0.getY() - v3.getY(), 2) + Math.pow(v0.getZ() - v3.getZ(), 2));
        float s0 = square(new float[]{a, b, c, d});
        float a1 = (float)Math.sqrt(Math.pow(v0.getX() - v.getX(), 2) + Math.pow(v0.getY() - v.getY(), 2) + Math.pow(v0.getZ() - v.getZ(), 2));
        float a2 = (float)Math.sqrt(Math.pow(v1.getX() - v.getX(), 2) + Math.pow(v1.getY() - v.getY(), 2) + Math.pow(v1.getZ() - v.getZ(), 2));
        float s1 = square(new float[]{0, a, a1, a2});
        float b1 = (float)Math.sqrt(Math.pow(v2.getX() - v.getX(), 2) + Math.pow(v2.getY() - v.getY(), 2) + Math.pow(v2.getZ() - v.getZ(), 2));
        float s2 = square(new float[]{0, a2, b, b1});
        float c1 = (float)Math.sqrt(Math.pow(v3.getX() - v.getX(), 2) + Math.pow(v3.getY() - v.getY(), 2) + Math.pow(v3.getZ() - v.getZ(), 2));
        float s3 = square(new float[]{0, b1, c, c1});
        float s4 = square(new float[]{0, c1, d, a1});
//        System.out.println(Math.abs(s0 - s1 - s2 - s3 - s4));
        return Math.abs(s0 - s1 - s2 - s3 - s4) < 1E-3f;
    }

    private static float square(float[] arr) {
        float p = 0;
        for (float curr : arr) p += curr;
        p /= 2;
        float sqr = 1;
        for (float curr : arr) sqr *= p - curr;
        return (float)Math.sqrt(sqr);
    }

    public static Vector3 projection(PolyLine3D p, Vector3 v) {
        return null;
    }

    private static float[] getUV(float[] arr) {
//        System.out.println(Arrays.toString(arr));
        float[] res = new float[2];
        float[][] m = {
                {arr[0] - arr[1], arr[3] - arr[2]},
                {arr[4] - arr[5], arr[7] - arr[6]}
        };
//        for (float[] c : m) System.out.println(Arrays.toString(c));

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
//        System.out.println(det);
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
