package com.shpak.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_label", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 32)
    private String name;

    @OneToMany(mappedBy = "label",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<IssueLabel> labelIssues = new HashSet<>();

}