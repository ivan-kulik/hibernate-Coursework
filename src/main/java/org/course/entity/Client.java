package org.course.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
import java.util.List;

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

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    private Profile profile;

    public Client(String name, String email) {
        this.name = name;
        this.email = email;
        this.registrationDate = LocalDate.now();
    }
}
