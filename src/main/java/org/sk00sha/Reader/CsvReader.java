package org.sk00sha.Reader;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class CsvReader {

    public static List<String[]> readSourceCsvData(String file){
        List<String[]> results = null;
        try {
            FileReader csvFileReader = new FileReader(file);

            CSVReader csvReader = new CSVReader(csvFileReader);

           results = csvReader.readAll();

           //Removing headers
           results.remove(0);

        }
        catch(Exception e){
            System.out.println("Caught exception while processing csv file: "+e.getMessage());
        }
        return results;
    }

}
