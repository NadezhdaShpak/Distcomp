package com.shpak.service;

import com.shpak.dto.in.EditorRequestTo;
import com.shpak.dto.out.EditorResponseTo;
import com.shpak.mapper.EditorDto;
import com.shpak.model.Editor;
import com.shpak.model.Issue;
import com.shpak.repository.impl.EditorRepoImpl;
import com.shpak.repository.impl.IssueRepoImpl;
import com.shpak.repository.impl.LabelRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class EditorService {

    public final EditorRepoImpl repoImpl;
    public final EditorDto mapper;
    public final IssueRepoImpl issueRepo;
    public final LabelRepoImpl labelRepo;

    @Transactional(readOnly = true)
    public List<EditorResponseTo> getAll() {
        return repoImpl
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public EditorResponseTo get(Long id) {
        Editor editor = repoImpl.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return mapper.out(editor);
    }

    @Transactional
    public EditorResponseTo create(EditorRequestTo input) {
        if (repoImpl.existsByLogin(input.getLogin())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        Editor saved = repoImpl
                .save(mapper.in(input));
        return mapper.out(saved);
    }

    @Transactional
    public EditorResponseTo update(EditorRequestTo input) {
        if (!repoImpl.existsById(input.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found");
        }
        Editor updated = repoImpl.save(mapper.in(input));
        return mapper.out(updated);
    }

    @Transactional
    public boolean delete(Long id) {
        Editor editor = repoImpl.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // Явное удаление связанных Issue (каскад)
        List<Issue> issues = editor.getIssues();
        issueRepo.deleteAll(issues);


        // Удаляем редактора
        repoImpl.delete(editor);

        // Удаляем оставшиеся метки без связей
        repoImpl.delete(editor);
        return true;
    }

    @Transactional
    public EditorResponseTo patch(Long id, EditorRequestTo input) {
        Editor existingEditor = repoImpl.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found"));
        Editor patchedEditor = mapper.applyPatch(input, existingEditor);
        Editor savedEditor = repoImpl.save(patchedEditor);
        return mapper.out(savedEditor);
    }

}
