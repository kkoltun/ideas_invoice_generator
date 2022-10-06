package org.example.nbp;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NbpRateDto {
    private final LocalDate date;
    private final String tableNo;
    private final BigDecimal rate;

    public NbpRateDto(LocalDate date, String tableNo, BigDecimal rate) {
        this.date = date;
        this.tableNo = tableNo;
        this.rate = rate;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTableNo() {
        return tableNo;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
