package eu.busi.martiastrid.dataAccess.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "users")
public class UserEntity implements UserDetails, Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "username")
    private String username;

    @Basic(optional = false)
    @Column(name = "password")
    private String password;

    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled;

    @Basic(optional = false)
    @Column(name = "non_expired")
    private boolean accountNonExpired;

    @Basic(optional = false)
    @Column(name = "non_locked")
    private boolean accountNonLocked;

    @Basic(optional = false)
    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;

    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)

    private Date creationDate;
    
    @Basic(optional = false)
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user")
    private Set<OrderEntity> ordersCollection;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jt_user_authorities ",
                joinColumns = @JoinColumn(name = "fk_user"),
                inverseJoinColumns = @JoinColumn(name = "fk_authority"))
    private Set<AuthorityEntity> authorities;

    public UserEntity() {
    }

    public UserEntity(String username) {
        this.username = username;
    }


    public UserEntity(String username, String password, boolean enabled, boolean accountNonExpired,
                      boolean accountNonLocked, boolean credentialsNonExpired, Date creationDate,
                      Date birthDate, Set<AuthorityEntity> authorities, Set<OrderEntity> orderEntities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.creationDate = creationDate;
        this.birthDate = birthDate;
        this.authorities = authorities;
        this.ordersCollection = orderEntities;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        System.out.println("-------------------------------------------------\ndate de naissance : " + birthDate);
        this.birthDate = birthDate;
    }

    public Collection<OrderEntity> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Set<OrderEntity> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }


}
