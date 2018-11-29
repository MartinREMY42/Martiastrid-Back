package eu.busi.martiastrid.model;

import java.util.Objects;

public class PromoInfo {
    public final String name;
    public final int reduction;

    public PromoInfo(String name, int reduction) {
        this.name = name;
        this.reduction = reduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromoInfo promoInfo = (PromoInfo) o;
        return reduction == promoInfo.reduction &&
                Objects.equals(name, promoInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, reduction);
    }

    @Override
    public String toString() {
        return "PromoInfo{" +
                "name='" + name + '\'' +
                ", reduction=" + reduction +
                '}';
    }
}
