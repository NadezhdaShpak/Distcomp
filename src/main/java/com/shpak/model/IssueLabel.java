package com.shpak.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_issue_label")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueLabel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "issue_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "label_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Label label;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime modified;
}
