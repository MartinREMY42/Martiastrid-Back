package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "orders")
public class OrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;

    @Basic(optional = false)
    @Column(name = "promo_name")
    private String promoName;

    @Basic(optional = false)
    @Column(name = "reduction")
    private int reduction;

    //,
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkOrder",  orphanRemoval = true)
    private Collection<OrderLineEntity> orderLineCollection;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_payment")
    private PaymentEntity fkPayment;

    @JoinColumn(name = "fk_user", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private UserEntity fkUser;


    public OrderEntity() {
    }

    public OrderEntity(Date creationDate, String promoName, int reduction,
                       Collection<OrderLineEntity> orderLineCollection, PaymentEntity fkPayment,
                       UserEntity fkUser) {
        this.creationDate = creationDate;
        this.promoName = promoName;
        this.reduction = reduction;
        this.orderLineCollection = orderLineCollection;
        this.fkPayment = fkPayment;
        this.fkUser = fkUser;
    }

    public OrderEntity(Integer id,Date creationDate, String promoName, int reduction,
                       Collection<OrderLineEntity> orderLineCollection, PaymentEntity fkPayment,
                       UserEntity fkUser) {
        this.id = id;
        this.creationDate = creationDate;
        this.promoName = promoName;
        this.reduction = reduction;
        this.orderLineCollection = orderLineCollection;
        this.fkPayment = fkPayment;
        this.fkUser = fkUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public int getReduction() {
        return reduction;
    }

    public void setReduction(int reduction) {
        this.reduction = reduction;
    }

    public Collection<OrderLineEntity> getOrderLineCollection() {
        return orderLineCollection;
    }

    public void setOrderLineCollection(Collection<OrderLineEntity> orderLineCollection) {
        this.orderLineCollection = orderLineCollection;
    }

    public PaymentEntity getFkPayment() {
        return fkPayment;
    }

    public void setFkPayment(PaymentEntity fkPayment) {
        this.fkPayment = fkPayment;
    }


    public UserEntity getFkUser() {
        return fkUser;
    }

    public void setFkUser(UserEntity fkUser) {
        this.fkUser = fkUser;
    }


    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", creationDate=" + creationDate +
                ", promoName='" + promoName + '\'' +
                ", reduction=" + reduction +
                ", orderLineCollection=" + orderLineCollection +
                ", fkPayment=" + fkPayment +
                "}\n";
    }
}
