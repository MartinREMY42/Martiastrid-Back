package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "order_line")
public class OrderLineEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "quantity")
            private int quantity;

    @JoinColumn(name = "fk_order", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrderEntity fkOrder;
    
    @JoinColumn(name = "fk_pizza", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private PizzaEntity fkPizza;

    public OrderLineEntity() {
    }

    public OrderLineEntity(Integer id, int quantity, OrderEntity fkOrder, PizzaEntity fkPizza) {
        this.id = id;
        this.quantity = quantity;
        this.fkOrder = fkOrder;
        this.fkPizza = fkPizza;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderEntity getFkOrder() {
        return fkOrder;
    }

    public void setFkOrder(OrderEntity fkOrder) {
        this.fkOrder = fkOrder;
    }

    public PizzaEntity getFkPizza() {
        return fkPizza;
    }

    public void setFkPizza(PizzaEntity fkPizza) {
        this.fkPizza = fkPizza;
    }

    @Override
    public String toString() {
        return "OrderLineEntity{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", fkOrder=" + fkOrder.getId() +
                ", fkPizza=" + fkPizza +
                "}\n";
    }
}
