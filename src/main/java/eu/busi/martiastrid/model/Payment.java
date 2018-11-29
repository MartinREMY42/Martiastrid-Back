package eu.busi.martiastrid.model;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
    private Integer id;
    private Date date;
    private String refPaypal;
    private int sum;

    public Payment() {
    }

    public Payment(Integer id, Date date, String refPaypal, int sum) {
        this.id = id;
        this.date = date;
        this.refPaypal = refPaypal;
        this.sum = sum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRefPaypal() {
        return refPaypal;
    }

    public void setRefPaypal(String refPaypal) {
        this.refPaypal = refPaypal;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
