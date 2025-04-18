package com.ajou.op.domain.user;

import com.ajou.op.domain.AuditingFields;
import com.ajou.op.domain.Part;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User  extends AuditingFields {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToOne(fetch = FetchType.LAZY)
    private Part part;


    public void changeRole(UserRole role) {
        this.role = role;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    @Builder
    public User(String email, String name, String password, UserRole role, Part part) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.part = part;
    }
}
