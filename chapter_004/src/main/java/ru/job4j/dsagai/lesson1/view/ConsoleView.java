package ru.job4j.dsagai.lesson1.view;

import ru.job4j.dsagai.lesson1.InteractCalc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * Class ConsoleView
 * implementation of View interface for
 * communication with user through console.
 * @author dsagai
 * @since 22.12.2016
 */
public class ConsoleView implements View {
    /**
     * application close command.
     */
    public static final String  EXIT_COMMAND = "exit";

    /**
     * controller.
     */
    private InteractCalc controller;

    /**
     * available operations.
     */
    private Set<String> operations;

    /**
     * string containing list of available operations.
     */
    private String operationsInfo;


    @Override
    /**
     * sets up and inits properties of View
     * @param controller InteractCalc.
     * @param operations Set<String>.
     */
    public void init(final InteractCalc controller, final Set<String>  operations) {
        this.controller = controller;
        this.operations = operations;
        ConsoleReader reader = new ConsoleReader(this);
        initOperationsInfo();
        reader.start();
        showMenu();

    }

    /**
     * inits operationsInfo property
     */
    public void initOperationsInfo() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for (String tag : operations){
            stringBuilder.append(tag);
            stringBuilder.append(" ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("]");
        this.operationsInfo = stringBuilder.toString();
    }

    @Override
    /**
     * updates view after result is ready.
     * @param result
     */
    public void update(double result) {
        System.out.printf("Result equals to %.2f \n", result);
    }

    /**
     * Asks for executing arithmetic expression
     * @param execStr String contains arithmetic expression
     */
    public void executeString(String execStr) {
        if (execStr.isEmpty()){
            controller.execute();
        } else {

            String[] splitStr = execStr.split(" ");
            double first;
            double second;
            String operation;
            try {
                switch (splitStr.length) {
                    case 3:
                        first = Double.parseDouble(splitStr[0]);
                        operation = splitStr[1];
                        second = Double.parseDouble(splitStr[2]);
                        controller.execute(operation, first, second);
                        break;
                    case 2:
                        operation = splitStr[0];
                        first = Double.parseDouble(splitStr[1]);
                        controller.execute(operation, first);
                        break;
                }
            } catch (Exception e) {
                System.out.println("Illegal expression format!");
            }
        }
    }

    /**
     * shows basic commands
     * and examples of use
     */
    private void showMenu() {
        String operationSample =  operations.iterator().next();
        System.out.printf("Avaliable operations: %s \n", this.operationsInfo);
        System.out.printf("Example of execution expression: 5.4 %s 6.2\n", operationSample);
        System.out.printf("Example if you want to use result of previous calculation: %s 6.2\n", operationSample);
        System.out.println("Or just press Enter, if you want to repeat previous operation.");
        System.out.printf("Enter %s to close application\n", ConsoleView.EXIT_COMMAND);

        System.out.println("Important: you need to separate numbers and operatins by white spaces.");
    }

    /**
     * ConsoleReader
     * service class for ConsoleView
     * listening console input.
     * @author dsagai
     * @since 22.12.2016
     */
    private static class ConsoleReader extends Thread {
        /**
         * ConsoleView link
         */
        private final ConsoleView listener;

        /**
         * reader
         */
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        /**
         * default constructor.
         * @param listener ConsoleView.
         */
        public ConsoleReader(final ConsoleView listener) {
            this.listener = listener;
        }

        /**
         * main loop.
         * listens console input
         * and ask View class for execution.
         */
        private void listen() {
            String execStr;
            try {
                while (!(execStr = reader.readLine()).equals(ConsoleView.EXIT_COMMAND)
                        && !isInterrupted()) {
                    listener.executeString(execStr);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        /**
         * starts thread
         */
        public void run() {
            listen();
        }
    }
}
