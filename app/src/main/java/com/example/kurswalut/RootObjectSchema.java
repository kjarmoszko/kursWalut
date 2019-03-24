package com.example.kurswalut;

public class RootObjectSchema {
    public String table;
    public String currency;
    public String code;
    public RateSchema[] rates;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RateSchema[] getRates() {
        return rates;
    }

    public void setRates(RateSchema[] rates) {
        this.rates = rates;
    }
}
