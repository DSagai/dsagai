package ru.job4j.dsagai.lesson1.services;

import ru.job4j.dsagai.lesson1.model.*;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Class convert TodoTasks list into XML representation.
 *
 * @author dsagai
 * @version 1.00
 * @since 11.04.2017
 */
public class XmlConverter {
    private static XmlConverter instance;

    private final Marshaller marshaller;

    /**
     * private constructor.
     * @throws JAXBException
     */
    private XmlConverter() throws JAXBException {
        this.marshaller = JAXBContext.newInstance(TodoList.class, TodoTask.class).createMarshaller();
    }

    /**
     * Method returns instance of XmlConverter
     * @return XmlConverter instance of class
     */
    public static XmlConverter getInstance() {
        if (instance == null) {
            try {
                instance = new XmlConverter();
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }

        return instance;
    }

    /**
     * Method converts TodoList into XML.
     * @param list TodoList.
     * @return String TodoList in XML representation.
     */
    public String toXml(TodoList list) {
        StringWriter writer = new StringWriter();
        String result = "";
        try {
            this.marshaller.marshal(list, writer);
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }


}
