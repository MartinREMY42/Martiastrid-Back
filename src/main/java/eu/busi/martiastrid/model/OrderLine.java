package eu.busi.martiastrid.model;

import java.io.Serializable;

public class OrderLine implements Serializable {
    private Integer id;
    private Pizza pizza;
    private Integer quantity;

    public OrderLine() {
    }

    public OrderLine(Integer id, int quantity, Pizza pizza) {
        this.id = id;
        this.quantity = quantity;
        this.pizza = pizza;
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

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

}
