package ru.netology;

import ru.netology.server.LoadSaveTSV;
import ru.netology.server.Log;
import ru.netology.server.Product;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Main {
    static final int PORT = 8989;
    public static void main(String[] args) throws FileNotFoundException {
        LoadSaveTSV loadSaveTSV = new LoadSaveTSV();
        List<Product> productList = loadSaveTSV.loadTSV("categories.tsv");
        productList.forEach(System.out::println);
        /*Log log = new Log();
        for (int i = 0; i < 10; i++) {
            log.addLog("{\"title\":\"булка\",\"date\":\"2023.04.20\",\"sum\":" + (double) i + "}");
        }
        log.saveLog();*/
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}