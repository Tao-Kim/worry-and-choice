package com.tao.wnc.model.domain;

public class SelectItem {
    private String name;
    private int count;

    public SelectItem(String name){
        this.name = name;
        count = 0;
    }

    public SelectItem(String name, int count){
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
