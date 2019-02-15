package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    /*
    
        Consider the following CSV data:
        
        "ID","Total","Assignment 1","Assignment 2","Exam 1"
        "111278","611","146","128","337"
        "111352","867","227","228","412"
        "111373","461","96","90","275"
        "111305","835","220","217","398"
        "111399","898","226","229","443"
        "111160","454","77","125","252"
        "111276","579","130","111","338"
        "111241","973","236","237","500"
        
        The corresponding JSON data would be similar to the following (tabs and
        other whitespace have been added for clarity).  Note the curly braces,
        square brackets, and double-quotes!  These indicate which values should
        be encoded as strings, and which values should be encoded as integers!
        
        {
            "colHeaders":["ID","Total","Assignment 1","Assignment 2","Exam 1"],
            "rowHeaders":["111278","111352","111373","111305","111399","111160",
            "111276","111241"],
            "data":[[611,146,128,337],
                    [867,227,228,412],
                    [461,96,90,275],
                    [835,220,217,398],
                    [898,226,229,443],
                    [454,77,125,252],
                    [579,130,111,338],
                    [973,236,237,500]
            ]
        }
    
        Your task for this program is to complete the two conversion methods in
        this class, "csvToJson()" and "jsonToCsv()", so that the CSV data shown
        above can be converted to JSON format, and vice-versa.  Both methods
        should return the converted data as strings, but the strings do not need
        to include the newlines and whitespace shown in the examples; again,
        this whitespace has been added only for clarity.
    
        NOTE: YOU SHOULD NOT WRITE ANY CODE WHICH MANUALLY COMPOSES THE OUTPUT
        STRINGS!!!  Leave ALL string conversion to the two data conversion
        libraries we have discussed, OpenCSV and json-simple.  See the "Data
        Exchange" lecture notes for more details, including example code.
    
    */
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            
            JSONObject JSONObject = new JSONObject();
            JSONArray colHeaders = new JSONArray();
            JSONArray rowHeaders = new JSONArray();
            JSONArray data = new JSONArray();
            JSONArray dataRow = new JSONArray();
            
            for (int i = 0; i < full.get(0).length; i++) {
                
                colHeaders.add(full.get(0)[i]);
                
            }
            
            for (int i = 1; i < full.size(); i++) {
                
                rowHeaders.add(full.get(i)[0]);
            }
            
                for (int i = 1; i < full.size(); i++) {
                    
                    for (int j = 1; j < full.get(0).length; j++) {
                    
                        int dat = Integer.parseInt(full.get(i)[j]);
                        dataRow.add(dat);
                
                    }
                    
                   data.add(dataRow.clone());
                   dataRow.clear();
                    
                }
                
            JSONObject.put("colHeaders",colHeaders);
            JSONObject.put("rowHeaders",rowHeaders);
            JSONObject.put("data",data);
            results = JSONValue.toJSONString(JSONObject);
            
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            JSONArray colHeaders = (JSONArray) (jsonObject.get("colHeaders"));
            JSONArray rowHeaders = (JSONArray) (jsonObject.get("rowHeaders"));
            JSONArray data = (JSONArray) (jsonObject.get("data"));
            String[] colArray = new String[colHeaders.size()];
            String[] dataArray = new String[colHeaders.size()];
            
            for(int i = 0; i < colHeaders.size(); i++) {
                
                colArray[i] = (String) colHeaders.get(i);
                
            }
            
            csvWriter.writeNext(colArray);
                
            for(int i = 0; i < rowHeaders.size(); i++) {
                
                dataArray[0] = (String) rowHeaders.get(i);
             
                for(int j = 1; j < colHeaders.size(); j++) {

                    JSONArray rowArray = (JSONArray)data.get(i);
    
                    for(int k = 0; k < rowArray.size() ; k++) {
                        
                        dataArray[k+1] = rowArray.get(k).toString();
                        
                        }
                    
                }
                
            csvWriter.writeNext(dataArray);
                    
            }
 
            results = writer.toString();
            
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}