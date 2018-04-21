package alebolo.rabdomante.core;

public class SaltProfiles {
    static final SaltProfile GYPSUM = new SaltProfile.Builder("gypsum").ca(23).so4(56).build();
    static final SaltProfile TABLE_SALT = new SaltProfile.Builder("table salt").na(39).cl(61).build();
    static final SaltProfile EPSOM_SALT = new SaltProfile.Builder("epsom salt").mg(10).so4(39).build();
    static final SaltProfile CALCIUM_CHLORIDE = new SaltProfile.Builder("calcium chloride").ca(36).cl(64).build();
    static final SaltProfile BAKING_SODA = new SaltProfile.Builder("baking soda").na(27).hco3(72).build();
    static final SaltProfile CHALK = new SaltProfile.Builder("chalk").ca(40).hco3(122).build();
    static final SaltProfile PICKLING_LIME = new SaltProfile.Builder("pickling lime").ca(54).hco3(164).build();
    static final SaltProfile MAGNESIUM_CHLORIDE = new SaltProfile.Builder("magnesium chloride").mg(12).cl(35).build();
}
