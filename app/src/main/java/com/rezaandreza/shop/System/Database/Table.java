package com.rezaandreza.shop.System.Database;

import java.util.ArrayList;

public class Table {
    String name;
    ArrayList<Column> columns;

    public void setName(String name) {
        this.name = name;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }
}
