package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.core.Water;
import au.com.bytecode.opencsv.CSVReader;
import org.apache.commons.text.WordUtils;
import org.apache.poi.util.IOUtils;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

import static alebolo.rabdomante.xlsx.KnownWatersProvider.COLUMNS.*;
import static alebolo.rabdomante.xlsx.Utils.capitalize;

public class KnownWatersProvider {
    private static final char DELIMITATOR = ';';
    private static final char VUOTO = '\0';
    enum COLUMNS { ID, NAME, CA, MG, NA, SO4, CL, HCO3 };

    public List<Water> knownWaters() {
        CSVReader reader = null;
        try {
            reader = new CSVReader(
                    new InputStreamReader(this.getClass().getResourceAsStream("/waters.csv")),
                    DELIMITATOR, VUOTO);

            return reader.readAll().stream()
                    .skip(1)
                    .map(r -> new Water(
                            toInt(r[CA.ordinal()]),
                            toInt(r[MG.ordinal()]),
                            toInt(r[NA.ordinal()]),
                            toInt(r[SO4.ordinal()]),
                            toInt(r[CL.ordinal()]),
                            toInt(r[HCO3.ordinal()]),
                            capitalize(r[NAME.ordinal()]),
                            0))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    private int toInt(String s) {
        return (int) Math.round(Double.parseDouble(s));
    }
}
