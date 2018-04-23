package alebolo.rabdomante.xlsx;

import org.apache.commons.text.WordUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Iterator;

public class Utils {
    public static final java.awt.Color COLOR_USER_INPUT = new java.awt.Color(223, 255, 255);
    public static final java.awt.Color COLOR_OUTPUT = new java.awt.Color(255, 255, 153);

    static String capitalize(String s) {
        return WordUtils.capitalizeFully(s, ' ', '\'');
    }
    static int toInt(double numericCellValue) {
        return (int) Math.round(numericCellValue);
    }

    public static void orderSheets(Workbook wb) {
        for (Constants.SHEETS s: Constants.SHEETS.values()) {
            wb.setSheetOrder(s.uiName, s.ordinal());
        }
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
