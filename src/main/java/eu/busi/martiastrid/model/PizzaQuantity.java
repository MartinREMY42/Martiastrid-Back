package eu.busi.martiastrid.model;

import java.util.Objects;

public class PizzaQuantity {
    private Pizza pizza;
    private int quantity;

    public PizzaQuantity() {
    }

    public PizzaQuantity(Pizza pizza, int quantity) {
        this.pizza = pizza;
        this.quantity = quantity;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PizzaQuantity{" +
                "pizza=" + pizza +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PizzaQuantity that = (PizzaQuantity) o;
        return Objects.equals(pizza, that.pizza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pizza);
    }

}
