package ru.netology.server;
//класс объекта JSON, содержащий пары ключ-значение максимальной категории и значения за период

public class MaxMonthCategory {
    private String category;
    private double sum;

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public double getSum() {
        return getSum();
    }

}
