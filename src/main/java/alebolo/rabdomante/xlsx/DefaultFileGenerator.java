package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.core.SaltProfile;
import alebolo.rabdomante.core.SaltProfiles;
import alebolo.rabdomante.core.Water;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import static alebolo.rabdomante.xlsx.ResultWriter.close;

import static alebolo.rabdomante.xlsx.Constants.CELLS.*;
import static alebolo.rabdomante.xlsx.ResultWriter.getOrCreate;
import static alebolo.rabdomante.xlsx.ResultWriter.styledCell;

public class DefaultFileGenerator {
    public static final List<SaltProfile> DEFAULT_SALTS = Arrays.asList(
            SaltProfiles.TABLE_SALT,
            SaltProfiles.GYPSUM,
            SaltProfiles.EPSOM_SALT,
            SaltProfiles.BAKING_SODA,
            SaltProfiles.CALCIUM_CHLORIDE,
            SaltProfiles.CHALK,
            SaltProfiles.PICKLING_LIME,
            SaltProfiles.MAGNESIUM_CHLORIDE
    );
    public static final java.awt.Color COLOR_LIGHT_YELLOW = new java.awt.Color(255, 255, 102);
    public static final java.awt.Color COLOR_LIGHT_BLUE = new java.awt.Color(50, 150, 200);

    public void generate(File file) {
        try {
            if (file.exists()) { throw new RabdoException("File already exists"); }

            writeWaterSheet(file);
            writeSaltsSheet(file);
            writeTargetSheet(file);
            writeResultSheet(file);
            writeKnownWatersSheet(file);

            cleanup(file);

        } catch (Exception e) {
            throw new RabdoException(e);
        }
    }

    private void writeKnownWatersSheet(File file) {
        List<Water> waters = new KnownWatersProvider().knownWaters();

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            try (Workbook wb = WorkbookFactory.create(fis)) {
                Sheet sheet = wb.createSheet(Constants.SHEETS.KNOWN_WATERS.uiName);
                int rowNum = 0;
                Font font = wb.createFont();

                ResultWriter.commonHeader(getOrCreate(sheet, rowNum), font);
                rowNum++;

                for (Water w : waters) {
                    Row row = getOrCreate(sheet, rowNum++);
                    row.createCell(NAME.ordinal()).setCellValue(w.nome);
                    row.createCell(CA.ordinal()).setCellValue(w.ca);
                    row.createCell(MG.ordinal()).setCellValue(w.mg);
                    row.createCell(NA.ordinal()).setCellValue(w.na);
                    row.createCell(SO4.ordinal()).setCellValue(w.so4);
                    row.createCell(CL.ordinal()).setCellValue(w.cl);
                    row.createCell(HCO3.ordinal()).setCellValue(w.hco3);
                }

                wb.write(new FileOutputStream(file));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            if (fis != null) close(fis);
        }
    }

    private void cleanup(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            try (Workbook wb = WorkbookFactory.create(fis)) {
                wb.setActiveSheet(wb.getSheetIndex(Constants.SHEETS.WATER.uiName));
                Utils.autoSizeColumns(wb);
                wb.write(new FileOutputStream(file));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            if (fis != null) close(fis);
        }
    }

    private void writeResultSheet(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            try (Workbook wb = WorkbookFactory.create(fis)) {
                Sheet sheet = wb.createSheet(Constants.SHEETS.RESULT.uiName);
                colorTab(sheet, COLOR_LIGHT_BLUE);
                wb.write(new FileOutputStream(file));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            if (fis != null) close(fis);
        }
    }

    private void writeTargetSheet(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            try (Workbook wb = WorkbookFactory.create(fis)) {
                Sheet sheet = wb.createSheet(Constants.SHEETS.TARGET.uiName);
                colorTab(sheet, COLOR_LIGHT_YELLOW);

                Font font = wb.createFont();
                targetHeader(font, getOrCreate(sheet, 0));

                wb.write(new FileOutputStream(file));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            if (fis != null) close(fis);
        }
    }

    private void writeSaltsSheet(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            try (Workbook wb = WorkbookFactory.create(fis)) {
                CellStyle style = wb.createCellStyle();
                style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.index);
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                Sheet sheet = wb.createSheet(Constants.SHEETS.SALTS.uiName);
                colorTab(sheet, COLOR_LIGHT_YELLOW);

                int rowNum = 0;
                Font font = wb.createFont();

                saltsHeader(font, getOrCreate(sheet, rowNum));
                rowNum++;

                rowNum = salts(sheet, rowNum, style);

                wb.write(new FileOutputStream(file));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            if (fis != null) close(fis);
        }
    }

    private int salts(Sheet sheet, int rowNum, CellStyle userInputStyle) {
        for (SaltProfile w : DEFAULT_SALTS) {
            Row row = getOrCreate(sheet, rowNum++);
            Cell cell = row.createCell(QTY.ordinal());
            cell.setCellStyle(userInputStyle); cell.setCellValue(0);

            row.createCell(NAME.ordinal()).setCellValue(w.nome);
            row.createCell(CA.ordinal()).setCellValue(w.ca);
            row.createCell(MG.ordinal()).setCellValue(w.mg);
            row.createCell(NA.ordinal()).setCellValue(w.na);
            row.createCell(SO4.ordinal()).setCellValue(w.so4);
            row.createCell(CL.ordinal()).setCellValue(w.cl);
            row.createCell(HCO3.ordinal()).setCellValue(w.hco3);
        }
        return rowNum;
    }

    private void writeWaterSheet(File file) {
        try {
            try (Workbook wb = new XSSFWorkbook()) {
                Sheet sheet = wb.createSheet(Constants.SHEETS.WATER.uiName);
                colorTab(sheet, COLOR_LIGHT_YELLOW);

                int rowNum = 0;
                Font font = wb.createFont();

                waterHeader(font, getOrCreate(sheet, rowNum));
                rowNum++;

                rowNum = waters(sheet, rowNum);

                wb.write(new FileOutputStream(file));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
        }
    }

    private void colorTab(Sheet sheet, java.awt.Color color) {
        ((XSSFSheet) sheet).setTabColor(new XSSFColor(color));
    }

    private int waters(Sheet sheet, int rowNum) {
        // TODO valutare se inserire acque
        return rowNum;
    }

    private void saltsHeader(Font font, Row row) {
        ResultWriter.writeWatersHeader(row, font);
        styledCell(row, QTY.ordinal(), font).setCellValue("Grammi disponibili (g)");
    }

    private void targetHeader(Font font, Row row) {
        ResultWriter.writeWatersHeader(row, font);
        styledCell(row, QTY.ordinal(), font).setCellValue("Litri Necessari (L)");
    }

    private void waterHeader(Font font, Row row) {
        ResultWriter.writeWatersHeader(row, font);
        styledCell(row, QTY.ordinal(), font).setCellValue("Litri Disponibili (L)");
    }
}
