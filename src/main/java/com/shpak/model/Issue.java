package com.shpak.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tbl_issue",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"title"}))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private Editor editor;

    @Column(nullable = false, length = 64, unique = true)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT", length = 2048)
    private String content;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modified;

    @OneToMany(mappedBy = "issue",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<IssueLabel> issueLabels = new HashSet<>();

    // Helper method to properly manage the relationship
    public void addIssueLabel(IssueLabel issueLabel) {
        issueLabels.add(issueLabel);
        issueLabel.setIssue(this);
    }

    // Helper method to properly remove a label
    public void removeIssueLabel(IssueLabel issueLabel) {
        issueLabels.remove(issueLabel);
        issueLabel.setIssue(null);
    }
}