package com.shpak.repository.impl;

import com.shpak.model.Issue;
import com.shpak.repository.Repo;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepoImpl extends Repo<Issue, Long> {

    boolean existsByTitle(@Size(min = 2, max = 64) String title);

    @Query("SELECT i FROM Issue i LEFT JOIN FETCH i.issueLabels WHERE i.id = :id")
    Optional<Issue> findByIdWithLabels(@Param("id") Long id);

}