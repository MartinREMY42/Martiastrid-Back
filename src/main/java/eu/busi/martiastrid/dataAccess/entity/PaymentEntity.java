package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "payment")
public class PaymentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Basic(optional = false)
    @Column(name = "ref_paypal")
    private String refPaypal;

    @Basic(optional = false)
    @Column(name = "total")
    private int total;

    @OneToOne(mappedBy = "fkPayment")
    private OrderEntity order;

    public PaymentEntity() {
    }

    public PaymentEntity(Integer id) {
        this.id = id;
    }

    public PaymentEntity(Integer id, Date date, String refPaypal, int total) {
        this.id = id;
        this.date = date;
        this.refPaypal = refPaypal;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }


}
