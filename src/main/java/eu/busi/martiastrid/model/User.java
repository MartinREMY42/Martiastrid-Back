package eu.busi.martiastrid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;

public class User {
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean nonExpired;
    private Boolean nonLocked;
    private Boolean credentialsNonExpired;
    private Collection<Authority> authorities;
    private Date birthDate;
    private Date creationDate;
    private Collection<Order> orders;
    private Collection<Pizza> pizzasFavorites;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.enabled = true;
        this.nonExpired = true;
        this.nonLocked = true;
        this.credentialsNonExpired = true;
        this.authorities = new ArrayList<>();
    }

    public User(String username, String password, Boolean enabled, Boolean nonExpired,
                Boolean nonLocked, Boolean credentialsNonExpired, Collection<Authority> authorities,
                Date birthDate, Date creationDate, Collection<Order> orders) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
        this.birthDate = birthDate;
        this.creationDate = creationDate;
        this.orders = orders;
    }

    public User(String username, String password, Boolean enabled, Boolean nonExpired, Boolean nonLocked,
                Boolean credentialsNonExpired, Collection<Authority> authorities,
                Date birthDate, Date creationDate, Collection<Order> orders,
                Collection<Pizza> pizzasFavorites) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.nonExpired = nonExpired;
        this.nonLocked = nonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.authorities = authorities;
        this.birthDate = birthDate;
        this.creationDate = creationDate;
        this.orders = orders;
        this.pizzasFavorites = pizzasFavorites;
    }

    public boolean addAuthority(Authority authority) {
        if (Objects.isNull(authorities)) {
            authorities = new ArrayList<>();
        }
        if (!authorities.contains(authority)) {
            authorities.add(authority);
            return true;
        }
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getNonExpired() {
        return nonExpired;
    }

    public void setNonExpired(Boolean nonExpired) {
        this.nonExpired = nonExpired;
    }

    public Boolean getNonLocked() {
        return nonLocked;
    }

    public void setNonLocked(Boolean nonLocked) {
        this.nonLocked = nonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Collection<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Authority> authorities) {
        this.authorities = authorities;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
        System.out.println("-------------------------------------------------\ndate de naissance : " + birthDate);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Collection<Order> getOrders() {
        return orders;
    }

    public void setOrders(Collection<Order> orders) {
        this.orders = orders;
    }

    public Collection<Pizza> getPizzasFavorites() {
        return pizzasFavorites;
    }

    public void setPizzasFavorites(Collection<Pizza> pizzasFavorites) {
        this.pizzasFavorites = pizzasFavorites;
    }
}
