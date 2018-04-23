package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.*;
import alebolo.rabdomante.xlsx.DefaultFileGenerator;
import alebolo.rabdomante.xlsx.ResultWriter;
import alebolo.rabdomante.xlsx.UserInputReader;
import ch.qos.logback.classic.Level;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Cli {
    static Logger logger = LoggerFactory.getLogger(Cli.class);
    public static final String DEFAULT_FILENAME = "rabdomante.xlsx";

    public static void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "java -jar rabdomante.jar", cliOptions() );
        System.out.println("\n version: " + fetchVersion());
    }

    private static Options cliOptions() {
        Options opts = new Options();

        opts.addOption(Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .optionalArg(true)
                .desc("Prints this message")
                .build());

        opts.addOption(Option.builder("v")
                .longOpt("verbose")
                .hasArg(false)
                .optionalArg(true)
                .desc("Shows details of the computation")
                .build());

        opts.addOption(Option.builder("i")
                .longOpt("input")
                .hasArg(true)
                .optionalArg(true)
                .desc("Input file")
                .build());

        opts.addOption(Option.builder("o")
                .longOpt("output")
                .hasArg(true)
                .optionalArg(true)
                .desc("Output file")
                .build());

        opts.addOption(Option.builder("t")
                .longOpt("timeout")
                .hasArg(true)
                .argName("timeout")
                .optionalArg(true)
                .desc("Maximum wait time (in seconds) for finding the best solution, 60 is the default value")
                .build());
        return opts;
    }

    public static void main(String[] args) { System.exit(doMain(args)); }

    private static void setLogLevel(Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    public static int doMain(String[] args) {
        long start = System.currentTimeMillis();

        try {
            CommandLine opts = new DefaultParser().parse(cliOptions(), args);

            if (opts.hasOption("help")) {
                printUsage();
                return 1;
            }

            if (opts.hasOption("verbose")) {
                setLogLevel(Level.TRACE);
            } else {
                setLogLevel(Level.WARN);
            }

            File input = opts.hasOption("input") ? new File(opts.getOptionValue("input")) : new File(DEFAULT_FILENAME);
            File output = opts.hasOption("output") ? new File(opts.getOptionValue("output")) : new File(DEFAULT_FILENAME);

            if (!input.exists()) {
                System.out.println("File non presente, genero template "+input.getAbsolutePath());
                new DefaultFileGenerator().generate(input);
                System.out.println("Template generato");
                return 1;
            }

            IUserInputReader uiReader = new UserInputReader(input);

            Long timeout = parseLong(opts.getOptionValue("timeout", "60"));

            Optional<WSolution> solution = new ChocoSolver().solve(
                    uiReader.target(),
                    uiReader.salts(),
                    uiReader.waters(),
                    timeout);

            long secondsElapsed = (System.currentTimeMillis() - start) / 1000;
            new ResultWriter(input, output).write(solution.orElseThrow(() -> {throw new RabdoException("Nessuna soluzione trovata");}), secondsElapsed);

            System.out.println(solution.get().searchCompleted ? "Soluzione trovata!" : "Ricerca incompleta!");

            return 0;
        } catch (Throwable e) {
            System.err.println("Soluzione NON trovata!");
            System.err.println("==================================== ERROR =====================================");
            e.printStackTrace();
            System.err.println("================================================================================");
            return 66;
        } finally {
            System.out.println("Execution time: " + String.format("%.03f", (System.currentTimeMillis() - start) / 1000.) + "s");
        }
    }

    private static Long parseLong(String s) {
        if (s == null || "".equals(s)) { return null; }
        return Long.parseLong(s);
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
