package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.*;
import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnit4.class)
public class UserInputReaderTest {
    @Test public void readWaters() {
        File file = new File(this.getClass().getResource("/input.xlsx").getFile());
        IUserInputReader it = new UserInputReader(file);
        assertThat(it.waters().size()).isEqualTo(12);
        assertThat(it.waters()).areAtLeastOne(new Condition<>( w -> WaterProfile.sameProfile(w, WaterProfiles.DISTILLED)
                                             , "contiene distillata"));
    }

    @Test public void readSalts() {
        File file = new File(this.getClass().getResource("/input.xlsx").getFile());
        IUserInputReader it = new UserInputReader(file);
        assertThat(it.salts().size()).isEqualTo(8);
        assertThat(it.salts()).areAtLeastOne(new Condition<>( w -> SaltProfile.sameProfile(w, SaltProfiles.TABLE_SALT)
                , "contiene sale"));
    }

    @Test public void readTarget() {
        File file = new File(this.getClass().getResource("/input.xlsx").getFile());
        IUserInputReader it = new UserInputReader(file);
        Water target = it.target();

        assertThat(WaterProfile.sameProfile(target, new WaterProfile("x", 100, 100, 100, 100, 100, 100))).isTrue();
    }
}