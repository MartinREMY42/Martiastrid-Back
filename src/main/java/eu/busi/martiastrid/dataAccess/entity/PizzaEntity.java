package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "recettes")
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
    @JoinTable(name = "jt_pizza_ingredient",
            joinColumns = @JoinColumn(name = "fk_pizza"),
            inverseJoinColumns = @JoinColumn(name = "fk_ingredient"))
    private Collection<IngredientEntity> ingredients;

    @Column(name = "custom")
    private Boolean custom;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jt_pizza_category",
                joinColumns = @JoinColumn(name = "fk_pizza"),
    inverseJoinColumns = @JoinColumn(name = "fk_category"))
    private Set<CategoryEntity> categoryEntity;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pizzaEntity",  orphanRemoval = true)
    private Collection<PortionEntity> portionEntities;

    public PizzaEntity() {
    }

    public PizzaEntity(Integer id, String genericName, int price,
                       Collection<IngredientEntity> ingredients, boolean custom,
                       Set<CategoryEntity> categoryEntity) {
        this.id = id;
        this.genericName = genericName;
        this.price = price;
        this.ingredients = ingredients;
        this.custom = custom;
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

    public Collection<IngredientEntity> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<IngredientEntity> ingredients) {
        this.ingredients = ingredients;
    }

    public Boolean isCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public Set<CategoryEntity> getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(Set<CategoryEntity> categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public Boolean getCustom() {
        return custom;
    }

    public Collection<PortionEntity> getPortionEntities() {
        return portionEntities;
    }

    public void setPortionEntities(Collection<PortionEntity> portionEntities) {
        this.portionEntities = portionEntities;
    }
}
