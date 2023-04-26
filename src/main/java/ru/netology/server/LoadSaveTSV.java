package ru.netology.server;
//класс загрузки, записи в файл categories.tsv пары продукт-категория

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                s[1] = s[1].equals("") ? "другое" : s[1];
                listProduct.add(new Product(s[0], s[1]));
            }
            return listProduct;
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTSV(ArrayList<Product> listProduct) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter("category.tsv", true))) {
            for (Product p : listProduct){
                String[] str = (p.getName() + " " + p.getCategory()).split(" ");
                csvWriter.writeNext(str);
            }
            csvWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
