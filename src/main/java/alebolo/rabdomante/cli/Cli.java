package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.Salt;
import alebolo.rabdomante.core.Water;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Cli {
    private static final IUserInputReader uiReader = new UserInputReader(new File("rabdomante.xlsx"));
    private static void printUsage() {
        System.out.println(
                "Rabdomante versione " + fetchVersion() + ",  utilizzo:"
                        + "\njava -jar rabdomante.jar"
        );
    }

    public static void main(String[] args) {
        printUsage();
        List<Water> waters = uiReader.waters();
        List<Salt> salts = uiReader.salts();
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
