package com.textify.textify.entity;



import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 4071234728582967483L;


    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 4, max=255)
    @UniqueUsername
    private String username;

    @NotNull
    @Size(min = 4, max=255)
    private String nickname;

    @NotNull
    @Size(min = 8, max=255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
    private String password;


    private String image;

    @OneToMany(mappedBy = "user")
    private List<Post> post;

    @Override
    @Transient
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("Role_USER");
    }

    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @Transient
    public boolean isEnabled() {
        return true;
    }
}

