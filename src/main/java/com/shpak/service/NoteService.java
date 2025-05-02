package com.shpak.service;

import com.shpak.dto.in.NoteRequestTo;
import com.shpak.dto.out.NoteResponseTo;
import com.shpak.mapper.NoteDto;
import com.shpak.model.Note;
import com.shpak.repository.impl.NoteRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    public final NoteRepoImpl repoImpl;
    public final NoteDto mapper;

    @Transactional(readOnly = true)
    public List<NoteResponseTo> getAll() {
        return repoImpl
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public NoteResponseTo get(Long id) {
        Note note = repoImpl.findById(id)
                .orElseThrow();
        return mapper.out(note);
    }

    @Transactional
    public NoteResponseTo create(NoteRequestTo input) {
        Note saved =  repoImpl
                .save(mapper.in(input));
        return mapper.out(saved);
    }

    @Transactional
    public NoteResponseTo update(NoteRequestTo input) {
        if (!repoImpl.existsById(input.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found with id: " + input.getId());
        }
        Note updated = repoImpl.save(mapper.in(input));
        return mapper.out(updated);
    }

    @Transactional
    public boolean delete(Long id) {
        if (!repoImpl.existsById(id)) {
            return false;
        }
        repoImpl.deleteById(id);
        return true;
    }
}
