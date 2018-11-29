package eu.busi.martiastrid.dataAccess.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@Entity
@Table(name = "authorities")
public class AuthorityEntity implements GrantedAuthority, Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "authority")
    private String authority;
    
    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Collection<UserEntity> users;

    public AuthorityEntity() {
    }

    public AuthorityEntity(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

}
