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
    public CalculateMax(Log log) {
        this.log = log;
        LoadSaveTSV loadSaveTSV = new LoadSaveTSV();
        productList = loadSaveTSV.loadTSV("categories.tsv");
    }

    public String calcStringMax() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 0);
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);//год
        int month = calendar.get(Calendar.MONTH) + 1;//месяц
        int dey = calendar.get(Calendar.DAY_OF_MONTH);//текущий день в месяце
        String allSFirst = calcMax("", "", 0);//за весь период
        String allSLast = allSFirst.substring(1);
        String allS = allSLast.substring(0, allSLast.length() - 1);
        String dateFrom = year + ".01.01";
        String dateTo = year + ".12.31";
        String yearSFirst = calcMax(dateFrom, dateTo, 1);//за год
        String yearSLast = yearSFirst.substring(1);
        String yearS = yearSLast.substring(0, yearSLast.length() - 1);
        dateFrom = year + "." + month + ".01";
        dateTo = year + "." + month + "." + daysInMonth;
        String monthSFirst = calcMax(dateFrom, dateTo, 2);//за месяц
        String monthSLast = monthSFirst.substring(1);
        String monthS = monthSLast.substring(0, monthSLast.length() - 1);
        dateFrom = year + "." + month + "." + (dey - 1);
        dateTo = year + "." + month + "." + (dey + 1);
        String daySFirst = calcMax(dateFrom, dateTo, 3);//за текущий день
        String daySLast = daySFirst.substring(1);
        String dayS = daySLast.substring(0, daySLast.length() - 1);
        return "{" + allS + "," + yearS + "," + monthS + "," + dayS + "}";
    }

    public String calcMax(String fromD, String toD, int period) throws ParseException {
        List<Request> requestList = null;
        if (log == null) return "Пустой лог!";
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
                MaxCategory responseKeyValue = new MaxCategory();
                responseKeyValue.setCategory(cat);
                responseKeyValue.setSum(max);
                ResponseMax responseMax = new ResponseMax();
                responseMax.setResponseKeyValue(responseKeyValue);
                Gson gson = new GsonBuilder().create();
                return gson.toJson(responseMax);
            case 1:
                MaxYearCategory maxYearCategory = new MaxYearCategory();
                maxYearCategory.setCategory(cat);
                maxYearCategory.setSum(max);
                ResponseMax responseMax1 = new ResponseMax();
                responseMax1.setMaxYearCategory(maxYearCategory);
                Gson gson1 = new GsonBuilder().create();
                return gson1.toJson(responseMax1);
            case 2:
                MaxMonthCategory maxMonthCategory = new MaxMonthCategory();
                maxMonthCategory.setCategory(cat);
                maxMonthCategory.setSum(max);
                ResponseMax responseMax2 = new ResponseMax();
                responseMax2.setMaxMonthCategory(maxMonthCategory);
                Gson gson2 = new GsonBuilder().create();
                return gson2.toJson(responseMax2);
            case 3:
                MaxDayCategory maxDayCategory = new MaxDayCategory();
                maxDayCategory.setCategory(cat);
                maxDayCategory.setSum(max);
                ResponseMax responseMax3 = new ResponseMax();
                responseMax3.setMaxDayCategory(maxDayCategory);
                Gson gson3 = new GsonBuilder().create();
                return gson3.toJson(responseMax3);
            default:
                break;
        }
        return "";
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
