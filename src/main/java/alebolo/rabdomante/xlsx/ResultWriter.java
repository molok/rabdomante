package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.cli.IResultWriter;
import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.core.Recipe;
import alebolo.rabdomante.core.Salt;
import alebolo.rabdomante.core.Water;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDate;
import java.util.Iterator;

import static alebolo.rabdomante.xlsx.Constants.CELLS.*;
import static alebolo.rabdomante.xlsx.Constants.SHEETS.RESULT;

public class ResultWriter implements IResultWriter {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final File output;
    private final File input;

    public ResultWriter(File file, File output) {
        this.input = file;
        this.output = output;
    }

    @Override
    public void write(Recipe recipe) {
        logger.info("inizio scrittura su {}", input);

        FileInputStream fis = null;

        try {
            fis = new FileInputStream(input);
            try (Workbook wb = WorkbookFactory.create(fis)) {
                wb.removeSheetAt(wb.getSheetIndex(RESULT.uiName));
                Sheet sheet = wb.createSheet(RESULT.uiName);
                Utils.colorTab(sheet, Utils.COLOR_LIGHT_BLUE);

                Font defaultFont = wb.createFont();

                int rowNum = 0;
                rowNum = writeWaters(recipe, sheet, defaultFont, rowNum);
                rowNum = spacer(sheet, rowNum);
                rowNum = writeSalts(recipe, sheet, defaultFont, rowNum);
                rowNum = spacer(sheet, rowNum);
                rowNum = writeTotals(recipe, sheet, defaultFont, rowNum);
                rowNum = spacer(sheet, rowNum);
                rowNum = timestamp(sheet, rowNum);

                Utils.autoSizeColumns(wb);

                wb.write(new FileOutputStream(output));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            if (fis != null) close(fis);
        }

        logger.info("fine scrittura");
    }

    private int timestamp(Sheet sheet, int rowNum) {
        getOrCreate(sheet, rowNum++).createCell(0).setCellValue("Aggiornato @" + LocalDate.now().toString());
        return rowNum;
    }

    private int writeTotals(Recipe recipe, Sheet sheet, Font defaultFont, int rowNum) {
        writeWatersHeader(getOrCreate(sheet, rowNum), defaultFont);
        rowNum++;
        writeTotal(sheet, recipe, rowNum);
        return rowNum;
    }

    private int writeSalts(Recipe recipe, Sheet sheet, Font defaultFont, int rowNum) {
        writeSaltHeader(getOrCreate(sheet, rowNum), defaultFont);
        rowNum++;
        for (Salt w : recipe.salts) {
            writeSalt(sheet, rowNum, w);
            rowNum++;
        }
        return rowNum;
    }

    private int spacer(Sheet sheet, int rowNum) {
        getOrCreate(sheet, rowNum); //spacer
        rowNum++;
        return rowNum;
    }

    private int writeWaters(Recipe recipe, Sheet sheet, Font defaultFont, int rowNum) {
        writeWatersHeader(getOrCreate(sheet, rowNum), defaultFont);
        rowNum++;
        for (Water w : recipe.waters) {
            writeWater(sheet, rowNum, w);
            rowNum++;
        }
        return rowNum;
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

    private static void writeSaltHeader(Row row, Font font) {
        styledCell(row, QTY.ordinal(), font).setCellValue("Grammi (g)");
        commonHeader(row, font);
    }

    public static void writeWatersHeader(Row row, Font font) {
        styledCell(row, QTY.ordinal(), font).setCellValue("Litri (L)");
        commonHeader(row, font);
    }

    public static void commonHeader(Row row, Font font) {
        styledCell(row, NAME.ordinal(), font).setCellValue("Nome");
        styledCell(row, CA.ordinal(), font).setCellValue("Calcio (Ca)");
        styledCell(row, MG.ordinal(), font).setCellValue("Magnesio (Mg)");
        styledCell(row, NA.ordinal(), font).setCellValue("Sodio (Na)");
        styledCell(row, SO4.ordinal(), font).setCellValue("Solfati (SO4)");
        styledCell(row, CL.ordinal(), font).setCellValue("Cloruri (Cl)");
        styledCell(row, HCO3.ordinal(), font).setCellValue("Bicarbonati (HCO3)");
    }

    public static Cell styledCell(Row row, int pos, Font font) {
        Cell cell = row.createCell(pos);
        cell.getCellStyle().setFont(font);
        return cell;
    }

    private void writeTotal(Sheet sheet, Recipe recipe, int rowNum) {
        Row row = getOrCreate(sheet, rowNum);
        row.createCell(QTY.ordinal()).setCellValue(recipe.liters());
        row.createCell(NAME.ordinal()).setCellValue("Totale");
        row.createCell(CA.ordinal()).setCellValue(recipe.ca());
        row.createCell(MG.ordinal()).setCellValue(recipe.mg());
        row.createCell(NA.ordinal()).setCellValue(recipe.na());
        row.createCell(SO4.ordinal()).setCellValue(recipe.so4());
        row.createCell(CL.ordinal()).setCellValue(recipe.cl());
        row.createCell(HCO3.ordinal()).setCellValue(recipe.hco3());
    }

    private void writeSalt(Sheet sheet, int rowNum, Salt w) {
        Row row = getOrCreate(sheet, rowNum);
        row.createCell(QTY.ordinal()).setCellValue(w.dg / 10.);
        row.createCell(NAME.ordinal()).setCellValue(w.nome);
        row.createCell(CA.ordinal()).setCellValue(w.ca);
        row.createCell(MG.ordinal()).setCellValue(w.mg);
        row.createCell(NA.ordinal()).setCellValue(w.na);
        row.createCell(SO4.ordinal()).setCellValue(w.so4);
        row.createCell(CL.ordinal()).setCellValue(w.cl);
        row.createCell(HCO3.ordinal()).setCellValue(w.hco3);
    }

    private void writeWater(Sheet sheet, int rowNum, Water w) {
        Row row = getOrCreate(sheet, rowNum);
        row.createCell(QTY.ordinal()).setCellValue(w.liters);
        row.createCell(NAME.ordinal()).setCellValue(w.nome);
        row.createCell(CA.ordinal()).setCellValue(w.ca);
        row.createCell(MG.ordinal()).setCellValue(w.mg);
        row.createCell(NA.ordinal()).setCellValue(w.na);
        row.createCell(SO4.ordinal()).setCellValue(w.so4);
        row.createCell(CL.ordinal()).setCellValue(w.cl);
        row.createCell(HCO3.ordinal()).setCellValue(w.hco3);
    }

    public static Row getOrCreate(Sheet sheet, int rowNum) {
        Row res = sheet.getRow(rowNum);
        if (res == null) {
            res = sheet.createRow(rowNum);
        }
        return res;
    }

    private void removePreviousRecipe(Sheet sheet) {
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            Iterator<Row> rowIte =  sheet.iterator();
            while(rowIte.hasNext()){
                rowIte.next();
                rowIte.remove();
            }
        }
    }
}
