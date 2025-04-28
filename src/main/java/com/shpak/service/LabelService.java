package com.shpak.service;

import com.shpak.dto.in.LabelRequestTo;
import com.shpak.dto.out.LabelResponseTo;
import com.shpak.mapper.LabelDto;
import com.shpak.repository.impl.LabelRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LabelService {

    public final LabelRepoImpl repoImpl;
    public final LabelDto mapper;

    public List<LabelResponseTo> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public LabelResponseTo get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public LabelResponseTo create(LabelRequestTo input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public LabelResponseTo update(LabelRequestTo input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
