package eu.busi.martiastrid.model;

import java.io.Serializable;
import java.util.Objects;

public class Ingredient implements Serializable {
    private Integer id;
    private String genericName;
    private Integer stockQuantity;
    private Integer priceForComposition;
    private Boolean selected;

    public Ingredient() {
    }

    public Ingredient(Integer id, String genericName, Integer stockQuantity,
                      Integer priceForComposition) {
        this.id = id;
        this.genericName = genericName;
        this.stockQuantity = stockQuantity;
        this.priceForComposition = priceForComposition;
        this.selected = false;
    }

    public boolean deCreaseStock(){
        return deCreaseStock(1);
    }

    public boolean deCreaseStock(Integer quantity){
        stockQuantity -= quantity;
        return stockQuantity > 0;
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

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getPriceForComposition() {
        return priceForComposition;
    }

    public void setPriceForComposition(Integer priceForComposition) {
        this.priceForComposition = priceForComposition;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Ingredient{" + genericName + '}';
    }
}
