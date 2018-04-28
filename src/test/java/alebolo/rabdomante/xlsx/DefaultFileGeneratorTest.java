package alebolo.rabdomante.xlsx;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class DefaultFileGeneratorTest {
    @Rule
    public TemporaryFolder tmpDir = new TemporaryFolder();

    @Test
    public void generate() throws IOException {
        File f = tmpDir.newFile("rabdomante_test.xlsx");
        assertThat(f.delete()).isTrue();
        new DefaultFileGenerator().generate(f, true);
        System.out.println("fine");
    }
}