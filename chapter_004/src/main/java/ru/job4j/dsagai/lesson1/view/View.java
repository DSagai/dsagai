package ru.job4j.dsagai.lesson1.view;

import ru.job4j.dsagai.lesson1.InteractCalc;

import java.util.Set;

/**
 * Interface View
 * specifies mandatory methodsfor view part
 * @author dsagai
 * @since 22.12.2016
 */
public interface View {

    /**
     * sets up and inits properties of View
     * @param controller InteractCalc.
     * @param operations Set<String>.
     */
    void init(InteractCalc controller, Set<String> operations);

    /**
     * updates view after result is ready
     * @param result
     */
    void update(double result);

}
