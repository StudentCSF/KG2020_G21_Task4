package course2.kg.task4.draw;

import course2.kg.task4.math.Vector3;
import course2.kg.task4.screen.ScreenConverter;
import course2.kg.task4.screen.ScreenCoordinates;
import course2.kg.task4.screen.ScreenPoint;
import course2.kg.task4.third.PolyLine3D;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class SimpleSideDrawer extends ScreenGraphicsDrawer {
    private static final Random RND = new Random();

    public SimpleSideDrawer(ScreenConverter sc, Graphics2D g) {
        super(sc, g);
    }

    /**
     * Рисует одну полилинию на графиксе.
     *
     * @param polyline полилиния
     */
    @Override
    protected void oneDraw(PolyLine3D polyline) {
        LinkedList<ScreenPoint> points = new LinkedList<>();
        /*переводим все точки в экранные*/
        getGraphics().setColor(polyline.getColor());
        for (Vector3 v : polyline.getPoints())
            points.add(getScreenConverter().r2s(v));
//        getGraphics().setColor(Color.BLACK);
        /*если точек меньше двух, то рисуем отдельными алгоритмами*/
        if (points.size() < 2) {
            if (points.size() > 0)
                getGraphics().fillRect(points.get(0).getI(), points.get(0).getJ(), 1, 1);
            return;
        }
        /*создаём хранилище этих точек в виде двух массивов*/
        ScreenCoordinates crds = new ScreenCoordinates(points);
        /*если линия замкнута - рисуем закрашенный полигон, иначе - полилинию*/
        if (polyline.isClosed()) {
            getGraphics().fillPolygon(crds.getXx(), crds.getYy(), crds.size());
        }
        else
            getGraphics().drawPolyline(crds.getXx(), crds.getYy(), crds.size());
    }

    /**
     * В данной реализации возвращаем фильтр, который одобряет все полилинии.
     *
     * @return фильтр полилиний
     */
    @Override
    protected IFilter<PolyLine3D> getFilter() {
        return new IFilter<PolyLine3D>() {
            @Override
            public boolean permit(PolyLine3D line) {
                return true;
            }
        };
    }

    /**
     * Сравниваем полилинии по среднему Z.
     *
     * @return компаратор
     */
    @Override
    protected Comparator<PolyLine3D> getComparator() {
        return new Comparator<PolyLine3D>() {
            private static final float EPSILON = 1e-10f;

            @Override
            public int compare(PolyLine3D o1, PolyLine3D o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }
}