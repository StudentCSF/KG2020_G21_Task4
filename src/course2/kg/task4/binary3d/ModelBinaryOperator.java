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
//        PartitionModel model = getPartitionModel(m1, m2);
        PartitionModel model = partitionModel(m1, m2);
        List<PolyLine3D> list = new LinkedList<>();
        list.addAll(model.getOm1());
        list.addAll(model.getOm2());
        return new CustomModel(list);
    }

    public static IModel subtraction(IModel from, IModel that) {
//        PartitionModel model = getPartitionModel(from, that);
        PartitionModel model = partitionModel(from, that);
        List<PolyLine3D> list = new LinkedList<>(model.getOm1());
//        list.addAll(model.getIm2());
        return new CustomModel(list);
    }

    public static IModel intersection(IModel m1, IModel m2) {
//        PartitionModel model = getPartitionModel(m1, m2);
        PartitionModel model = partitionModel(m1, m2);
        List<PolyLine3D> list = new LinkedList<>();
        list.addAll(model.getIm1());
        list.addAll(model.getIm2());
        return new CustomModel(list);
    }

    public static IModel test(IModel m1, IModel m2) {
//        PartitionModel model = getPartitionModel(m1, m2);
        PartitionModel model = partitionModel(m1, m2);
        List<PolyLine3D> list = new LinkedList<>();
        list.addAll(model.getIm1());
        list.addAll(model.getIm2());
        list.addAll(model.getOm1());
        list.addAll(model.getOm2());
        return new CustomModel(list);
    }

    private static PartitionModel getPartitionModel(IModel m1, IModel m2) {
        List<PolyLine3D> o1 = new LinkedList<>();
        List<PolyLine3D> i1 = new LinkedList<>();
        List<PolyLine3D> o2 = new LinkedList<>();
        List<PolyLine3D> i2 = new LinkedList<>();



        Map<PolyLine3D, Set<Vector3>> buf1 = new HashMap<>();
        Map<PolyLine3D, Set<Vector3>> buf2 = new HashMap<>();
        for (PolyLine3D p : m1.getMesh().getPolygons()) {
            Set<Vector3> curr = new LinkedHashSet<>();
            for (Vector3 v : p.getPoints()) {
                if (isInside(m2, v)) {
                    curr.add(v);
                }
            }
            if (curr.size() == 0) o1.add(p);
            else if (curr.size() == p.getPoints().size()) i1.add(p);
            else buf1.put(p, curr);
        }
        for (PolyLine3D p : m2.getMesh().getPolygons()) {
            Set<Vector3> curr = new LinkedHashSet<>();
            for (Vector3 v : p.getPoints()) {
                if (isInside(m1, v)) {
                    curr.add(v);
                }
            }
            if (curr.size() == 0) o2.add(p);
            else if (curr.size() == p.getPoints().size()) i2.add(p);
            else buf2.put(p, curr);
        }
        for (Map.Entry<PolyLine3D, Set<Vector3>> kv : buf1.entrySet()) {
            for (PolyLine3D pol : m2.getMesh().getPolygons()) {
                Line3D line = intersection(kv.getKey(), pol);
                if (line != null) {
                    ArrayList<Vector3> list = new ArrayList<>(kv.getValue());
                    list.add(line.getP1());
                    list.add(line.getP2());
                    sort(list);
                    i1.add(new PolyLine3D(list, true, kv.getKey().getColor()));
                    list = new ArrayList<>();
                    list.add(line.getP1());
                    for (Vector3 vc : kv.getKey().getPoints()) {
                        if (!kv.getValue().contains(vc)) list.add(vc);
                    }
                    list.add(line.getP2());
                    sort(list);
                    o1.add(new PolyLine3D(list, true, kv.getKey().getColor()));
                }
            }
        }

        for (Map.Entry<PolyLine3D, Set<Vector3>> kv : buf2.entrySet()) {
            for (PolyLine3D pol : m1.getMesh().getPolygons()) {
                Line3D line = intersection(kv.getKey(), pol);
                if (line != null) {
                    ArrayList<Vector3> list = new ArrayList<>(kv.getValue());
                    list.add(line.getP1());
                    list.add(line.getP2());
                    sort(list);
                    i2.add(new PolyLine3D(list, true, kv.getKey().getColor()));
                    list = new ArrayList<>();
                    list.add(line.getP1());
                    list.add(line.getP2());
                    for (Vector3 vc : kv.getKey().getPoints()) {
                        if (!kv.getValue().contains(vc)) list.add(vc);
                    }
                    sort(list);
                    o2.add(new PolyLine3D(list, true, kv.getKey().getColor()));
                }
            }
        }
        return new PartitionModel(o1, o2, i1, i2);
    }

    private static PartitionModel partitionModel(IModel m1, IModel m2) {
        List<PolyLine3D> o1 = new LinkedList<>();
        List<PolyLine3D> i1 = new LinkedList<>();
        List<PolyLine3D> o2 = new LinkedList<>();
        List<PolyLine3D> i2 = new LinkedList<>();

        List<PolyLine3D> poly1 = m1.getMesh().getPolygons();
        List<PolyLine3D> poly2 = m2.getMesh().getPolygons();

        for (PolyLine3D p : poly1) separate(o1, i1, poly2, p);
        for (PolyLine3D p : poly2) separate(o2, i2, poly1, p);

        return new PartitionModel(o1, o2, i1, i2);
    }

    private static void separate(List<PolyLine3D> o, List<PolyLine3D> i, List<PolyLine3D> lp, PolyLine3D p) {
        List<Vector3> outers = new ArrayList<>();
        List<Vector3> inners = new ArrayList<>();
        List<Vector3> inters = new ArrayList<>();
        for (Vector3 v : p.getPoints()) {
            if (isInside(lp, v)) inners.add(v);
            else outers.add(v);
        }
        if (inners.size() == p.getPoints().size()) {
            i.add(p);
            return;
        }
        for (PolyLine3D curr : lp) {
//            separate(o, i, curr, p);
            Line3D l = intersection(curr, p);
            if (l != null) {
                inters.add(l.getP1());
                inters.add(l.getP2());
            }
        }
        ArrayList<Vector3> list = new ArrayList<>(inners);
        list.addAll(inters);
        if (list.size() > 0) i.add(new PolyLine3D(sort(list), true, p.getColor()));
        else {
            o.add(p);
            return;
        }
        list = new ArrayList<>(outers);
        list.addAll(inters);
        o.add(new PolyLine3D(sort(list), true, p.getColor()));
    }

//    private static void separate(List<PolyLine3D> o, List<PolyLine3D> i, PolyLine3D tmp, PolyLine3D src) {
//
//    }

    public static PolyLine3D intersection(IModel m, PolyLine3D p) {
        for (PolyLine3D curr : m.getMesh().getPolygons()) {

        }
        return null;
    }

    public static Line3D intersection(PolyLine3D p1, PolyLine3D p2) {
        List<Vector3> lp = p1.getPoints();
        List<Line3D> lines = new ArrayList<>();
        int i = 1;
        for (; i < lp.size(); i++) {
            lines.add(new Line3D(lp.get(i), lp.get(i - 1)));
        }
        lines.add(new Line3D(lp.get(0), lp.get(i - 1)));
        List<Vector3> inters = new ArrayList<>();
        for (Line3D l : lines) {
            Vector3 v = intersection(p2, l);
            if (v != null) inters.add(v);
        }

        lp = p1.getPoints();
        lines = new ArrayList<>();
        i = 1;
        for (; i < lp.size(); i++) {
            lines.add(new Line3D(lp.get(i), lp.get(i - 1)));
        }
        lines.add(new Line3D(lp.get(0), lp.get(i - 1)));
        for (Line3D l : lines) {
            Vector3 v = intersection(p1, l);
            if (v != null) inters.add(v);
        }
//        if (inters.size() == 1) return new Line3D(inters.get(0), inters.get(0));
        if (inters.size() == 2) return new Line3D(inters.get(0), inters.get(1));
//        if (inters.size() > 2) System.out.println(inters.size());
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

        float k = -(a11 * a22 * k1 + a12 * a20 * k2 + a10 * a21 * k3 - a20 * a11 * k3 - a21 * a12 * k1 - a10 * a22 * k2);
        if (Math.abs(k) < EPS) return null;
        float t = (a11 * a22 * a00 + a12 * a20 * a01 + a10 * a21 * a02 - a20 * a11 * a02 - a21 * a12 * a00 - a10 * a22 * a01) / k;
        Vector3 v = new Vector3(k1 * t + pts[0], k2 * t + pts[1], k3 * t + pts[2]);
        return isInside(p, v) ? v : null;
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
                    && (p11.getY() != 0 || p12.getY() != 0 || p21.getY() != 0 || p22.getY() != 0)
                    && (p11.getX() != p12.getX() || p21.getX() != p22.getX())
                    && (p11.getY() != p12.getY() || p21.getY() != p22.getY())) {
                uv = getUV(new float[]{p11.getX(), p12.getX(), p21.getX(), p22.getX(), p11.getY(), p12.getY(), p21.getY(), p22.getY()});
            } else if ((p11.getY() != 0 || p12.getY() != 0 || p21.getY() != 0 || p22.getY() != 0)
                    && (p11.getZ() != 0 || p12.getZ() != 0 || p21.getZ() != 0 || p22.getZ() != 0)
                    && (p11.getZ() != p12.getZ() || p21.getZ() != p22.getZ())
                    && (p11.getY() != p12.getY() || p21.getY() != p22.getY())) {
                uv = getUV(new float[]{p11.getZ(), p12.getZ(), p21.getZ(), p22.getZ(), p11.getY(), p12.getY(), p21.getY(), p22.getY()});

            } else if ((p11.getX() != 0 || p12.getX() != 0 || p21.getX() != 0 || p22.getX() != 0)
                    && (p11.getZ() != 0 || p12.getZ() != 0 || p21.getZ() != 0 || p22.getZ() != 0)
                    && (p11.getX() != p12.getX() || p21.getX() != p22.getX())
                    && (p11.getZ() != p12.getZ() || p21.getZ() != p22.getZ())) {
                uv = getUV(new float[]{p11.getX(), p12.getX(), p21.getX(), p22.getX(), p11.getZ(), p12.getZ(), p21.getZ(), p22.getZ()});

            }
            if (uv != null && uv[0] < 1 && uv[1] < 1 && uv[0] > 0 && uv[1] > 0) {
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
        for (PolyLine3D p : model.getMesh().getPolygons()) {
            Vector3 curr = intersection(p, ray);
            if (curr != null) {
                boolean flag = true;
                for (Vector3 setv : inters) {
                    if (similar(setv, curr)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) inters.add(curr);
            }
        }
        return inters.size() % 2 == 1;
    }

    private static boolean isInside(List<PolyLine3D> lp, Vector3 v) {
        Line3D ray = new Line3D(v, new Vector3(1E+3f, 1E+3f, 1E+3f));
        Set<Vector3> inters = new HashSet<>();
        for (PolyLine3D p : lp) {
            Vector3 curr = intersection(p, ray);
            if (curr != null) {
                boolean flag = true;
                for (Vector3 setv : inters) {
                    if (similar(setv, curr)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) inters.add(curr);
            }
        }
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
        if (Math.abs(MathUtils.determinant(new float[][]{
                {v.getX() - v0.getX(), v.getY() - v0.getY(), v.getZ() - v0.getZ()},
                {v1.getX() - v0.getX(), v1.getY() - v0.getY(), v1.getZ() - v0.getZ()},
                {v2.getX() - v0.getX(), v2.getY() - v0.getY(), v2.getZ() - v0.getZ()}
        })) > 1E-6f) return false;
        float a = (float) Math.sqrt(Math.pow(v0.getX() - v1.getX(), 2) + Math.pow(v0.getY() - v1.getY(), 2) + Math.pow(v0.getZ() - v1.getZ(), 2));
        float b = (float) Math.sqrt(Math.pow(v2.getX() - v1.getX(), 2) + Math.pow(v2.getY() - v1.getY(), 2) + Math.pow(v2.getZ() - v1.getZ(), 2));
        float c = (float) Math.sqrt(Math.pow(v2.getX() - v3.getX(), 2) + Math.pow(v2.getY() - v3.getY(), 2) + Math.pow(v2.getZ() - v3.getZ(), 2));
        float d = (float) Math.sqrt(Math.pow(v0.getX() - v3.getX(), 2) + Math.pow(v0.getY() - v3.getY(), 2) + Math.pow(v0.getZ() - v3.getZ(), 2));
        float s0 = MathUtils.square(new float[]{a, b, c, d});
        float a1 = (float) Math.sqrt(Math.pow(v0.getX() - v.getX(), 2) + Math.pow(v0.getY() - v.getY(), 2) + Math.pow(v0.getZ() - v.getZ(), 2));
        float a2 = (float) Math.sqrt(Math.pow(v1.getX() - v.getX(), 2) + Math.pow(v1.getY() - v.getY(), 2) + Math.pow(v1.getZ() - v.getZ(), 2));
        float s1 = MathUtils.square(new float[]{a, a1, a2});
        float b1 = (float) Math.sqrt(Math.pow(v2.getX() - v.getX(), 2) + Math.pow(v2.getY() - v.getY(), 2) + Math.pow(v2.getZ() - v.getZ(), 2));
        float s2 = MathUtils.square(new float[]{a2, b, b1});
        float c1 = (float) Math.sqrt(Math.pow(v3.getX() - v.getX(), 2) + Math.pow(v3.getY() - v.getY(), 2) + Math.pow(v3.getZ() - v.getZ(), 2));
        float s3 = MathUtils.square(new float[]{b1, c, c1});
        float s4 = MathUtils.square(new float[]{c1, d, a1});
        return Math.abs(s0 - s1 - s2 - s3 - s4) < 1E-3f;
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

    private static List<Vector3> sort(ArrayList<Vector3> src) {
        if (src.size() <= 3) return src;
        List<Vector3> res = new ArrayList<>();
        res.add(src.remove(0));
        while (!src.isEmpty()) {
            int minI = -1;
            float minL = Float.MAX_VALUE;
            Vector3 curr = res.get(res.size() - 1);
            for (int i = 0; i < src.size(); i++) {
                float l = curr.add(src.get(i).mult(-1f)).length();
                if (l < minL) {
                    minI = i;
                    minL = l;
                }
            }
            res.add(src.remove(minI));
        }
        return res;
    }
}
