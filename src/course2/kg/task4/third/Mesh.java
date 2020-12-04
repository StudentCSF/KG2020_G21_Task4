package course2.kg.task4.third;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.third.PolyLine3D;

import java.util.List;

public final class Mesh {
    private final List<PolyLine3D> polygons;
    private final List<Vector3> vertexes;

    public List<PolyLine3D> getPolygons() {
        return polygons;
    }

    public List<Vector3> getVertexes() {
        return vertexes;
    }

    public Mesh(List<PolyLine3D> polygons, List<Vector3> vertexes) {
        this.polygons = polygons;
        this.vertexes = vertexes;
    }
}
