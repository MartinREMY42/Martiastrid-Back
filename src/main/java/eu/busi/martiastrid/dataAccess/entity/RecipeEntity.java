package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "recettes")
public class RecipeEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;

    @JoinColumn(name = "fk_pizza", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private PizzaEntity fkPizza;

    @JoinColumn(name = "fk_ingredient", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    private IngredientEntity fkIngredient;

    public RecipeEntity(){}

    public RecipeEntity(Integer id, int quantity, PizzaEntity fkPizza, IngredientEntity fkIngredient) {
        this.id = id;
        this.quantity = quantity;
        this.fkPizza = fkPizza;
        this.fkIngredient = fkIngredient;
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

    public PizzaEntity getFkPizza() {
        return fkPizza;
    }

    public void setFkPizza(PizzaEntity fkPizza) {
        this.fkPizza = fkPizza;
    }

    public IngredientEntity getFkIngredient() {
        return fkIngredient;
    }

    public void setFkIngredient(IngredientEntity fkIngredient) {
        this.fkIngredient = fkIngredient;
    }
}