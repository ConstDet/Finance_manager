package ru.netology;

import java.io.FileNotFoundException;
import java.util.List;

public class Server {
    public static void main(String[] args) throws FileNotFoundException {
        LoadSaveTSV loadSaveTSV = new LoadSaveTSV();
        List<Product> productList = loadSaveTSV.loadTSV("categories.tsv");
        productList.forEach(System.out::println);
    }
}