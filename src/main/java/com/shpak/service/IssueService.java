package com.shpak.service;

import com.shpak.dto.in.IssueRequestTo;
import com.shpak.dto.out.IssueResponseTo;
import com.shpak.mapper.IssueDto;
import com.shpak.model.Editor;
import com.shpak.model.Issue;
import com.shpak.model.IssueLabel;
import com.shpak.model.Label;
import com.shpak.repository.impl.EditorRepoImpl;
import com.shpak.repository.impl.IssueRepoImpl;
import com.shpak.repository.impl.LabelRepoImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class IssueService {

    public final IssueRepoImpl repoImpl;
    public final EditorRepoImpl editorRepo;
    public final LabelRepoImpl labelRepo;
    public final IssueDto mapper;

    @Transactional(readOnly = true)
    public List<IssueResponseTo> getAll() {
        return repoImpl
                .findAll()
                .stream()
                .map(mapper::out)
                .toList();
    }

    @Transactional(readOnly = true)
    public IssueResponseTo get(Long id) {
        Issue issue = repoImpl.findById(id)
                .orElseThrow();
        return mapper.out(issue);
    }

    @Transactional
    public IssueResponseTo create(IssueRequestTo input) {
        // Проверка наличия заголовка
        if (repoImpl.existsByTitle(input.getTitle())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // Создание или получение меток
        Set<Label> labels = new HashSet<>();
        if (input.getLabels() != null) {
            for (String labelName : input.getLabels()) {
                Label label = labelRepo.findByName(labelName)
                        .orElseGet(() -> {
                            log.debug("MY LOG Creating new label: {}", labelName);
                            return labelRepo.save(Label.builder().name(labelName).build());
                        });
                log.debug("MY LOG Label added: {}", label.getName());
                labels.add(label);
            }
        }

        // Создание Issue
        Issue issue = mapper.in(input);

        // Установка редактора
        if (input.getEditorId() != null) {
            Editor editor = editorRepo.findById(input.getEditorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found"));
            issue.setEditor(editor);
        }

        // Связывание меток
        labels.forEach(label -> {
            IssueLabel link = IssueLabel.builder()
                    .issue(issue)
                    .label(label)
                    .build();
            log.debug("MY LOG Linking issue {} to label {}", issue.getId(), label.getName());
            issue.getIssueLabels().add(link);
        });

        Issue savedIssue = repoImpl.save(issue);
        return mapper.out(savedIssue);
    }

    @Transactional
    public IssueResponseTo update(IssueRequestTo input) {
        if (!repoImpl.existsById(input.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found");
        }
        Issue updated = repoImpl.save(mapper.in(input));
        return mapper.out(updated);
    }

    @Transactional
    public boolean delete(Long id) {
        Issue issue = repoImpl.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));

        Set<Label> labelsToDelete = new HashSet<>();
        issue.getIssueLabels().forEach(link -> {
            Label label = link.getLabel();
            log.debug(label.getName());
            labelRepo.delete(label);
        });

        // Удаляем задачу
        repoImpl.delete(issue);

        // Удаляем метки без связей
        labelRepo.deleteAll(labelsToDelete);

        return true;
    }
}
