package com.shpak.repository.impl;

import com.shpak.model.Label;
import com.shpak.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepoImpl extends Repo<Label, Long> {
    Optional<Label> findByName(String name);
}