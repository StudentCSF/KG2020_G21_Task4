/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.task4.third;

import java.util.List;

/**
 * Описывает в общем виде любую модель
 * @author Alexey
 */
public interface IModel {
    /**
     * Любая модель - это набор полилиний.
     * @return Списко полилиний модели.
     */
    List<PolyLine3D> getLines();
}
