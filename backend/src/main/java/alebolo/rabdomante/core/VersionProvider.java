package alebolo.rabdomante.core;

import alebolo.rabdomante.cli.Cli;

import java.io.InputStream;
import java.util.Properties;

public class VersionProvider {
    public String fetchVersion() {
        String version = null;

        // try to load from maven properties first
        try {
            Properties p = new Properties();
            InputStream is = this.getClass().getResourceAsStream("/META-INF/maven/alebolo/rabdomante/pom.properties");
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
            version = "0.0";
        }

        return version;
    }
}
