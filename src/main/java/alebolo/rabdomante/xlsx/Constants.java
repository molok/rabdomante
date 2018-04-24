package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.Msg;

public class Constants {
    static public final int HEADER_ROWS = 1;
    //public enum SHEETS { WATER(Msg.availableWaters()), SALTS(Msg.salts()), TARGET(Msg.target()), RESULT(Msg.recipe()), KNOWN_WATERS(Msg.bottledWaters()), COMMON_PROFILES(Msg.commonProfiles());
    public enum SHEETS { WATER("Available Waters"), SALTS("Salts"), TARGET("Target"), RESULT("Recipe"), KNOWN_WATERS("Bottled Waters"), COMMON_PROFILES("Common Profiles");

        public final String uiName;
        SHEETS(String name) {
            this.uiName = name;
        }
    }
    enum CELLS { QTY, NAME, CA, MG, NA, SO4, CL, HCO3 }
}
