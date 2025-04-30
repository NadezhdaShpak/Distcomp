package com.shpak.service;

import com.shpak.dto.in.LabelRequestTo;
import com.shpak.dto.out.LabelResponseTo;
import com.shpak.mapper.LabelDto;
import com.shpak.model.Label;
import com.shpak.repository.impl.IssueLabelRepoImpl;
import com.shpak.repository.impl.LabelRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LabelService {

    public final LabelRepoImpl repoImpl;
    public final LabelDto mapper;
    private final IssueLabelRepoImpl issueLabelRepo;

    @Transactional(readOnly = true)
    public List<LabelResponseTo> getAll() {
        return repoImpl
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public LabelResponseTo get(Long id) {
        Label label = repoImpl.findById(id)
                .orElseThrow();
        return mapper.out(label);
    }

    @Transactional
    public LabelResponseTo create(LabelRequestTo input) {
        Label saved = repoImpl
                .save(mapper.in(input));
        return mapper.out(saved);
    }

    @Transactional
    public LabelResponseTo update(LabelRequestTo input) {
        if (!repoImpl.existsById(input.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found");
        }
        Label updated = repoImpl.save(mapper.in(input));
        return mapper.out(updated);
    }

    @Transactional
    public boolean delete(Long id) {
        Label label = repoImpl.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));

        issueLabelRepo.deleteAll(label.getLabelIssues());
        repoImpl.delete(label);
        return true;
    }

    @Transactional(readOnly = true)
    public LabelResponseTo findByName(String name) {
        try {
            Label label = repoImpl.findByName(name).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));
            return mapper.out(label);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
