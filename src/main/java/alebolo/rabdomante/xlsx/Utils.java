package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.xlsx.Constants.SHEETS;
import com.google.common.collect.Streams;
import org.apache.commons.text.WordUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.Closeable;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

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
        Arrays.stream(SHEETS.values())
              .forEach(
                  s -> wb.setSheetOrder(
                          wb.getSheetName( sheetIndex(wb, s)
                                           .orElseThrow(() -> new RabdoException("Sheet not found:"+s.localizedName()))),
                          s.ordinal()));
    }

    public static OptionalInt sheetIndex(Workbook wb, SHEETS what) {
        return IntStream.range(0, wb.getNumberOfSheets())
                        .filter(i -> wb.getSheetAt(i).getSheetName().startsWith(what.shortHand))
                        .findFirst();
    }

    public static Optional<Sheet> searchSheet(Workbook wb, SHEETS what) {
        return Streams.stream(wb.sheetIterator())
               .filter(s -> s.getSheetName().startsWith(what.shortHand))
               .findFirst();
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

    public static void close(Closeable... toClose) {
        for (Closeable c : toClose) {
            try {
                if (c != null) { c.close(); }
            } catch (Throwable t) {
                // ignoro, non mi interessa
            }
        }
    }

    public static boolean fileLocked(File file) {
        try {
            return !file.renameTo(file);
        } catch (Exception e) {
            return true;
        }
    }
}
