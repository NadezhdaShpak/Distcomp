package com.shpak.repository.impl;

import com.shpak.model.Note;
import com.shpak.repository.Repo;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepoImpl extends Repo<Note, Long> {

}