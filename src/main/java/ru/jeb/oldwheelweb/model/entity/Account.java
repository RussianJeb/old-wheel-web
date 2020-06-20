package ru.jeb.oldwheelweb.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Jeb
 */
@Data
@Entity
@NoArgsConstructor
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String password;
    private String email;
    private Date createdAt;
    private String contact;
    private boolean enabled = false;

    public Account(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Account(String username, String password, String email, String contact) {
        this(username, password, email);
        this.contact = contact;
    }

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SELECT)
    private Set<Player> players;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public enum Role {
        ADMIN("USER", "ADMIN"), USER("USER");
        private final List<String> roles;

        Role(String... roles) {
            this.roles = Arrays.asList(roles);
        }

        Collection<? extends GrantedAuthority> getAuthorities() {
            return roles.stream()
                    .map(s -> "ROLE_" + s)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }

    @PrePersist
    public void install() {
        this.createdAt = new Date();
    }
}
