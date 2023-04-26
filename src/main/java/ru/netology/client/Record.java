package ru.netology.client;
//класс формирования строки запроса от клиента серверу

public class Record {
    private String title;
    private String date;
    private double sum;

    public void setTitle(String name) {
        this.title = name;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "{\"title:\" " + title + "\"date:\" " + date + "\"sum:\" " + sum + "}";
    }
}
