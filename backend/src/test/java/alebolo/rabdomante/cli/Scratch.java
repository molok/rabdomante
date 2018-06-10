package alebolo.rabdomante.cli;

import org.junit.Test;

public class Scratch {
    @Test
    public void fmt() {
        System.out.println(String.format("%.03f", 1.1234));
        System.out.println(String.format("%.01f", 1.1234));

    }
}
