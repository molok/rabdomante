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

import static alebolo.rabdomante.xlsx.Constants.CELLS.*;
import static alebolo.rabdomante.xlsx.Constants.HEADER_ROWS;
import static alebolo.rabdomante.xlsx.Constants.SHEETS.RESULT;

public class ResultWriter implements IResultWriter {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final File output;
    private final File input;

    public ResultWriter(File file, File output) {
        this.input = file;
        this.output = output;
    }

    private static void close( Closeable a, Closeable b ) {
        try { if (a != null) a.close(); } catch (Exception e) {}
        try { if (b != null) b.close(); } catch (Exception e) {}
    }

    @Override
    public void write(Recipe recipe) {
        logger.info("inizio scrittura su {}", input);

        try {
            try (Workbook wb = WorkbookFactory.create(new FileInputStream(input))) {
                Sheet sheet = wb.getSheetAt(RESULT.ordinal());
                removePreviousRecipe(sheet);

                writeRecipe(sheet, recipe);

                wb.write(new FileOutputStream(output));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        }

        logger.info("fine scrittura");
    }

    private void writeRecipe(Sheet sheet, Recipe recipe) {
        int rowNum = HEADER_ROWS + 1;
        for (Water w : recipe.waters) {
            writeWater(sheet, rowNum, w);
            rowNum++;
        }

        for (Salt w : recipe.salts) {
            writeSalt(sheet, rowNum, w);
            rowNum++;
        }

        writeTotal(sheet, recipe, rowNum);
    }

    private void writeTotal(Sheet sheet, Recipe recipe, int rowNum) {
        Row row = getOrCreate(sheet, rowNum);
        row.createCell(QTY.ordinal()).setCellValue(recipe.liters());
        row.createCell(NAME.ordinal()).setCellValue("Total");
        row.createCell(CA.ordinal()).setCellValue(recipe.ca());
        row.createCell(MG.ordinal()).setCellValue(recipe.mg());
        row.createCell(NA.ordinal()).setCellValue(recipe.na());
        row.createCell(SO4.ordinal()).setCellValue(recipe.so4());
        row.createCell(CL.ordinal()).setCellValue(recipe.cl());
        row.createCell(HCO3.ordinal()).setCellValue(recipe.hco3());
    }

    private void writeSalt(Sheet sheet, int rowNum, Salt w) {
        Row row = getOrCreate(sheet, rowNum);
        row.createCell(QTY.ordinal()).setCellValue(w.dg / 10);
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

    private Row getOrCreate(Sheet sheet, int rowNum) {
        Row res = sheet.getRow(rowNum);
        if (res == null) {
            res = sheet.createRow(rowNum);
        }
        return res;
    }

    private void removePreviousRecipe(Sheet sheet) {
        for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row.getRowNum() <= HEADER_ROWS) { continue; }
            sheet.removeRow(row);
        }
    }
}
