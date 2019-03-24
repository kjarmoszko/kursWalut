package com.example.kurswalut;

import java.util.Arrays;
import java.util.List;

public class RootObject
{
    public String no;
    public String table;
    public Rate[] rates;
    public String effectiveDate;
    public String tradingDate;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Rate[] getRates() {
        return rates;
    }

    public void setRates(Rate[] rates) {
        this.rates = rates;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    @Override
    public String toString() {
        return "RootObject{" +
                "no='" + no + '\'' +
                ", table='" + table + '\'' +
                ", rates=" + Arrays.toString(rates) +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", tradingDate='" + tradingDate + '\'' +
                '}';
    }
}