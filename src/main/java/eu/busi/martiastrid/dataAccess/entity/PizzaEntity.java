package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "pizzas")
public class PizzaEntity implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "generic_name")
    private String genericName;

    @Basic(optional = false)
    @Column(name = "price")
    private int price;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jt_pizza_category",
            joinColumns = @JoinColumn(name = "fk_pizza"),
            inverseJoinColumns = @JoinColumn(name = "fk_category"))
    private Set<CategoryEntity> categoryEntity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pizzaEntity", orphanRemoval = true)
    private Collection<RecipeEntity> recipeEntities;

    public PizzaEntity() {
    }

    public PizzaEntity(Integer id, String genericName, int price,
                       Collection<RecipeEntity> recipeEntities,
                       Set<CategoryEntity> categoryEntity) {
        this.id = id;
        this.genericName = genericName;
        this.price = price;
        this.recipeEntities = recipeEntities;
        this.categoryEntity = categoryEntity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<CategoryEntity> getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(Set<CategoryEntity> categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public Collection<RecipeEntity> getRecipeEntities() {
        return recipeEntities;
    }

    public void setRecipeEntities(Collection<RecipeEntity> recipeEntities) {
        this.recipeEntities = recipeEntities;
    }
}