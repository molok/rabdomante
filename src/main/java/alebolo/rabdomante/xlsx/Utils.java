package alebolo.rabdomante.xlsx;

import org.apache.commons.text.WordUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Iterator;

public class Utils {
    public static final java.awt.Color COLOR_LIGHT_YELLOW = new java.awt.Color(255, 255, 102);
    public static final java.awt.Color COLOR_LIGHT_BLUE = new java.awt.Color(50, 150, 200);

    static String capitalize(String s) {
        return WordUtils.capitalizeFully(s, ' ', '\'');
    }
    static int toInt(double numericCellValue) {
        return (int) Math.round(numericCellValue);
    }

    public static void autoSizeColumns(Workbook workbook) {
        int numberOfSheets = workbook.getNumberOfSheets();
        for (int i = 0; i < numberOfSheets; i++) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet.getPhysicalNumberOfRows() > 0) {
                Row row = sheet.getRow(0);
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    int columnIndex = cell.getColumnIndex();
                    sheet.autoSizeColumn(columnIndex);
                }
            }
        }
    }

    static void colorTab(Sheet sheet, java.awt.Color color) {
        ((XSSFSheet) sheet).setTabColor(new XSSFColor(color));
    }
}
