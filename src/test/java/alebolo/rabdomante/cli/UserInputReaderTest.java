package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.WaterProfile;
import alebolo.rabdomante.core.WaterProfiles;
import org.assertj.core.api.Condition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(JUnit4.class)
public class UserInputReaderTest {
    @Test public void waters() {
        File file = new File(this.getClass().getResource("/input.xlsx").getFile());
        IUserInputReader it = new UserInputReader(file);
        assertThat(it.waters().size()).isEqualTo(12);
        assertThat(it.waters()).areAtLeastOne(new Condition<>( w -> WaterProfile.sameProfile(w, WaterProfiles.DISTILLED)
                                             , "contiene distillata"));
    }
}