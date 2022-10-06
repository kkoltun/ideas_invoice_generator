package org.example.nbp;

import java.math.BigDecimal;

public class RateDto {
  private String no;
  private String effectiveDate;
  private BigDecimal mid;

  public RateDto() {
  }

  public String getNo() {
    return no;
  }

  public RateDto setNo(String no) {
    this.no = no;
    return this;
  }

  public String getEffectiveDate() {
    return effectiveDate;
  }

  public RateDto setEffectiveDate(String effectiveDate) {
    this.effectiveDate = effectiveDate;
    return this;
  }

  public BigDecimal getMid() {
    return mid;
  }

  public RateDto setMid(BigDecimal mid) {
    this.mid = mid;
    return this;
  }

  @Override
  public String toString() {
    return "RateDto{" +
        "no='" + no + '\'' +
        ", effectiveDate='" + effectiveDate + '\'' +
        ", mid=" + mid +
        '}';
  }
}
