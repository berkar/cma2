package se.berkar;

import org.apache.commons.csv.*;
import se.berkar.common.helpers.EmptyHandler;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;

public class App {

    private static String FILE;

    public static void main(String[] args) throws Exception {
/*
        if (EmptyHandler.isEmpty(args)) {
            System.out.println("Usage: java -jar [url-to-jar-file] <file>");
            return;
        }
*/
        FILE = args[0];
//        FILE = "c:/Users/beka_metria.se/Downloads/TuffaTimmen2.txt";
        try {
            Reader in = new FileReader(FILE);
            Writer out = new FileWriter(FILE + ".out");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter('\t').withHeader().parse(in);
            CSVPrinter printer = CSVFormat.DEFAULT.withDelimiter('\t').withQuoteMode(QuoteMode.NON_NUMERIC).withHeader("Name", "Bib", "Start time", "Gender").print(out);
            for (CSVRecord record : records) {
                String firstName = record.get("First name");
                String lastName = record.get("Last name");
                String bib = record.get("Bib");
                String startTime = record.get("Start time");
                String gender = record.get("Gender");

                printer.printRecord(firstName + " " + lastName, bib, startTime, gender);
            }
            printer.close();
        } catch (Exception e) {
            throw new Exception("Error reading " + FILE, e);
        }
    }
}
