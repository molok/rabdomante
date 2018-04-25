package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.IResultWriter;
import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.core.Recipe;
import alebolo.rabdomante.core.Salt;
import alebolo.rabdomante.core.WSolution;
import alebolo.rabdomante.core.Water;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static alebolo.rabdomante.xlsx.Constants.CELLS.*;
import static alebolo.rabdomante.xlsx.Constants.SHEETS.RESULT;

public class ResultWriter implements IResultWriter {
    public static final String AGGIORNATO = "Aggiornato @";
    public static final String RICERCA_COMPLETATA_CON_SUCCESSO_IN = "Ricerca completata con successo in ";
    public static final String IL_RISULTATO_POTREBBE_NON_ESSERE_OTTIMALE_LA_RICERCA_È_STATA_INTERROTTA_DOPO = "Il risultato potrebbe non essere ottimale, la ricerca è stata interrotta dopo ";
    public static final String GRAMMI_G = "Grammi (g)";
    public static final String LITRI_L = "Litri (L)";
    public static final String NOME = "Nome";
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private final File output;
    private final File input;

    public ResultWriter(File file, File output) {
        this.input = file;
        this.output = output;
    }

    private XSSFCellStyle baseStyle(Workbook workbook) {
        XSSFCellStyle res = (XSSFCellStyle) workbook.createCellStyle();
        res.setBorderBottom(BorderStyle.THIN);
        res.setBorderTop(BorderStyle.THIN);
        res.setBorderLeft(BorderStyle.THIN);
        res.setBorderRight(BorderStyle.THIN);
        return res;
    }

    private XSSFCellStyle headerStyle(Workbook workbook) {
        XSSFCellStyle res = (XSSFCellStyle) workbook.createCellStyle();
        res.setBorderBottom(BorderStyle.THIN);
        res.setBorderTop(BorderStyle.THIN);
        res.setBorderLeft(BorderStyle.THIN);
        res.setBorderRight(BorderStyle.THIN);
        return res;
    }

    private XSSFCellStyle baseStyle;
    private XSSFCellStyle headerStyle;

    @Override
    public void write(WSolution solution, long secondsElapsed) {
        logger.info("inizio scrittura su {}", input);

        FileInputStream fis = null;

        try {
            Recipe recipe = solution.recipe;
            fis = new FileInputStream(input);
            try (Workbook wb = WorkbookFactory.create(fis)) {
                baseStyle = baseStyle(wb);
                headerStyle = headerStyle(wb);
                wb.removeSheetAt(Utils.sheetIndex(wb, RESULT).getAsInt());
                Sheet sheet = wb.createSheet(RESULT.localizedName());
                Utils.colorTab(sheet, Utils.COLOR_OUTPUT);

                int rowNum = 0;
                rowNum = writeWaters(recipe, sheet, rowNum);
                rowNum = spacer(sheet, rowNum);
                rowNum = writeSalts(recipe, sheet, rowNum);
                rowNum = spacer(sheet, rowNum);
                rowNum = writeTotals(recipe, sheet, rowNum);
                rowNum = spacer(sheet, rowNum);
                rowNum = spacer(sheet, rowNum);
                rowNum = timestamp(sheet, rowNum, solution.searchCompleted, secondsElapsed);

                Utils.autoSizeColumns(wb);
                Utils.orderSheets(wb);

                wb.write(new FileOutputStream(output));
            }
        } catch (Exception e) {
            throw new RabdoException(e);
        } finally {
            if (fis != null) close(fis);
        }

        logger.info("fine scrittura");
    }

    private int timestamp(Sheet sheet, int rowNum, boolean searchCompleted, long secondsElapsed) {
        getOrCreate(sheet, rowNum++).createCell(0).setCellValue(AGGIORNATO + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        String msg = searchCompleted ? RICERCA_COMPLETATA_CON_SUCCESSO_IN + secondsElapsed + "s"
                                     : IL_RISULTATO_POTREBBE_NON_ESSERE_OTTIMALE_LA_RICERCA_È_STATA_INTERROTTA_DOPO + secondsElapsed + "s";
        getOrCreate(sheet, rowNum++).createCell(0).setCellValue(msg);
        return rowNum;
    }

    private int writeTotals(Recipe recipe, Sheet sheet, int rowNum) {
        writeWatersHeader(getOrCreate(sheet, rowNum), headerStyle);
        rowNum++;
        writeTotal(sheet, recipe, rowNum);
        return rowNum;
    }

    private int writeSalts(Recipe recipe, Sheet sheet, int rowNum) {
        writeSaltHeader(getOrCreate(sheet, rowNum));
        rowNum++;
        for (Salt w : recipe.salts) {
            writeSalt(sheet, rowNum, w);
            rowNum++;
        }
        return rowNum;
    }

    private int spacer(Sheet sheet, int rowNum) {
        getOrCreate(sheet, rowNum++); //spacer
        return rowNum;
    }

    private int writeWaters(Recipe recipe, Sheet sheet, int rowNum) {
        writeWatersHeader(getOrCreate(sheet, rowNum), headerStyle);
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

    private void writeSaltHeader(Row row) {
        styledCell(row, QTY.ordinal(), headerStyle).setCellValue(GRAMMI_G);
        commonHeader(row, headerStyle);
    }

    public static void writeWatersHeader(Row row, XSSFCellStyle style) {
        styledCell(row, QTY.ordinal(), style).setCellValue(LITRI_L);
        commonHeader(row, style);
    }

    public static void commonHeader(Row row, XSSFCellStyle style) {
        styledCell(row, NAME.ordinal(), style).setCellValue(NOME);
        styledCell(row, CA.ordinal(), style).setCellValue(Msg.calcium());
        styledCell(row, MG.ordinal(), style).setCellValue(Msg.magnesium());
        styledCell(row, NA.ordinal(), style).setCellValue(Msg.sodium());
        styledCell(row, SO4.ordinal(), style).setCellValue(Msg.sulfate());
        styledCell(row, CL.ordinal(), style).setCellValue(Msg.chloride());
        styledCell(row, HCO3.ordinal(), style).setCellValue(Msg.bicarbonates());
    }

    public static Cell styledCell(Row row, int pos, XSSFCellStyle style) {
        Cell cell = row.createCell(pos);
        cell.setCellStyle(style);
        return cell;
    }

    private void writeTotal(Sheet sheet, Recipe recipe, int rowNum) {
        Row row = getOrCreate(sheet, rowNum);
        row.createCell(QTY.ordinal()).setCellValue(recipe.liters());
        row.createCell(NAME.ordinal()).setCellValue(Msg.total());
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
}
