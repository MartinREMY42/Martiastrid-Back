package eu.busi.martiastrid.model.form;

import java.io.Serializable;

public class IdToEdit implements Serializable {
    private Integer id;

    public IdToEdit() {
    }

    public IdToEdit(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
