package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.WSolution;
import alebolo.rabdomante.core.Water;

public interface IResultWriter {
    void write(WSolution solution, long secondsElapsed, Water target);
}
