package alebolo.rabdomante.xlsx;

import alebolo.rabdomante.Msg;
import alebolo.rabdomante.cli.IUserInputReader;
import alebolo.rabdomante.cli.RabdoException;
import alebolo.rabdomante.cli.RabdoInputException;
import alebolo.rabdomante.core.Salt;
import alebolo.rabdomante.core.SaltProfile;
import alebolo.rabdomante.core.Water;
import alebolo.rabdomante.core.WaterProfile;
import com.google.common.collect.Streams;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import static alebolo.rabdomante.xlsx.Constants.CELLS.*;
import static alebolo.rabdomante.xlsx.Constants.SHEETS.SALTS;

public class UserInputReader implements IUserInputReader {
    private final File file;

    public UserInputReader(File file) { this.file = file; }

    @Override public List<Water> waters() {
        return readWaters(Constants.SHEETS.WATER);
    }

    @Override public Water target() {
        List<Water> res = readWaters(Constants.SHEETS.TARGET);
        if (res.size() == 0) {
            throw new RabdoInputException(Msg.targetProfileNotConfigured());
        } else if (res.size() > 1) {
            throw new RabdoInputException(Msg.tooManyTargetProfiles() + res.size());
        } else {
            return res.get(0);
        }
    }

    List<Water> readWaters(Constants.SHEETS sheet) {
        try (Workbook wb = WorkbookFactory.create(file)) {
            List<Water> res = Streams.stream(Utils.searchSheet(wb, sheet)
                                                  .orElseThrow(() -> new RabdoException("Sheet not found:"+sheet.localizedName()))
                                                  .rowIterator())
                    .skip(Constants.HEADER_ROWS)
                    .filter(row -> {
                        Cell cell = row.getCell(NAME.ordinal());
                        if (cell == null) return false;
                        String name = cell.getStringCellValue();
                        return name != null && !name.isEmpty();
                    })
                    .filter(row -> Utils.toInt(row.getCell(QTY.ordinal()).getNumericCellValue()) > 0)
                    .map(row -> new Water(
                            new WaterProfile(
                                    row.getCell(NAME.ordinal()).getStringCellValue(),
                                    Utils.toInt(row.getCell(CA.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(MG.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(NA.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(SO4.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(CL.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(HCO3.ordinal()).getNumericCellValue())),
                            Utils.toInt(row.getCell(QTY.ordinal()).getNumericCellValue())))
                    .collect(Collectors.toList());

            if (res.size() == 0) {
                throw new RabdoInputException(Msg.wrongFileNoWaterProvided());
            } else {
                return res;
            }
        } catch (Exception e) {
            throw new RabdoInputException(e);
        }
    }

    @Override public List<Salt> salts() {
        try (Workbook wb = WorkbookFactory.create(file)) {
            List<Salt> res = Streams.stream(Utils.searchSheet(wb, SALTS)
                                                 .orElseThrow(() -> new RabdoException("Sheet not found:"+SALTS.localizedName()))
                                                 .rowIterator())
                    .skip(Constants.HEADER_ROWS)
                    .filter(row -> {
                        String name = row.getCell(NAME.ordinal()).getStringCellValue();
                        return name != null && !name.isEmpty();
                    })
                    .filter(row -> Utils.toInt(row.getCell(QTY.ordinal()).getNumericCellValue()) > 0)
                    .map(row -> new Salt(
                            new SaltProfile(
                                    row.getCell(NAME.ordinal()).getStringCellValue(),
                                    Utils.toInt(row.getCell(CA.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(MG.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(NA.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(SO4.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(CL.ordinal()).getNumericCellValue()),
                                    Utils.toInt(row.getCell(HCO3.ordinal()).getNumericCellValue())),
                            Utils.toInt(row.getCell(QTY.ordinal()).getNumericCellValue())))
                    .collect(Collectors.toList());

            if (res.size() == 0) {
                throw new RabdoInputException(Msg.wrongFileNoWaterProvided());
            } else {
                return res;
            }
        } catch (Exception e) {
            throw new RabdoInputException(e);
        }
    }
}
