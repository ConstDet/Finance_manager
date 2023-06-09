package ru.netology;

import ru.netology.client.RecordInServer;
import ru.netology.server.LoadSaveTSV;
import ru.netology.server.Product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        final int PORT = 8989;
        final String HOST = "localhost";
        while (true) {
            try (Socket clientSocket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                System.out.printf("Подключились на %d порту%n", clientSocket.getPort());
                System.out.println(in.readLine());
                Scanner scanner = new Scanner(System.in);
                System.out.println("Что вы хотите сделать?");
                System.out.println("1. Отправить запись о покупке на сервер");
                System.out.println("2. Показать список \"Продукт-Категория\" и завершить работу клиента");
                System.out.println("3. Завершить работу клиента");
                String choice = scanner.nextLine();
                switch (choice) {
                    case "1":
                        RecordInServer record = new RecordInServer(scanner, in, out);
                        record.inServer();
                        break;
                    case "2":
                        LoadSaveTSV loadSaveTSV = new LoadSaveTSV();
                        ArrayList<Product> arrayListP = loadSaveTSV.loadTSV("categories.tsv");
                        arrayListP.forEach(System.out::println);
                        System.out.println("Требуется новое подключение клиента и сервера!");
                        return;
                    case "3":
                        System.out.println("Всего хорошего!");
                        return;
                    default:
                        System.out.println("Требуется указать цифру от 1 до 3!");
                        return;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}