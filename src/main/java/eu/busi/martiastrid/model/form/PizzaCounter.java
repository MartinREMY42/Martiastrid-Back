package eu.busi.martiastrid.model.form;

import java.io.Serializable;
import java.util.HashMap;

public class PizzaCounter implements Serializable {
    private HashMap<Integer, Integer> orderedPizzas ;

    public PizzaCounter() {
    }

    public HashMap<Integer, Integer> getOrderedPizzas() {
        return orderedPizzas;
    }

    public void setOrderedPizzas(HashMap<Integer, Integer> orderedPizzas) {
        this.orderedPizzas = orderedPizzas;
    }
}
