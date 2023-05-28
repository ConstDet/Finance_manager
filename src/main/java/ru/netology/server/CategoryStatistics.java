package ru.netology.server;

import java.util.HashMap;
import java.util.Map;

public class CategoryStatistics {
    private Map maxCategory;
    private Map maxYearCategory;
    private Map maxMonthCategory;
    private Map maxDayCategory;

    public CategoryStatistics() {
        Map maxCategory = new HashMap<>();
        Map maxYearCategory = new HashMap<>();
        Map maxMonthCategory = new HashMap<>();
        Map maxDayCategory = new HashMap<>();
    }

    public Map getMaxCategory() {
        return maxCategory;
    }

    public Map getMaxYearCategory() {
        return maxYearCategory;
    }

    public Map getMaxMonthCategory() {
        return maxMonthCategory;
    }

    public Map getMaxDayCategory() {
        return maxDayCategory;
    }

    public void setMaxCategory(Map maxCategory) {
        this.maxCategory = maxCategory;
    }

    public void setMaxYearCategory(Map maxYearCategory) {
        this.maxYearCategory = maxYearCategory;
    }

    public void setMaxMonthCategory(Map maxMonthCategory) {
        this.maxMonthCategory = maxMonthCategory;
    }

    public void setMaxDayCategory(Map maxDayCategory) {
        this.maxDayCategory = maxDayCategory;
    }
}
