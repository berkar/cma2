package se.berkar.web.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import se.berkar.common.helpers.EmptyHandler;
import se.berkar.model.Anmalning;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK;

public class AnmalningReader {
	public List<Anmalning> fromStream(final InputStream inputStream) throws IllegalFormatException {
		final List<Anmalning> results = new ArrayList<>();
		try {
			final Workbook workbook = WorkbookFactory.create(inputStream);
			if (workbook.getNumberOfSheets() != 1) {
				throw new IllegalArgumentException("Filen måste innehålla exakt ett arbetsblad");
			}
			Sheet sheet = workbook.getSheetAt(0);
			for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				String aName = parseStringValue(sheet.getRow(rowIndex).getCell(0), rowIndex);
				if (EmptyHandler.isNotEmpty(aName)) {
					results.add(new Anmalning(aName));
				}
			}
		} catch (InvalidFormatException | IOException e) {
			throw new IllegalFormatException(e);
		}
		return results;
	}

	private static String parseStringValue(final Cell cell, final int row) {
		if (cell == null || cell.getCellType() == CELL_TYPE_BLANK) {
			return "";
		}
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			default:
				throw new IllegalArgumentException("Värdet på rad " + (row + 1) + " är inte av godkänd typ!");
		}
	}
}
