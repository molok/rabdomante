package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.Salt;
import alebolo.rabdomante.core.Water;

import java.util.List;

public interface IUserInputReader {
    List<Water> waters();

    Water target();

    List<Salt> salts();
}
