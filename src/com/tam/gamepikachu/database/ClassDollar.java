package com.tam.gamepikachu.database;

public class ClassDollar {
    String name;
    int dollar;
    int theme;
 
    public ClassDollar(String name, int dollar, int theme){
        this.name = name;
        this.dollar = dollar;
        this.theme = theme;
    }
 
    public String getName() {
        return name;
    }
 
    public int getDollar() {
        return dollar;
    }
 
    public int getTheme() {
        return theme;
    }
}
