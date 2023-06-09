package ru.netology.server;
//класс загрузки, записи в файл data.bin строки клиента

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Log {
    private List<Request> listRequestLog = new ArrayList<>();
    private final File fileBin = new File("data.bin");

    public Log() throws FileNotFoundException {
        loadLog();
    }

    public List<Request> getLog() throws ParseException {
        return listRequestLog;
    }

    public  void loadLog() throws FileNotFoundException {
        if (fileBin.exists()) {
            InputStreamReader iSR = new FileReader(fileBin);
            StringBuilder strBuild = new StringBuilder();
            try(BufferedReader BuffR = new BufferedReader(iSR)){
                int intStr;
                while ((intStr = BuffR.read()) != -1) {
                    strBuild.append(Character.toChars(intStr));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String strBis = strBuild.toString();
            if (strBis.equals("")) return;
            Gson gson = new GsonBuilder().create();
            Request[] req = gson.fromJson(strBis, Request[].class);
            listRequestLog.clear();
            Collections.addAll(listRequestLog, req);
        }
    }

    public void saveLog() throws FileNotFoundException {
        if (listRequestLog == null) return;
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(listRequestLog);
        byte[] buffer = str.getBytes();
        try (FileOutputStream out = new FileOutputStream(fileBin);
        BufferedOutputStream bos = new BufferedOutputStream(out)) {
            bos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addLog(String strLog) {
        Gson gson = new GsonBuilder().create();
        Request req = gson.fromJson(strLog, Request.class);
        listRequestLog.add(req);
    }
}
