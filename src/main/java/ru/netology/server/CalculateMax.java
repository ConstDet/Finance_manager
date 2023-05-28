package ru.netology.server;
//класс подсчета максимальных затрат по категориям продуктов

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalculateMax {
    private Log log;
    private HashMap<String, Double> mapMax = new HashMap<>();
    private List<Product> productList;
    protected CategoryStatistics categoryStatistics;
    public CalculateMax(Log log) {
        this.log = log;
        LoadSaveTSV loadSaveTSV = new LoadSaveTSV();
        productList = loadSaveTSV.loadTSV("categories.tsv");
        categoryStatistics = new CategoryStatistics();
    }

    private Calendar setDey(String jsonStringFromClient) throws ParseException {
        Gson gson = new GsonBuilder().create();
        Request req = gson.fromJson(jsonStringFromClient, Request.class);
        String[] strCalendar = req.getDate().split("\\.");
        int yearInt = Integer.parseInt(strCalendar[0]);
        int monthInt = Integer.parseInt(strCalendar[1]);
        int dayInt = Integer.parseInt(strCalendar[2]);
        return new GregorianCalendar(yearInt, monthInt - 1, dayInt);
    }

    public String calcStringMax(String jsonStringFromClient) throws ParseException {
        Calendar calendar = setDey(jsonStringFromClient);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dey = calendar.get(Calendar.DAY_OF_MONTH);
        calcMax("", "", 0);//за весь период
        String dateFrom = year + ".01.01";
        String dateTo = year + ".12.31";
        calcMax(dateFrom, dateTo, 1);//за год
        dateFrom = year + "." + month + ".01";
        dateTo = year + "." + month + "." + daysInMonth;
        calcMax(dateFrom, dateTo, 2);//за месяц
        dateFrom = year + "." + month + "." + (dey - 1);
        dateTo = year + "." + month + "." + (dey + 1);
        calcMax(dateFrom, dateTo, 3);//за текущий день
        Gson gson = new GsonBuilder().create();
        String str = gson.toJson(categoryStatistics);
        return str;
    }

    public void calcMax(String fromD, String toD, int period) throws ParseException {
        List<Request> requestList = null;
        if (log == null) return;
        if (fromD.equals("") && toD.equals("")) { //весь период
            requestList = log.getLog();
        } else {
            requestList = periodData(fromD, toD);//сформируем за период
        }
        String findCategory = "";
        for (Request request : requestList) {
            if (request == null)
                continue;
            for (Product product : productList) {
                if (request.getTitle().equals(product.getName())) {
                    findCategory = product.getCategory();
                    break;
                } else {
                    findCategory = "другое";
                }
            }
            if (mapMax.containsKey(findCategory)) {//если такая категория существует в мапе
                double sumMap = mapMax.get(findCategory);//получим ее значение
                mapMax.put(findCategory, sumMap + request.getSum());//добавим текущую сумму
            } else {//если не существует
                mapMax.put(findCategory, request.getSum());//запишем категорию и текущую сумму
            }
        }

        Double max = -1.0;
        String cat = "";
        for (Map.Entry<String, Double> entry : mapMax.entrySet()) {
            if (entry.getValue() > max) {
                cat = entry.getKey();
                max = entry.getValue();
            }
        }
        //очистим мапу mapMax
        mapMax.clear();
        switch (period) {
            case 0:
                Map maxCategory = new TreeMap(); {
                maxCategory.put("category", cat);
                maxCategory.put("sum", max);
            }
                categoryStatistics.setMaxCategory(maxCategory);
                break;
            case 1:
                Map maxYearCategory = new TreeMap<>(); {
                maxYearCategory.put("category", cat);
                maxYearCategory.put("sum", max);
            }
            categoryStatistics.setMaxYearCategory(maxYearCategory);
                break;
            case 2:
                Map maxMonthCategory = new TreeMap<>(); {
                maxMonthCategory.put("category", cat);
                maxMonthCategory.put("sum", max);
            }
            categoryStatistics.setMaxMonthCategory(maxMonthCategory);
                break;
            case 3:
                Map maxDayCategory = new TreeMap<>(); {
                maxDayCategory.put("category", cat);
                maxDayCategory.put("sum", max);
            }
            categoryStatistics.setMaxDayCategory(maxDayCategory);
                break;
            default:
                break;
        }
    }

    public Date StrToDate(String strDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy.MM.dd");
        return format.parse(strDate);
    }

    public List<Request> periodData(String fromD, String toD) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date dateFrom = formatter.parse(fromD);
        Date dateTo = formatter.parse(toD);
        List<Request> listOut = new ArrayList<>();
        List<Request> listReq = log.getLog();
        for (Request r : listReq) {
            if (StrToDate(r.getDate()).after(dateFrom) &&
                    StrToDate(r.getDate()).before(dateTo)) { //в заданном диапазоне, пишем в List
                listOut.add(r);
            }
        }
        return listOut;
    }
}
