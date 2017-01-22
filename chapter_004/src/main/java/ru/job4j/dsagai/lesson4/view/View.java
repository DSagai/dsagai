package ru.job4j.dsagai.lesson4.view;




/**
 * Interface View
 * specifies mandatory methods for view part
 * @author dsagai
 * @version 1.00
 * @since 18.01.2017
 */
public interface View {


    /**
     * sets up and inits Menu
     */
    void initView();

    /**
     * updates view after result is ready
     */
    void update();

    /**
     * terminates program.
     */
    void terminate();

}
