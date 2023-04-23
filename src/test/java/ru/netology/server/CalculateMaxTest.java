package ru.netology.server;

import junit.framework.TestCase;
import org.junit.Test;

import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CalculateMaxTest extends TestCase {
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
    public void testCalcMax() throws FileNotFoundException {
        LogMock logMock = new LogMock();
        logMock.getLog();

        CalculateMax calculateMax = new CalculateMax(logMock);
        String preferences = calculateMax.calcMax();

        String expect = "{\"maxCategory\":{\"category\":\"еда\",\"sum\":300.0}}";

        Assertions.assertEquals(expect, preferences);
    }
}