package com.javarush.lectures.rest_example.DownloadParse.parse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс ExcelParser предназначен для парсинга .xlsx файлов
 */
public class ExcelParser {

    /**
     * Метод parse(String fileName), который будет
     * принимать в качестве параметра путь к .xlsx
     * файлу. После чего будет его парсить
     *
     * @param fileName
     * @return
     */
    public static List<String[]> parse(String fileName) {

        /**
         * Инициализируем потоки
         */

        InputStream inputStream = null;
        XSSFWorkbook workbook = null;

        try {
            inputStream = new FileInputStream(fileName);
            workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * Разбираем первый лист входного файла на объектную модель
         */
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        List<String[]> arrList = new ArrayList<String[]>();

        /**
         * Проходим по всему листу
         */
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            int i = 0;
            String[] arrString = new String[row.getPhysicalNumberOfCells()];
            while (cells.hasNext()) {
                Cell cell = cells.next();
                int cellType = cell.getCellType();

                /**
                 * Перебираем возможные типы ячеек
                 */
                switch (cellType) {

                    case Cell.CELL_TYPE_STRING: {
                        arrString[i] = cell.getStringCellValue();
                        i++;
                        break;
                    }
                    case Cell.CELL_TYPE_NUMERIC: {
                        arrString[i] = String.valueOf(cell.getNumericCellValue());
                        i++;
                        break;
                    }
                    case Cell.CELL_TYPE_FORMULA: {
                        arrString[i] = String.valueOf(cell.getNumericCellValue());
                        i++;
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
            arrList.add(arrString);
        }
        return arrList;
    }
}
