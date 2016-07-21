package se.berkar.web.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import se.berkar.common.helpers.EmptyHandler;
import se.berkar.model.Foranmald;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import static org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BLANK;

public class ForanmaldReader {
	private final static Integer SHEET = 0;
	private final static Integer ROW_POS = 0;
	private final static Integer COLUMN_POS = 0;

	public List<Foranmald> fromStream(final InputStream inputStream) throws IllegalFormatException {
		final List<Foranmald> results = new ArrayList<>();
		try {
			final Workbook workbook = WorkbookFactory.create(inputStream);
//			if (workbook.getNumberOfSheets() > SHEET) {
//				throw new IllegalArgumentException("Felaktigt antal arbetsblad!");
//			}
			Sheet sheet = workbook.getSheetAt(SHEET);
			for (int rowIndex = ROW_POS; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				String aFirstname = parseStringValue(sheet.getRow(rowIndex).getCell(COLUMN_POS), rowIndex);
				String aLastname = parseStringValue(sheet.getRow(rowIndex).getCell(COLUMN_POS + 1), rowIndex);
				if (EmptyHandler.isNotEmpty(aFirstname)) {
					results.add(new Foranmald(aFirstname + (EmptyHandler.isNotEmpty(aLastname) ? (" " + aLastname): "")));
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
