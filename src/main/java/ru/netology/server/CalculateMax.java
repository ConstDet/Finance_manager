package ru.netology.server;

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
        String allS = calcMax("", "");//за весь период
        String dateFrom = year + ".01.01";
        String dateTo = year + ".12.31";
        String yearS = calcMax(dateFrom, dateTo);//за год
        dateFrom = year + "." + month + ".01";
        dateTo = year + "." + month + "." + daysInMonth;
        String monthS = calcMax(dateFrom, dateTo);//за месяц
        dateFrom = year + "." + month + "." + (dey - 1);
        dateTo = year + "." + month + "." + dey;
        String dayS = calcMax(dateFrom, dateTo);//за текущий день
        return "[" + allS + "," + yearS + "," + monthS + "," + dayS + "]";
    }

    public String calcMax(String fromD, String toD) throws ParseException {
        List<Request> requestList = null;
        if (log == null) return "Пустой лог!";
        if (fromD.equals("") && toD.equals("")) { //весь период
            requestList = log.getLog();
        } else {
            requestList = periodData(fromD, toD);
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
        MaxCategory responseKeyValue = new MaxCategory();
        responseKeyValue.setCategory(cat);
        responseKeyValue.setSum(max);
        ResponseMax responseMax = new ResponseMax();
        responseMax.setResponseKeyValue(responseKeyValue);
        Gson gson = new GsonBuilder().create();
        return gson.toJson(responseMax);
    }

    public List<Request> periodData(String fromD, String toD) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date dateFrom = formatter.parse(fromD);
        Date dateTo = formatter.parse(toD);
        List<Request> listOut = new ArrayList<>();
        List<Request> listReq = log.getLog();
        for (Request r : listReq) {
            Date dateReq = formatter.parse(r.getDate());
            if (dateReq.after(dateFrom) && dateReq.before(dateTo)) { //в заданном диапазоне, пишем в List
                listOut.add(r);
            }
        }
        return listOut;
    }
}
