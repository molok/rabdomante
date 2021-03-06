package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.Msg;

import java.util.Arrays;
import java.util.Optional;

public class Constants {
    static public final int HEADER_ROWS = 1;
    public enum SHEETS {
        WATER("available_waters", "U1"),
        SALTS("salts", "U2"),
        TARGET("target", "U3"),
        RESULT("recipe", "R"),
        KNOWN_WATERS("bottled_waters", "D1"),
        COMMON_PROFILES("common_profiles", "D2");

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
