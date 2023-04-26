package ru.netology;

import ru.netology.server.CalculateMax;
import ru.netology.server.Log;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;

public class Main {
    static final int PORT = 8989;
    public static void main(String[] args) throws FileNotFoundException {
        Log log = new Log();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                    out.println("Подключились на сервере через порт " + clientSocket.getLocalPort());
                    String str = in.readLine();
                    if (str == null) return;
                    log.addLog(str);//получили сообщение и записали его в лог
                    log.saveLog();//и сохраним его в файле data.bin
                    CalculateMax calculateMax = new CalculateMax(log);//считаем макс сумму по категориям
                    out.println(calculateMax.calcStringMax());//ответ за весь период и по датам
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}