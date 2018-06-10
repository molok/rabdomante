package alebolo.rabdomante.core;

import alebolo.rabdomante.Msg;
import org.junit.Test;

public class I18N {
    @Test
    public void accenti() {
        System.out.println(
            Msg.getString("__test")
        );
    }
}
