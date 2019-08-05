package com.rezaandreza.shop.System.Database;

public class Column {
    String name;
    String type;
    boolean primaryKey;
    boolean autoIncrement;
    boolean unique;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isUnique() {
        return unique;
    }
}
