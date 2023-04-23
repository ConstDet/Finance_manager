package ru.netology.server;

public class ResponseMax {
    private MaxCategory maxCategory;

    public void setResponseKeyValue(MaxCategory maxCategory) {
        this.maxCategory = maxCategory;
    }

    public MaxCategory getResponseKeyValue() {
        return maxCategory;
    }
}
