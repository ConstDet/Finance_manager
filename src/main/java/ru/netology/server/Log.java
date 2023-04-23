package ru.netology.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Log {
    private List<Request> listRequestLog = new ArrayList<>();
    private final File fileJson = new File("jsonLog.json");

    public Log() throws FileNotFoundException {
        loadLog();
    }

    public List<Request> getLog() {
        return listRequestLog;
    }

    public  void loadLog() throws FileNotFoundException {
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
    }

    public void addLog(String strLog) {
        Gson gson = new GsonBuilder().create();
        Request req = gson.fromJson(strLog, Request.class);
        listRequestLog.add(req);
    }
}
