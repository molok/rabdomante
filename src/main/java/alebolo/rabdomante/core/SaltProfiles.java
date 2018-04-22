package alebolo.rabdomante.core;

public class SaltProfiles {
    public static final SaltProfile GYPSUM = new SaltProfile.Builder("Gypsum").ca(23).so4(56).build();
    public static final SaltProfile TABLE_SALT = new SaltProfile.Builder("Sale").na(39).cl(61).build();
    public static final SaltProfile EPSOM_SALT = new SaltProfile.Builder("Sali di epsom").mg(10).so4(39).build();
    public static final SaltProfile CALCIUM_CHLORIDE = new SaltProfile.Builder("Cloruro di calcio").ca(36).cl(64).build();
    public static final SaltProfile BAKING_SODA = new SaltProfile.Builder("Bicarbonato di sodio").na(27).hco3(72).build();
    public static final SaltProfile CHALK = new SaltProfile.Builder("Chalk").ca(40).hco3(122).build();
    public static final SaltProfile PICKLING_LIME = new SaltProfile.Builder("Idrossido di calcio").ca(54).hco3(164).build();
    public static final SaltProfile MAGNESIUM_CHLORIDE = new SaltProfile.Builder("Cloruro di magnesio").mg(12).cl(35).build();
}
