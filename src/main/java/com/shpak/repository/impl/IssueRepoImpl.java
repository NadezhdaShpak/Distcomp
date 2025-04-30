package com.shpak.repository.impl;

import com.shpak.model.Issue;
import com.shpak.repository.Repo;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueRepoImpl extends Repo<Issue, Long> {

    boolean existsByTitle(@Size(min = 2, max = 64) String title);
}