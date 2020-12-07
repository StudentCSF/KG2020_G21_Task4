package course2.kg.task4.third;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.models.Line3D;
import course2.kg.task4.third.PolyLine3D;

import java.util.List;

public final class Mesh {
    private final List<PolyLine3D> polygons;
    private final List<Vector3> vertexes;
    private final List<Line3D> edges;

    public Mesh(List<PolyLine3D> polygons, List<Vector3> vertexes, List<Line3D> edges) {
        this.polygons = polygons;
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<PolyLine3D> getPolygons() {
        return polygons;
    }

    public List<Vector3> getVertexes() {
        return vertexes;
    }

    public List<Line3D> getEdges() {
        return edges;
    }
}
