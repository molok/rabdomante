package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.RabdoException;

import java.util.Arrays;
import java.util.Optional;

public class Constants {
    static public final int HEADER_ROWS = 1;
    public enum SHEETS {
        WATER("AVAILABLE_WATERS", "U1"),
        SALTS("SALTS", "U2"),
        TARGET("TARGET", "U3"),
        RESULT("RECIPE", "R"),
        KNOWN_WATERS("BOTTLED_WATERS", "D1"),
        COMMON_PROFILES("COMMON_PROFILES", "D2");

        public String localizedName() { return this.shortHand + ". " + Msg.getString(this.uiName); }
        private final String uiName;
        public final String shortHand;

        SHEETS(String name, String shortHand) {
            this.uiName = name;
            this.shortHand = shortHand;
        }

        Optional<SHEETS> fromLocalizedName(String localName) {
            String _shortHand = localName.split("\\.")[0];
            return Arrays.stream(SHEETS.values())
                          .filter(c -> c.shortHand.equals(_shortHand))
                          .findFirst();
        }
    }
    enum CELLS { QTY, NAME, CA, MG, NA, SO4, CL, HCO3 }
}
