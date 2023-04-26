package ru.netology.server;
//класс строки, получаемой сервером от клиента

public class Request {
    private String title;
    private String date;
    private double sum;

    public void setTitle(String title) {
        this.title = title;
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
