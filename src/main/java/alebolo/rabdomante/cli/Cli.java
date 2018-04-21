package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.*;
import alebolo.rabdomante.xlsx.ResultWriter;
import alebolo.rabdomante.xlsx.UserInputReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class Cli {
    static Logger logger = LoggerFactory.getLogger(Cli.class);
    public static final String DEFAULT_FILENAME = "rabdomante.xlsx";
    private static final IUserInputReader uiReader = new UserInputReader(new File(DEFAULT_FILENAME));
    private static final IResultWriter resWriter = new ResultWriter(new File(DEFAULT_FILENAME), new File("rabdomante.xlsx"));
    private static void printUsage() {
        System.out.println(
                "Rabdomante versione " + fetchVersion() + ",  utilizzo:"
                        + "\njava -jar rabdomante.jar"
        );
    }

    public static void main(String[] args) {
        logger.info("entrato");
        // TODO add secondsTimeout
        resWriter.write(new ChocoSolver().solve(uiReader.target(), uiReader.salts(), uiReader.waters()).get());
        logger.info("uscito");
    }

    public static String fetchVersion() {
        String version = null;

        // try to load from maven properties first
        try {
            Properties p = new Properties();
            InputStream is = Cli.class.getResourceAsStream("/META-INF/maven/alebolo/rabdomante/pom.properties");
            if (is != null) {
                p.load(is);
                version = p.getProperty("version", "");
            }
        } catch (Exception e) {
            // ignore
        }

        // fallback to using Java API
        if (version == null) {
            Package aPackage = Cli.class.getPackage();
            if (aPackage != null) {
                version = aPackage.getImplementationVersion();
                if (version == null) {
                    version = aPackage.getSpecificationVersion();
                }
            }
        }

        if (version == null) {
            // we could not compute the version so use a blank
            version = "";
        }

        return version;
    }
}
