package org.course.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name = "name",
            nullable = false,
            unique = true)
    private String name;

    @Column(name = "email",
            nullable = false,
            unique = true)
    private String email;

    @Column(name = "registration_date",
            nullable = false)
    @CreationTimestamp
    private LocalDate registrationDate;

    @OneToOne(
            mappedBy = "client",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private Profile profile;

    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "client_coupons",
            joinColumns = @JoinColumn(
                    name = "client_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "coupon_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Coupon> coupons = new HashSet<>();

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setProfile(Profile profile) {
        if (profile != null) {
            profile.setClient(this);
        }
        this.profile = profile;
    }
}
