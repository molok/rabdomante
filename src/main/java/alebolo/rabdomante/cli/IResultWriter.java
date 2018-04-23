package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.WSolution;

public interface IResultWriter {
    void write(WSolution solution, long secondsElapsed);
}
