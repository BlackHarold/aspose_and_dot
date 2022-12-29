package ru.bluewhale;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.bluewhale.bean.Code;
import ru.bluewhale.bean.Group;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class OpenCSV {

    public static void main(String[] args) {
        File input = new File("C:/Temp/коды документов 1.xlsx");
        File output = new File("C:/Temp/output.csv");
        try {
            convertXls2Cvs(input, output);
        } catch (Exception e) {
            System.err.println("exception: " + e.getMessage());
            e.printStackTrace();
        }

        List<Group> groups = accumulateDataByPathFile(output);
        for (int i = 0; i < groups.size(); i++) {
            System.out.println("group " + i + ": " + groups.get(i));
            //search group in db
            //BusinessObject group = new BusinessObject(ctx,
            //for connect or remove connection
        }
    }

    public static void convertXls2Cvs(File input, File output) throws Exception {
        StringBuffer sb = new StringBuffer();

        if (!output.exists()) {
            output.getParentFile().mkdirs();
            output.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(output, false);

        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(input));
        XSSFSheet sheet = wb.getSheet("table");

        Row row;
        Cell cell;

        Iterator<Row> rowIterator = sheet.iterator();

        while (rowIterator.hasNext()) {
            row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                cell = cellIterator.next();

                switch (cell.getCellType()) {
                    case BOOLEAN -> sb.append(cell.getBooleanCellValue());
                    case NUMERIC -> sb.append(cell.getNumericCellValue());
                    case STRING -> sb.append(cell.getStringCellValue());
//                    case FORMULA, BLANK, _NONE, ERROR -> sb.append("");
                    default -> sb.append(cell);
                }

                sb.append(",");
            }
            sb.append("\n");
        }

        DataOutputStream dos = new DataOutputStream(fos);
        dos.writeUTF(sb.toString());
        fos.close();
    }

    public static List<Group> accumulateDataByPathFile(File file) {

        Reader reader = getBufferedReader(file);
        CSVReader csvReader = new CSVReader(reader);

        String[] headers = getHeaders(csvReader);
        System.out.println("headers: " + Arrays.asList(headers));
        List<String[]> list = readAllLines(reader);

        List<Group> groups = list.stream()
                .filter(a -> a != null && !a[0].isEmpty())
                .map(a -> {

                    Group group = new Group();
                    group.setName(a[0]);
                    group.setDepartment(a[1]);

                    List<Code> codes = new ArrayList<>();
                    for (int i = 2; i < a.length; i++) {
                        if (a[i] != null && a[i].equals("+")) {
                            codes.add(new Code(headers[i]));
                        }
                    }
                    group.setCodes(codes);


                    System.out.println(group);
                    return group;
                }).collect(Collectors.toList());

        return groups;
    }


    public static List<String[]> readAllLines(Reader reader) {
        try {


            CSVParser parser = new CSVParserBuilder()
                    .withSeparator(',')
                    .withIgnoreQuotations(true)
                    .build();

            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withSkipLines(1)
                    .withCSVParser(parser)
                    .build();


            return csvReader.readAll();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String[] getHeaders(CSVReader csvReader) {
        try {
            return csvReader.readNext();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static Reader getBufferedReader(File file) {
        Reader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), StandardCharsets.UTF_8)
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

        return reader;
    }
}
