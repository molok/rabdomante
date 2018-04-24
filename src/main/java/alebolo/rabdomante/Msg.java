package alebolo.rabdomante;

import java.util.ResourceBundle;

public class Msg {
    static ResourceBundle local;
    static {
        local = ResourceBundle.getBundle("messages");
    }

    public static String calcium() { return local.getString("CALCIUM"); }
    public static String magnesium() { return local.getString("MAGNESIUM"); }
    public static String sodium() { return local.getString("SODIUM"); }
    public static String sulfate() { return local.getString("SULFATE"); }
    public static String chloride() { return local.getString("CHLORIDE"); }
    public static String bicarbonates() { return local.getString("BICARBONATES"); }
    public static String total() { return local.getString("TOTAL"); }
    public static String targetProfileNotConfigured() { return local.getString("TARGET_PROFILE_NOT_CONFIGURED"); }
    public static String tooManyTargetProfiles() { return local.getString("TOO_MANY_TARGET_PROFILES"); }
    public static String wrongFileNoWaterProvided() { return local.getString("WRONG_FILE_NO_WATER_PROVIDED"); }
    public static String gypsum() { return local.getString("GYPSUM"); }
    public static String tableSalt() { return local.getString("TABLE_SALT"); }
    public static String epsomSalt() { return local.getString("ESPOM_SALT"); }
    public static String calciumChloride() { return local.getString("CALCIUM_CHLORIDE"); }
    public static String bakingSoda() { return local.getString("BAKING_SODA"); }
    public static String chalk() { return local.getString("CHALK"); }
    public static String picklingLime() { return local.getString("PICKLING_LIME"); }
    public static String magnesiumChloride() { return local.getString("MAGNESIUM_CHLORIDE"); }
    public static String mineralValueCanTBeLt1Was() { return local.getString("MINERAL_VALUE_CAN_T_BE_LT_1_WAS"); }
    public static String fileAlreadyExists() { return local.getString("FILE_ALREADY_EXISTS"); }
    public static String availableWaters() { return local.getString("AVAILABLE_WATERS"); }
    public static String salts() { return local.getString("SALTS"); }
    public static String target() { return local.getString("TARGET"); }
    public static String recipe() {return local.getString("RECIPE"); }
    public static String bottledWaters() { return local.getString("BOTTLED_WATERS"); }
    public static String commonProfiles() { return local.getString("COMMON_PROFILES"); }
    public static String language() { return local.getString("LANGUAGE"); }
    public static String templateGenerated() { return local.getString("TEMPLATE_GENERATED"); }
    public static String fileNotFoundTemplateGenerated() { return local.getString("FILE_NOT_FOUND_TEMPLATE_GENERATED"); }
    public static String noSolutionFound() { return local.getString("NO_SOLUTION_FOUND"); }
    public static String optimalSolutionFoudn() { return local.getString("OPTIMAL_SOLUTION_FOUND"); }
    public static String searchInterrupted() { return local.getString("INCOMPLETE_SEARCH"); }
    public static String printsThisMessage() { return local.getString("PRINTS_THIS_MESSAGE"); }
    public static String verboseDescription() { return local.getString("SHOWS_DETAILS_OF_THE_COMPUTATION"); }
    public static String inputFile() { return local.getString("INPUT_FILE"); }
    public static String outputFile() { return local.getString("OUTPUT_FILE"); }
    public static String timeoutDescription() { return local.getString("TIMEOUT_DESC"); }
    public static String executionTime() { return local.getString("EXECUTION_TIME"); }
    public static String solutionNotFound() { return local.getString("SOLUTION_NOT_FOUND"); }
}
