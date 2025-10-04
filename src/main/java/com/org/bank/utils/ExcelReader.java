package com.org.bank.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelReader {

    private final String filePath;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
    }

    public void readData() {
        try (FileInputStream inputStream = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(inputStream)) {
            int numberOfSheet = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheet; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                System.out.println("\n===== Sheet " + (i + 1) + " : " + sheet.getSheetName() + " =====");

                //Print content of each sheet
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String value = "";
                        switch (cell.getCellTypeEnum()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                if (DateUtil.isCellDateFormatted(cell)) {
                                    value = cell.getDateCellValue().toString();
                                } else {
                                    value = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case BOOLEAN:
                                value = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                value = cell.getCellFormula();
                                break;
                            default:
                                value = "";
                        }
                        System.out.printf("%-20s", value);
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}
