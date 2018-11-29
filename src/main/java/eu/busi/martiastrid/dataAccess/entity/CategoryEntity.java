package eu.busi.martiastrid.dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "pizza_category")
public class CategoryEntity {

    @Id
    @Column(name = "category")
    private String category;

    public CategoryEntity() {
    }

    public CategoryEntity(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
