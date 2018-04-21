package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.Recipe;

public interface IResultWriter {
    void write(Recipe res);
}
