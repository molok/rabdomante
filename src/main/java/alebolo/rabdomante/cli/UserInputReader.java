package alebolo.rabdomante.cli;

import alebolo.rabdomante.core.Salt;
import alebolo.rabdomante.core.SaltProfile;
import alebolo.rabdomante.core.Water;
import alebolo.rabdomante.core.WaterProfile;
import com.google.common.collect.Streams;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static alebolo.rabdomante.cli.UserInputReader.CELLS.*;

public class UserInputReader implements IUserInputReader {
    private static final int HEADER_ROWS = 1;
    private final File file;

    enum CELLS { QTY, NAME, CA, MG, NA, SO4, CL, HCO3 }
    enum SHEETS { WATER, SALTS, TARGET }

    public UserInputReader(File file) {
        this.file = file;
    }

    @Override public List<Water> waters() {
        try (Workbook wb = workbook()) {
            return Streams.stream(wb.getSheetAt(SHEETS.WATER.ordinal()).rowIterator())
                    .skip(HEADER_ROWS)
                    .filter( row -> {
                        String name = row.getCell(NAME.ordinal()).getStringCellValue();
                        return name != null && !name.isEmpty();
                    } )
                    .map( row -> new Water(
                            new WaterProfile(
                                    row.getCell(NAME.ordinal()).getStringCellValue(),
                                    toInt(row.getCell(CA.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(MG.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(NA.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(SO4.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(CL.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(HCO3.ordinal()).getNumericCellValue())),
                            toInt(row.getCell(QTY.ordinal()).getNumericCellValue())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RabdoInputException(e);
        }
    }

    @Override public List<Salt> salts() {
        try (Workbook wb = workbook()) {
            return Streams.stream(wb.getSheetAt(SHEETS.SALTS.ordinal()).rowIterator())
                    .skip(HEADER_ROWS)
                    .filter( row -> {
                        String name = row.getCell(NAME.ordinal()).getStringCellValue();
                        return name != null && !name.isEmpty();
                    } )
                    .map( row -> new Salt(
                            new SaltProfile(
                                    row.getCell(NAME.ordinal()).getStringCellValue(),
                                    toInt(row.getCell(CA.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(MG.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(NA.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(SO4.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(CL.ordinal()).getNumericCellValue()),
                                    toInt(row.getCell(HCO3.ordinal()).getNumericCellValue())),
                            toInt(row.getCell(QTY.ordinal()).getNumericCellValue())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RabdoInputException(e);
        }
    }

    private int toInt(double numericCellValue) {
        return (int) Math.round(numericCellValue);
    }

    private Workbook workbook() throws IOException, InvalidFormatException {
        return WorkbookFactory.create(file);
    }
}
