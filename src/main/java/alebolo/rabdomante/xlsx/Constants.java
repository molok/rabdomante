package alebolo.rabdomante.xlsx;

public class Constants {
    static public final int HEADER_ROWS = 1;
    enum SHEETS { WATER("Acque Disponibili"), SALTS("Sali"), TARGET("Obiettivo"), RESULT("Ricetta"), KNOWN_WATERS("Acque Commerciali"), COMMON_PROFILES("Profili comuni");

        public final String uiName;
        SHEETS(String name) {
            this.uiName = name;
        }
    }
    enum CELLS { QTY, NAME, CA, MG, NA, SO4, CL, HCO3 }
}
