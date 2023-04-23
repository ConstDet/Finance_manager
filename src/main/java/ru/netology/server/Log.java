package ru.netology.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//org.fife.io.UnicodeReader

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Log {
    private List<Request> listRequestLog = new ArrayList<>();
    private final File fileJson = new File("jsonLog.json");
    private final File fileBin = new File("data.bin");

    public Log() throws FileNotFoundException {
        loadLog();
    }

    public List<Request> getLog() {
        return listRequestLog;
    }

/*    public  void loadLog() throws FileNotFoundException {
        if (fileJson.exists()) {
            InputStreamReader iSR = new FileReader(fileJson);
            StringBuilder strBuild = new StringBuilder();
            try (BufferedReader BuffR = new BufferedReader(iSR)) {
                int intStr;
                while ((intStr = BuffR.read()) != -1) {
                    strBuild.append(Character.toChars(intStr));
                }
                String strB = strBuild.toString();
                Gson gson = new GsonBuilder().create();
                Request[] req = gson.fromJson(strB, Request[].class);
                listRequestLog.clear();
                for (Request r : req) {
                    listRequestLog.add(r);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }*/

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
            Gson gson = new GsonBuilder().create();
            Request[] req = gson.fromJson(strBis, Request[].class);
            listRequestLog.clear();
            for (Request r : req) {
                listRequestLog.add(r);
            }
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
