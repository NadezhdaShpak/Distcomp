package com.shpak.service;

import com.shpak.dto.in.NoteRequestTo;
import com.shpak.dto.out.NoteResponseTo;
import com.shpak.mapper.NoteDto;
import com.shpak.repository.impl.NoteRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {

    public final NoteRepoImpl repoImpl;
    public final NoteDto mapper;

    public List<NoteResponseTo> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public NoteResponseTo get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public NoteResponseTo create(NoteRequestTo input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public NoteResponseTo update(NoteRequestTo input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
