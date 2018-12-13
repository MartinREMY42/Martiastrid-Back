package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "recipes")
public class RecipeEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "quantity")
    private int quantity;

    @JoinColumn(name = "fk_pizza", referencedColumnName = "id")
    @ManyToOne()
    private PizzaEntity pizzaEntity;

    @JoinColumn(name = "fk_ingredient", referencedColumnName = "id")
    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.REMOVE})
    private IngredientEntity ingredientEntity;

    public RecipeEntity(){}

    public RecipeEntity(Integer id, int quantity, PizzaEntity pizzaEntity, IngredientEntity ingredientEntity) {
        this.id = id;
        this.quantity = quantity;
        this.pizzaEntity = pizzaEntity;
        this.ingredientEntity = ingredientEntity;
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

    public PizzaEntity getPizzaEntity() {
        return pizzaEntity;
    }

    public void setPizzaEntity(PizzaEntity pizzaEntity) {
        this.pizzaEntity = pizzaEntity;
    }

    public IngredientEntity getIngredientEntity() {
        return ingredientEntity;
    }

    public void setIngredientEntity(IngredientEntity ingredientEntity) {
        this.ingredientEntity = ingredientEntity;
    }
}