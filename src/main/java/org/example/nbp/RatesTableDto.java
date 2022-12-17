package org.example.nbp;

import java.util.List;

public class RatesTableDto {
    private String table;
    private String currency;
    private String code;
    private List<RateDto> rates;

    public RatesTableDto() {
    }

    public String getTable() {
        return table;
    }

    public RatesTableDto setTable(String table) {
        this.table = table;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public RatesTableDto setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public String getCode() {
        return code;
    }

    public RatesTableDto setCode(String code) {
        this.code = code;
        return this;
    }

    public List<RateDto> getRates() {
        return rates;
    }

    public RatesTableDto setRates(List<RateDto> rates) {
        this.rates = rates;
        return this;
    }

    @Override
    public String toString() {
        return "RatesTableDto{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}
