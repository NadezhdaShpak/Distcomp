package com.shpak.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbl_editor", uniqueConstraints = {
        @UniqueConstraint(columnNames = "login")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String login;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(nullable = false, length = 64)
    private String firstname;

    @Column(nullable = false, length = 64)
    private String lastname;

    @OneToMany(mappedBy = "editor",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Issue> issues = new ArrayList<>();
}