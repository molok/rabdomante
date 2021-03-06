package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.*;
import alebolo.rabdomante.xlsx.Constants;
import alebolo.rabdomante.xlsx.ResultWriter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static alebolo.rabdomante.core.SaltProfiles.GYPSUM;
import static alebolo.rabdomante.core.SaltProfiles.TABLE_SALT;
import static alebolo.rabdomante.core.WaterProfiles.BOARIO;
import static alebolo.rabdomante.core.WaterProfiles.DISTILLED;
import static org.assertj.core.api.Java6Assertions.assertThat;


@RunWith(JUnit4.class)
public class ResultWriterTest {
    @Rule public TemporaryFolder tmpDir = new TemporaryFolder();

    @Test public void x() {
        System.out.println(Constants.SHEETS.RESULT.localizedName());
    }

    @Test public void writes() throws IOException {
        File output = tmpDir.newFile("testfile.xlsx");
        File input = new File(this.getClass().getResource("/input.xlsx").getFile());
        assertThat(input.exists()).isTrue();
        ResultWriter it = new ResultWriter(input, output);
        it.write(
                new WSolution(
                    new Recipe(
                            Arrays.asList(new Water(DISTILLED, 10), new Water(BOARIO, 20)),
                            Arrays.asList(new Salt(GYPSUM, 10), new Salt(TABLE_SALT, 20)), new Water(WaterProfiles.DISTILLED, 20), 100 ),
                        true), 100,
                new Water(WaterProfiles.DISTILLED, 10));
    }
}