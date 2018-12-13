package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "ingredients")
public class IngredientEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "generic_name")
    private String genericName;
    
    @Basic(optional = false)
    @Column(name = "stock_quantity")
    private int stockQuantity;
    
    @Basic(optional = false)
    @Column(name = "price_for_composition")
    private int priceForComposition;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ingredientEntity",  orphanRemoval = true)
    private Collection<RecipeEntity> recipeEntities;

    public IngredientEntity() {
    }

    public IngredientEntity(Integer id, String genericName, int stockQuantity, int priceForComposition) {
        this.id = id;
        this.genericName = genericName;
        this.stockQuantity = stockQuantity;
        this.priceForComposition = priceForComposition;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getPriceForComposition() {
        return priceForComposition;
    }

    public void setPriceForComposition(int priceForComposition) {
        this.priceForComposition = priceForComposition;
    }

    public Collection<RecipeEntity> getRecipeEntities() {
        return recipeEntities;
    }

    public void setRecipeEntities(Collection<RecipeEntity> recipeEntities) {
        this.recipeEntities = recipeEntities;
    }

    @Override
    public String toString() {
        return "IngredientEntity{" +
                "id=" + id +
                ", genericName='" + genericName + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", priceForComposition=" + priceForComposition +
                "}\n";
    }
}
