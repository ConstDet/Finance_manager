package ru.netology.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateMax {
    private Log log;
    private HashMap<String, Double> mapMax = new HashMap<>();
    private List<Product> productList;
    public CalculateMax(Log log) {
        this.log = log;
        LoadSaveTSV loadSaveTSV = new LoadSaveTSV();
        productList = loadSaveTSV.loadTSV("categories.tsv");
    }

    public String calcMax() {
        if (log == null) return "Пустой лог!";
        List<Request> requestList = log.getLog();
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
}
