package ru.netology;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LoadSaveTSV {
    private ArrayList<Product> listProduct = new ArrayList<>();

    public ArrayList<Product> loadTSV(String file) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator('\t')
                .build();
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(file))
                .withCSVParser(parser)
                .build()) {
            List<String[]> allRow = reader.readAll();
            for (String[] s : allRow) {
                listProduct.add(new Product(s[0], s[1]));
            }
            return listProduct;
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
