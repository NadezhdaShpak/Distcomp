package com.shpak.repository.impl;

import com.shpak.model.IssueLabel;
import com.shpak.repository.Repo;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueLabelRepoImpl extends Repo<IssueLabel, Long> {

}