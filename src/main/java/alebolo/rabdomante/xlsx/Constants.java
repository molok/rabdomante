package alebolo.rabdomante.xlsx;

public class Constants {
    static public final int HEADER_ROWS = 1;
    enum SHEETS { KNOWN_WATERS("Acque Commerciali"), WATER("Acque Disponibili"), SALTS("Sali"), TARGET("Obiettivo"), RESULT("Ricetta");

        public final String uiName;
        SHEETS(String name) {
            this.uiName = name;
        }
    }
    enum CELLS { QTY, NAME, CA, MG, NA, SO4, CL, HCO3 }
}
