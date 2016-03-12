package se.berkar.excel.helpers;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.ss.usermodel.*;
import se.berkar.common.helpers.EmptyHandler;
import se.berkar.model.Loadable;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class WorkbookHelper {

    public static Workbook getWorkbook(URL theWorkbook) throws Exception {
        InputStream aInputStream = theWorkbook.openStream();
        return WorkbookFactory.create(aInputStream);
    }

    public static Workbook getWorkbook(String theWorkbook) throws Exception {
        InputStream aInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(theWorkbook);
        return WorkbookFactory.create(aInputStream);
    }

    public static Sheet getSheet(Workbook theWorkbook, String theSheetName) throws Exception {
        for (int i = 0; i < theWorkbook.getNumberOfSheets(); i++) {
            Sheet sheet = theWorkbook.getSheetAt(i);
            if (theSheetName.equals(sheet.getSheetName())) {
                return sheet;
            }
        }
        return null;
    }

    public static Sheet getSheet(Workbook theWorkbook, int theSheetPos) throws Exception {
        return theWorkbook.getSheetAt(theSheetPos);
    }

    public static boolean isNotEmpty(Cell theCell) {
        if (theCell != null) {
            return EmptyHandler.isNotEmpty(theCell.getStringCellValue());
        }
        return false;
    }

    public static String getStringCellValue(Cell theCell) {
        if (theCell != null) {
            String value = theCell.getStringCellValue();
            if (value != null) {
                return value.replace("\n", " ");
            }
        }
        return null;
    }

    public static <T extends Loadable> Map<String, T> load(Sheet theSheet, Class<T> theClass, String theKeyColumnName,
                                                           int theHeadRow) throws Exception {
        return load(theSheet, theClass, theKeyColumnName, theHeadRow, new HashMap<String, Integer>());
    }

    public static <T extends Loadable> Map<String, T> load(Sheet theSheet, Class<T> theClass, String theKeyColumnName,
                                                           int theHeadRow, Map<String, Integer> theColumnOverride) throws Exception {
        Map<String, T> aMap = new HashMap<>();

        // Setup columns map with Column names
        Map<String, Integer> aColumnList = initColumnMap(theSheet, theHeadRow, theColumnOverride);

        for (int aRowCount = theHeadRow + 1; aRowCount <= theSheet.getLastRowNum(); aRowCount++) {
            Row aRow = theSheet.getRow(aRowCount);
            if (aRow == null) {
                continue;
            }
            HashMap<String, String> aColumnMap = getRowMap(aColumnList, aRow);
            if (!aColumnMap.isEmpty()) {
                T aT = theClass.newInstance();
                aT.load(aColumnMap);
                // make sure the property name is according to what's needed
                Object aKey = PropertyUtils.getSimpleProperty(aT, theKeyColumnName.substring(0, 1).toLowerCase()
                        + theKeyColumnName.substring(1));
                if (aKey != null) {
                    // Special handling of the key, due to POI returning decimal
                    // sometimes, depending on sheet editor?
                    String aKeyAsString = aKey.toString();
                    if (aKeyAsString.contains(".")) {
                        aMap.put(aKeyAsString.substring(0, aKeyAsString.indexOf(".")), aT);
                    } else {
                        aMap.put(aKeyAsString, aT);
                    }
                }
            }
        }

        return aMap;
    }

    public static <T extends Loadable> Map<String, Collection<T>> loadMultiple(Sheet theSheet, Class<T> theClass,
                                                                               String theKeyColumnName, int theHeadRow) throws Exception {
        return loadMultiple(theSheet, theClass, theKeyColumnName, theHeadRow, new HashMap<String, Integer>());
    }

    public static <T extends Loadable> Map<String, Collection<T>> loadMultiple(Sheet theSheet, Class<T> theClass,
                                                                               String theKeyColumnName, int theHeadRow, Map<String, Integer> theColumnOverride) throws Exception {
        Map<String, Collection<T>> aMap = new HashMap<>();

        // Setup columns map with Column names
        Map<String, Integer> aColumnList = initColumnMap(theSheet, theHeadRow, theColumnOverride);

        for (int aRowCount = theHeadRow + 1; aRowCount <= theSheet.getLastRowNum(); aRowCount++) {
            Row aRow = theSheet.getRow(aRowCount);
            if (aRow == null) {
                continue;
            }
            HashMap<String, String> aColumnMap = getRowMap(aColumnList, aRow);
            if (!aColumnMap.isEmpty()) {
                T aT = theClass.newInstance();
                aT.load(aColumnMap);
                // make sure the property name is according to what's needed
                Object aKey = PropertyUtils.getSimpleProperty(aT, theKeyColumnName.substring(0, 1).toLowerCase()
                        + theKeyColumnName.substring(1));
                if (aKey != null) {
                    // Special handling of the key, due to POI returning decimal
                    // sometimes, depending on sheet editor?
                    String aKeyAsString = aKey.toString();
                    if (aKeyAsString.contains(".")) {
                        aKeyAsString = aKeyAsString.substring(0, aKeyAsString.indexOf("."));
                    }
                    if (!aMap.containsKey(aKeyAsString)) {
                        aMap.put(aKeyAsString, new ArrayList<T>());
                    }
                    aMap.get(aKeyAsString).add(aT);
                }
            }
        }

        return aMap;
    }

    private static Map<String, Integer> initColumnMap(Sheet theSheet, int theHeadRow,
                                                      Map<String, Integer> theColumnOverride) {
        Map<String, Integer> aColumnList = new HashMap<>();
        aColumnList.putAll(theColumnOverride);
        Row aRow = theSheet.getRow(theHeadRow);

        // Setup columns map with Column names
        for (int aColCount = 0; aColCount < aRow.getLastCellNum(); aColCount++) {
            Cell aCell = aRow.getCell(aColCount);
            if (aCell != null) {
                String aCellValue = aCell.getStringCellValue();
                // Check for empty column name
                if (EmptyHandler.isEmpty(aCellValue)) {
                    aCellValue = findInPreviousRow(theSheet, theHeadRow, aColCount);
                }
                if (EmptyHandler.isNotEmpty(aCellValue) && !aColumnList.containsKey(aCellValue)) {
                    aColumnList.put(aCellValue, aColCount);
                }
            }
        }
        return aColumnList;
    }

    private static String findInPreviousRow(Sheet theSheet, int theHeadRow, int theColCount) {
        int rowNum = theHeadRow - 1;
        if (rowNum < 0) {
            return null;
        }
        Row aRow = theSheet.getRow(rowNum);
        if (aRow != null && theColCount <= aRow.getLastCellNum()) {
            Cell aCell = aRow.getCell(theColCount);
            if (aCell != null) {
                String aCellValue = aCell.getStringCellValue();
                // Check for empty column name
                if (EmptyHandler.isNotEmpty(aCellValue)) {
                    return aCellValue;
                }
            }
        }
        return findInPreviousRow(theSheet, rowNum, theColCount);
    }

    private static HashMap<String, String> getRowMap(Map<String, Integer> theColumnList, Row theRow) {
        HashMap<String, String> aColumnMap = new HashMap<>();
        for (Map.Entry<String, Integer> aEntry : theColumnList.entrySet()) {
            if (theRow == null || aEntry.getValue() == null) {
                System.out.println();
            }
            Cell aCell = theRow.getCell(aEntry.getValue());
            if (aCell != null) {
                switch (aCell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        aColumnMap.put(aEntry.getKey(), aCell.getStringCellValue());
                        break;
                    default:
                        aColumnMap.put(aEntry.getKey(), aCell.toString());
                }
            } else {
                aColumnMap.put(aEntry.getKey(), "");
            }
        }
        return aColumnMap;
    }

}
