package ru.netology.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculateMaxTest {
    Request requestMock = new Request();

    public class LogMock extends Log {

        public LogMock() throws FileNotFoundException {
            loadLog();
        }

        @Override
        public List<Request> getLog() {
            requestMock.setTitle("булка");
            requestMock.setDate("2023.04.23");
            requestMock.setSum(300.0);
            List<Request> listRequestLogMock = new ArrayList<>();
            listRequestLogMock.add(requestMock);
            return listRequestLogMock;
        }

        @Override
        public void loadLog() throws FileNotFoundException {
            super.loadLog();
        }
    }
    @Test
    void calcMax() throws FileNotFoundException, ParseException {
        LogMock logMock = new LogMock();
        logMock.getLog();

        CalculateMax calculateMax = new CalculateMax(logMock);
        calculateMax.calcMax("", "", 0);
        Gson gson = new GsonBuilder().create();
        String preferences = gson.toJson(calculateMax.categoryStatistics);

        String expect = "{\"maxCategory\":{\"category\":\"еда\",\"sum\":300.0}}";

        Assertions.assertEquals(expect, preferences);
    }
}