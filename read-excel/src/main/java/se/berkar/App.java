package se.berkar;


import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import se.berkar.common.helpers.EmptyHandler;
import se.berkar.excel.helpers.WorkbookHelper;
import se.berkar.model.Loadable;
import se.berkar.model.Resultat;

import java.net.URL;
import java.util.Map;

public class App {

    private static String FILE;
    private static String keyColumnName;
    private static int tabPos;
    private static int headerRowPos;

    private static Map<String, String> itsEndTime;

    private static Workbook itsWorkbook;

    public static void main(String[] args) throws Exception {
        if (EmptyHandler.isEmpty(args)) {
            System.out.println("Usage: java -jar [url-to-jar-file] <key-column-name:id> <tab-pos:0> <header-row-start:0>");
            return;
        }
        FILE = args[0];
        keyColumnName = args.length > 1 ? args[1] : "id";
        tabPos = args.length > 2 ? Integer.valueOf(args[2]) : 0;
        headerRowPos = args.length > 3 ? Integer.valueOf(args[3]) : 0;
//        FILE = "file:C:/Users/beka_metria.se/Downloads/Sample - Penticton Granfondo.xls";
        try {
            itsWorkbook = WorkbookHelper.getWorkbook(new URL(FILE));

            Map<String, Resultat> resultatMap = load(tabPos, Resultat.class, keyColumnName, headerRowPos);

            for (Map.Entry<String, Resultat> entry : resultatMap.entrySet()) {
                System.out.println(entry.getKey() + " => " + entry.getValue());
            }
        } catch (Exception e) {
            throw new Exception("Error reading " + FILE, e);
        }
    }

    private static <T extends Loadable> Map<String, T> load(int theSheetPos, Class<T> theClass, String theKeyColumn, int theHeadRow) throws Exception {
        Sheet sheet = WorkbookHelper.getSheet(itsWorkbook, theSheetPos);
        if (sheet == null) {
            throw new Exception(String.format("Sheet pos %1$d is missing in %2$s", theSheetPos, FILE));
        }
        return WorkbookHelper.load(sheet, theClass, theKeyColumn, theHeadRow);
    }
}
