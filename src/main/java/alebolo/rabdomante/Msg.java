package alebolo.rabdomante;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Msg {
    public static final String BUNDLE_NAME = "messages";
    static ResourceBundle m = ResourceBundle.getBundle(BUNDLE_NAME);
    public static void changeLocale(Locale locale) {
        m = ResourceBundle.getBundle(
                BUNDLE_NAME,
                locale,
                new ResourceBundle.Control() {
                    public Locale getFallbackLocale(String baseName, Locale locale) {
                        return Locale.ENGLISH;
                    }
                });
    }

    public static String getString(String key) { return m.getString(key); }
    public static String calcium() { return m.getString("calcium"); }
    public static String magnesium() { return m.getString("magnesium"); }
    public static String sodium() { return m.getString("sodium"); }
    public static String sulfate() { return m.getString("sulfate"); }
    public static String chloride() { return m.getString("chloride"); }
    public static String bicarbonates() { return m.getString("bicarbonates"); }
    public static String total() { return m.getString("total"); }
    public static String targetProfileNotConfigured() { return m.getString("target_profile_not_configured"); }
    public static String tooManyTargetProfiles() { return m.getString("too_many_target_profiles"); }
    public static String wrongFileNoWaterProvided() { return m.getString("wrong_file_no_water_provided"); }
    public static String gypsum() { return m.getString("gypsum"); }
    public static String tableSalt() { return m.getString("table_salt"); }
    public static String epsomSalt() { return m.getString("espom_salt"); }
    public static String calciumChloride() { return m.getString("calcium_chloride"); }
    public static String bakingSoda() { return m.getString("baking_soda"); }
    public static String chalk() { return m.getString("chalk"); }
    public static String picklingLime() { return m.getString("pickling_lime"); }
    public static String magnesiumChloride() { return m.getString("magnesium_chloride"); }
    public static String mineralValueCanTBeLt1Was() { return m.getString("mineral_value_can_t_be_lt_1_was"); }
    public static String fileAlreadyExists() { return m.getString("file_already_exists"); }
    public static String availableWaters() { return m.getString("available_waters"); }
    public static String salts() { return m.getString("salts"); }
    public static String target() { return m.getString("target"); }
    public static String recipe() {return m.getString("recipe"); }
    public static String bottledWaters() { return m.getString("bottled_waters"); }
    public static String commonProfiles() { return m.getString("common_profiles"); }
    public static String locale() { return m.getString("locale"); }
    public static String templateGenerated() { return m.getString("template_generated"); }
    public static String fileNotFoundTemplateGenerated() { return m.getString("file_not_found_template_generated"); }
    public static String noSolutionFound() { return m.getString("no_solution_found"); }
    public static String optimalSolutionFoudn() { return m.getString("optimal_solution_found"); }
    public static String searchInterrupted() { return m.getString("incomplete_search"); }
    public static String printsThisMessage() { return m.getString("prints_this_message"); }
    public static String verboseDescription() { return m.getString("shows_details_of_the_computation"); }
    public static String inputFile() { return m.getString("input_file"); }
    public static String outputFile() { return m.getString("output_file"); }
    public static String timeLimitDescription() { return m.getString("timeout_desc"); }
    public static String executionTime() { return m.getString("execution_time"); }
    public static String solutionNotFound() { return m.getString("solution_not_found"); }
    public static String noGui() { return m.getString("no_gui"); }
    public static String getTheTemplate() { return m.getString("get_the_template"); }
    public static String saveItSomewhere() { return m.getString("save_it_somewhere"); }
    public static String welcome() { return m.getString("welcome");}
    public static String editCells() { return m.getString("edit_cells"); }
    public static String saveAndCloseInput() { return m.getString("save_and_close_input"); }
    public static String selectOrDrag() { return m.getString("select_or_drag"); }
    public static String selectFile() { return m.getString("select_file"); }
    public static String runTitle() { return m.getString("run_title"); }
    public static String timeLimitSeconds() { return m.getString("time_limit_seconds"); }
    public static String run() { return m.getString("run"); }
    public static String error() { return m.getString("error"); }
    public static String unexpectedError() { return m.getString("unex_error"); }
    public static String solutionFound() { return m.getString("solution_found"); }
    public static String success() { return m.getString("success"); }
    public static String warning() { return m.getString("warning"); }
    public static String solutionIncomplete() {
        return m.getString("solution_incomplete");
    }
    public static String noSolutionFoundTime() { return m.getString("no_solution_found_time"); }
    public static String menuFile() { return m.getString("menu_file"); }
    public static String menuExit() { return m.getString("menu_exit"); }
    public static String menuHelp() { return m.getString("menu_help"); }
    public static String menuAbout() { return m.getString("menu_about"); }
    public static String delta() { return m.getString("recipe_delta"); }

    public static String openSpreadsheet() { return m.getString("open_spreadsheet"); }
}
