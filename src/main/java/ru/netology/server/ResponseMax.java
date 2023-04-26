package ru.netology.server;
//класс, содержащий ключи внутренних объектов строки JSON (ответ сервера)

public class ResponseMax {
    private MaxCategory maxCategory;
    private MaxYearCategory maxYearCategory;
    private MaxMonthCategory maxMonthCategory;
    private MaxDayCategory maxDayCategory;

    public void setResponseKeyValue(MaxCategory maxCategory) {
        this.maxCategory = maxCategory;
    }

    public MaxCategory getResponseKeyValue() {
        return maxCategory;
    }

    public void setMaxYearCategory(MaxYearCategory maxYearCategory) {
        this.maxYearCategory = maxYearCategory;
    }

    public MaxYearCategory getMaxYearCategory() {
        return maxYearCategory;
    }

    public void setMaxMonthCategory(MaxMonthCategory maxMonthCategory) {
        this.maxMonthCategory = maxMonthCategory;
    }

    public MaxMonthCategory getMaxMonthCategory() {
        return maxMonthCategory;
    }

    public void setMaxDayCategory(MaxDayCategory maxDayCategory) {
        this.maxDayCategory = maxDayCategory;
    }

    public MaxDayCategory getMaxDayCategory() {
        return maxDayCategory;
    }
}
