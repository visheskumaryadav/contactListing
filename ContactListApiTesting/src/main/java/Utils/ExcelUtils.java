package Utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// This class will fetch the data from the excel file
public class ExcelUtils {

    public static List<Map<String, String>> getData(String fileName){
        // Needed to store the data as heading and value
        List<Map<String,String>> data=new ArrayList<>();
        // Needed to store columns heading as keys
        List<String> keys=new ArrayList<>();
        // Needed to map every column heading with its row value
        Map<String,String> map;
//        System.out.println(System.getProperty("user.dir") + fileName);
        try(FileInputStream inputStream=new FileInputStream(System.getProperty("user.dir") + fileName)){
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet=workbook.getSheet("data");
            for(int i=0;i<sheet.getPhysicalNumberOfRows();i++) {
                map = new LinkedHashMap<>();
                if (i == 0) {
                    // For heading row
                    for (int j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells(); j++) {
                        keys.add(sheet.getRow(0).getCell(j).getStringCellValue());
                    }
                } else {
                    // For rest of the rows
                    for (int j = 0; j < sheet.getRow(i).getPhysicalNumberOfCells(); j++) {
                        DataFormatter dataFormatter=new DataFormatter();
                        map.put(keys.get(j), dataFormatter.formatCellValue(sheet.getRow(i).getCell(j)));
                    }
                    data.add(map);
                }
            }
        }catch (IOException e){
           e.printStackTrace();
        }
        return data;
    }
}
