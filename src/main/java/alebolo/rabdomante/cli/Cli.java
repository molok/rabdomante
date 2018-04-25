package alebolo.rabdomante.cli;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.core.*;
import alebolo.rabdomante.xlsx.DefaultFileGenerator;
import alebolo.rabdomante.xlsx.ResultWriter;
import alebolo.rabdomante.xlsx.UserInputReader;
import ch.qos.logback.classic.Level;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.LocaleUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class Cli {
    public static final String DEFAULT_FILENAME = "rabdomante.xlsx";

    public static void main(String[] args) { System.exit(new Cli().doMain(args)); }

    public int doMain(String[] args) {
        long start = System.currentTimeMillis();

        try {
            CommandLine opts = new DefaultParser().parse(cliOptions(), args);

            if (opts.hasOption("locale")) {
                Msg.changeLocale(LocaleUtils.toLocale(opts.getOptionValue("locale")));
            }

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
                System.out.println(Msg.fileNotFoundTemplateGenerated() +" " +input.getAbsolutePath());
                new DefaultFileGenerator().generate(input);
                System.out.println(Msg.templateGenerated());
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
            new ResultWriter(input, output).write(solution.orElseThrow(() -> new RabdoException(Msg.noSolutionFound())), secondsElapsed);

            System.out.println(solution.get().searchCompleted ? Msg.optimalSolutionFoudn() : Msg.searchInterrupted());

            return 0;
        } catch (Throwable e) {
            System.err.println(Msg.solutionNotFound());
            System.err.println("==================================== ERROR =====================================");
            e.printStackTrace();
            System.err.println("================================================================================");
            return 66;
        } finally {
            System.out.println(Msg.executionTime() + ": " + String.format("%.03f", (System.currentTimeMillis() - start) / 1000.) + "s");
        }
    }

    public void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "java -jar rabdomante.jar", cliOptions() );
        System.out.println("\nversion: " + fetchVersion());
    }

    private Options cliOptions() {
        Options opts = new Options();

        opts.addOption(Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .desc(Msg.printsThisMessage())
                .build());

        opts.addOption(Option.builder("v")
                .longOpt("verbose")
                .hasArg(false)
                .desc(Msg.verboseDescription())
                .build());

        opts.addOption(Option.builder("i")
                .longOpt("input")
                .hasArg().argName("file")
                .desc(Msg.inputFile())
                .build());

        opts.addOption(Option.builder("o")
                .longOpt("output")
                .hasArg().argName("file")
                .desc(Msg.outputFile())
                .build());

        opts.addOption(Option.builder("t")
                .longOpt("timeout")
                .hasArg().argName("timeout")
                .desc(Msg.timeoutDescription())
                .build());

        opts.addOption(Option.builder("l")
                .longOpt("locale")
                .hasArg().argName("locale")
                .desc(Msg.locale())
                .build());
        return opts;
    }

    private void setLogLevel(Level level) {
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) org.slf4j.LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(level);
    }

    private Long parseLong(String s) {
        if (s == null || "".equals(s)) { return null; }
        return Long.parseLong(s);
    }

    public String fetchVersion() {
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
