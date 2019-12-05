package dclab.powerdatabackend.model;

import java.util.Date;

public class Rtdata {
    private int customerid;
    private String meterid;

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getMeterid() {
        return meterid;
    }

    public void setMeterid(String meterid) {
        this.meterid = meterid;
    }

    public String getMetercolumn() {
        return metercolumn;
    }

    public void setMetercolumn(String metercolumn) {
        this.metercolumn = metercolumn;
    }

    public double getCulunmvalue() {
        return culunmvalue;
    }

    public void setCulunmvalue(double culunmvalue) {
        this.culunmvalue = culunmvalue;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    private String metercolumn;
    private double culunmvalue;
    private String regdate;
}
