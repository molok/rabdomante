package alebolo.rabdomante.cli;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Locale;
import java.util.ResourceBundle;

@RunWith(JUnit4.class)
public class I18NTest {
    @Test
    public void x() {
        System.out.println(ResourceBundle.getBundle("messages").getString("hello"));
        System.out.println(ResourceBundle.getBundle("messages", Locale.ITALIAN).getString("hello"));
        System.out.println(ResourceBundle.getBundle("messages", Locale.FRENCH).getString("hello"));
    }
}
