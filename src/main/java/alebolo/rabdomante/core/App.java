package alebolo.rabdomante.core;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.IUserInputReader;
import alebolo.rabdomante.xlsx.DefaultFileGenerator;
import alebolo.rabdomante.xlsx.ResultWriter;
import alebolo.rabdomante.xlsx.UserInputReader;

import java.io.File;
import java.util.Optional;

public class App {
    public void generate(File input, boolean overwrite) {
        new DefaultFileGenerator().generate(input, overwrite);
    }

    public enum SOLUTION { NONE, OPTIMAL, INCOMPLETE }

    public static class Result {
        public final SOLUTION res;
        public final long elapsedMs;

        Result(SOLUTION res, long elapsedMs) {
            this.res = res;
            this.elapsedMs = elapsedMs;
        }
    }

    public Result calc(File input, File output, Long timeout) {
        long start = System.currentTimeMillis();
        try {
            IUserInputReader uiReader = new UserInputReader(input);
            Optional<WSolution> maybeSolution = new ChocoSolver().solve(
                    uiReader.target(),
                    uiReader.salts(),
                    uiReader.waters(),
                    timeout);

            long secondsElapsed = timePast(start) / 1000;

            if (!maybeSolution.isPresent()) {
                return new Result(SOLUTION.NONE, secondsElapsed);
            } else {
                WSolution solution = maybeSolution.get();
                new ResultWriter(input, output).write(solution, secondsElapsed);

                return solution.searchCompleted ?
                        new Result(SOLUTION.OPTIMAL, secondsElapsed) :
                        new Result(SOLUTION.INCOMPLETE, secondsElapsed);
            }
        } finally {
            System.out.println(Msg.executionTime() + ": " + String.format("%.03f", timePast(start)/1000.) + "s");
        }
    }

    private long timePast(long start) {
        return (System.currentTimeMillis() - start);
    }
}
