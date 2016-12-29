package ru.job4j.dsagai.lesson3.food;




import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import java.util.Properties;

/**
 * Created by QQQ on 28.12.2016.
 */
public class ResourcesTest {

    @Test
    public void resourceTest() throws Exception {
        Properties properties = new Properties();
        ClassLoader loader = Food.class.getClassLoader();
        properties.load(loader.getResourceAsStream("lesson3/foodStorApp.properties"));
        assertThat(properties.get("borderFresh.fresh"), is("0.25"));

    }
}
