package alebolo.rabdomante.cli;

import alebolo.rabdomante.Msg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import sun.util.locale.LocaleUtils;

import javax.sound.midi.SysexMessage;
import java.util.Locale;
import java.util.ResourceBundle;

@RunWith(JUnit4.class)
public class I18NTest {

    @Test public void foo() {
//        System.out.println(Msg.tableSalt());
//        Msg.changeLocale(Locale.ENGLISH);
//        System.out.println(Msg.tableSalt());

        ResourceBundle en= ResourceBundle.getBundle("messages", Locale.ENGLISH);
        System.out.println(en.getLocale());
        System.out.println(en.getString("TABLE_SALT"));

        ResourceBundle fr= ResourceBundle.getBundle(
                "messages",
                Locale.FRENCH,
                new ResourceBundle.Control() {
                    public Locale getFallbackLocale(String baseName, Locale locale) {
                        return Locale.ENGLISH;
                    }
                });
        System.out.println(fr.getLocale());
        System.out.println(fr.getString("TABLE_SALT"));

        ResourceBundle it= ResourceBundle.getBundle(
                "messages",
                Locale.ITALIAN,
                new ResourceBundle.Control() {
                    public Locale getFallbackLocale(String baseName, Locale locale) {
                        return Locale.ENGLISH;
                    }
                });

        System.out.println(it.getLocale());
        System.out.println(it.getString("TABLE_SALT"));
    }
    @Test
    public void x() {
        System.out.println(ResourceBundle.getBundle("messages").getString("hello"));
        System.out.println(ResourceBundle.getBundle("messages", Locale.ITALIAN).getString("hello"));
        System.out.println(ResourceBundle.getBundle("messages", Locale.ENGLISH).getString("hello"));
        System.out.println(ResourceBundle.getBundle("messages", Locale.FRENCH).getString("hello"));
    }
}
