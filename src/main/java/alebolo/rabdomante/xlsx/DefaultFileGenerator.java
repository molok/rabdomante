package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.core.SaltProfile;
import alebolo.rabdomante.core.SaltProfiles;
import alebolo.rabdomante.core.Water;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import static alebolo.rabdomante.xlsx.Constants.CELLS.*;
import static alebolo.rabdomante.xlsx.Constants.SHEETS.*;
import static alebolo.rabdomante.xlsx.ResultWriter.getOrCreate;

public class DefaultFileGenerator {

    private final WaterProfileParser waterProfileParser = new WaterProfileParser();
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

    private XSSFCellStyle headerStyle;
    private XSSFCellStyle baseStyle;
    private XSSFCellStyle userInputStyle;

    public void generate(File file, boolean overwrite) {
        try {
            if (file.exists() && !overwrite) { throw new RabdoException(Msg.fileAlreadyExists()); }

            try (Workbook wb = new XSSFWorkbook()) {
                userInputStyle = userInputStyle(wb);
                baseStyle = baseStyle(wb);
                headerStyle = headerStyle(wb);

                writeWatersSheet(wb);
                writeSaltsSheet(wb);
                writeTargetSheet(wb);
                writeResultSheet(wb);
                writeParsedWaters(wb, Constants.SHEETS.KNOWN_WATERS, waterProfileParser.parse(this.getClass().getResourceAsStream("/waters.csv")));
                writeParsedWaters(wb, Constants.SHEETS.COMMON_PROFILES, waterProfileParser.parse(this.getClass().getResourceAsStream("/common_profiles.csv")));

                wb.setActiveSheet(Utils.sheetIndex(wb, WATER).orElseThrow(() -> new RabdoException("Sheet not found:"+WATER.localizedName())));

                cleanup(wb);

                wb.write(new FileOutputStream(file));

            } catch (Exception e) {
                throw new RabdoException(e);
            }

        } catch (Exception e) {
            throw new RabdoException(e);
        }
    }

    private void writeWatersSheet(Workbook wb) {
        Sheet sheet = wb.createSheet(WATER.localizedName());
        Utils.colorTab(sheet, Utils.COLOR_USER_INPUT);

        int rowNum = 0;

        Row headerRow = getOrCreate(sheet, rowNum);
        waterHeader(headerRow);
        rowNum++;

        for (; rowNum <= 10; rowNum++) {
            Row currRow = getOrCreate(sheet, rowNum);
            for (int c = 0; c < headerRow.getLastCellNum(); c++) {
                styledCell(currRow, userInputStyle, c);
            }
        }
    }

    private void writeSaltsSheet(Workbook wb) {
        Sheet sheet = wb.createSheet(Constants.SHEETS.SALTS.localizedName());
        Utils.colorTab(sheet, Utils.COLOR_USER_INPUT);

        int rowNum = 0;

        saltsHeader(getOrCreate(sheet, rowNum));
        rowNum++;

        rowNum = salts(sheet, rowNum);
    }

    private void writeParsedWaters(Workbook wb, Constants.SHEETS sheetName, List<Water> waters) {
        Sheet sheet = wb.createSheet(sheetName.localizedName());
        int rowNum = 0;

        ResultWriter.commonHeader(getOrCreate(sheet, rowNum), headerStyle);
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
    }

    private void cleanup(Workbook wb) {
        wb.setActiveSheet(Utils.sheetIndex(wb, WATER).getAsInt());
        Utils.autoSizeColumns(wb);
        Utils.orderSheets(wb);
    }

    private void writeResultSheet(Workbook wb) {
        Sheet sheet = wb.createSheet(RESULT.localizedName());
        Utils.colorTab(sheet, Utils.COLOR_OUTPUT);
    }

    private void writeTargetSheet(Workbook wb) {
        Sheet sheet = wb.createSheet(TARGET.localizedName());
        Utils.colorTab(sheet, Utils.COLOR_USER_INPUT);

        Row headerRow = getOrCreate(sheet, 0);
        targetHeader(headerRow);

        Row targetInputRow = getOrCreate(sheet, 1);
        for (int c = 0; c < headerRow.getLastCellNum(); c++) {
            styledCell(targetInputRow, userInputStyle, c);
        }
    }

    private int salts(Sheet sheet, int rowNum) {
        for (SaltProfile w : DEFAULT_SALTS) {
            Row row = getOrCreate(sheet, rowNum++);
            Cell cell = styledCell(row, userInputStyle, QTY.ordinal());
            cell.setCellValue(0);

            styledCell(row, baseStyle, NAME.ordinal()).setCellValue(w.name);
            styledCell(row, baseStyle, CA.ordinal()).setCellValue(w.ca);
            styledCell(row, baseStyle, MG.ordinal()).setCellValue(w.mg);
            styledCell(row, baseStyle, NA.ordinal()).setCellValue(w.na);
            styledCell(row, baseStyle, SO4.ordinal()).setCellValue(w.so4);
            styledCell(row, baseStyle, CL.ordinal()).setCellValue(w.cl);
            styledCell(row, baseStyle, HCO3.ordinal()).setCellValue(w.hco3);
        }
        return rowNum;
    }

    private XSSFCellStyle baseStyle(Workbook workbook) {
        XSSFCellStyle res = (XSSFCellStyle) workbook.createCellStyle();
        res.setBorderBottom(BorderStyle.THIN);
        res.setBorderTop(BorderStyle.THIN);
        res.setBorderLeft(BorderStyle.THIN);
        res.setBorderRight(BorderStyle.THIN);
        return res;
    }

    private Cell styledCell(Row row, XSSFCellStyle style, int ordinal) {
        Cell cell = row.createCell(ordinal);
        cell.setCellStyle(style);
        return cell;
    }

    private XSSFCellStyle userInputStyle(Workbook wb) {
        XSSFCellStyle res = (XSSFCellStyle) wb.createCellStyle();
        res.setFillForegroundColor(new XSSFColor(Utils.COLOR_USER_INPUT));
        res.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        res.setBorderBottom(BorderStyle.THIN);
        res.setBorderTop(BorderStyle.THIN);
        res.setBorderLeft(BorderStyle.THIN);
        res.setBorderRight(BorderStyle.THIN);
        return res;
    }

    private void saltsHeader(Row row) {
        ResultWriter.writeWatersHeader(row, headerStyle);
        styledCell(row, headerStyle, QTY.ordinal()).setCellValue(Msg.availableGrams() + " (g)");
    }

    private XSSFCellStyle headerStyle(Workbook workbook) { return baseStyle(workbook); }

    private void targetHeader(Row row) {
        ResultWriter.writeWatersHeader(row, headerStyle);
        styledCell(row, headerStyle, QTY.ordinal()).setCellValue(Msg.litersNeeded() + " (L)");
    }

    private void waterHeader(Row row) {
        ResultWriter.writeWatersHeader(row, headerStyle);
        styledCell(row, headerStyle, QTY.ordinal()).setCellValue(Msg.litersAvailable() + " (L)");
    }
}
