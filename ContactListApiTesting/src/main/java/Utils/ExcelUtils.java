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
                        /*
                        Purpose of DataFormatter:
                    Excel cells can store data in various formats like numbers, dates, formulas,
                    and even text with specific formatting applied.
                    When you directly access the cell value using methods
                    like getStringCellValue() or getNumericCellValue(), you might get the
                    raw data representation or encounter issues depending on the cell format.
                    How DataFormatter Helps:
                    The DataFormatter class provides a way to format the cell value consistently
                    into a String representation, regardless of the underlying data type or formatting
                    applied in the Excel sheet. This is particularly helpful for data-driven testing where
                    you need predictable and usable data for your test cases.
                    {TestID=1, TestDescription=register with valid data, StatusCode=201, ExpectedErrorMessage=NO_DATA,
                    first_name=dinesh, last_name=kumar, email=dinesh@yopmail.com, password=12345678}
                         */
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
