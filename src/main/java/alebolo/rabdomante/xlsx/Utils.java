package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.xlsx.Constants.SHEETS;
import com.google.common.collect.Streams;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.text.WordUtils;
import org.apache.poi.openxml4j.opc.StreamHelper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

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
}
