package alebolo.rabdomante.cli;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.core.App;
import alebolo.rabdomante.core.Defect;
import alebolo.rabdomante.core.VersionProvider;
import alebolo.rabdomante.gui.Gui;
import ch.qos.logback.classic.Level;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.LocaleUtils;

import java.io.File;

public class Cli {
    public static final String DEFAULT_FILENAME = "rabdomante.xlsx";
    private final VersionProvider versionProvider = new VersionProvider();

    public static void main(String[] args) { System.exit(new Cli().doMain(args)); }

    enum RetCode {
        ok(0), incompleteSolution(1), noSolution(2), err(66);

        private final int val;
        RetCode(int val) {
            this.val = val;
        }
    }

    public int doMain(String[] args) {
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

            if (opts.hasOption("no-gui")) {
                return cli(opts);
            } else {
                Gui.main(args);
                return RetCode.ok.val;
            }
        } catch (Throwable e) {
            System.err.println(Msg.solutionNotFound());
            System.err.println("==================================== ERROR =====================================");
            e.printStackTrace();
            System.err.println("================================================================================");
            return RetCode.err.val;
        }
    }

    private int cli(CommandLine opts) {
        App app = new App();

        File input = opts.hasOption("input") ? new File(opts.getOptionValue("input")) : new File(DEFAULT_FILENAME);
        File output = opts.hasOption("output") ? new File(opts.getOptionValue("output")) : new File(DEFAULT_FILENAME);

        if (!input.exists()) {
            System.out.println(Msg.fileNotFoundTemplateGenerated() +" " +input.getAbsolutePath());
            app.generate(input, false);
            System.out.println(Msg.templateGenerated());
            return 1;
        }

        App.Result solution = app.calc(input, output, parseLong(opts.getOptionValue("timelimit", "60")));

        switch (solution.res) {
            case NONE: System.out.println(Msg.noSolutionFound()); return RetCode.noSolution.val;
            case INCOMPLETE: System.out.println(Msg.searchInterrupted()); return  RetCode.incompleteSolution.val;
            case OPTIMAL: System.out.println(Msg.optimalSolutionFoudn()); return RetCode.ok.val;
            default: throw new Defect("I shouldn't be here");
        }
    }

    public void printUsage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "java -jar rabdomante.jar", cliOptions() );
        System.out.println("\nversion: " + versionProvider.fetchVersion());
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
                .longOpt("timelimit")
                .hasArg().argName("seconds")
                .desc(Msg.timeLimitDescription())
                .build());

        opts.addOption(Option.builder("l")
                .longOpt("locale")
                .hasArg().argName("locale")
                .desc(Msg.locale())
                .build());

        opts.addOption(Option.builder("c")
                .longOpt("no-gui")
                .desc(Msg.noGui())
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

}
