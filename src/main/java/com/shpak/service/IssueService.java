package com.shpak.service;

import com.shpak.dto.in.IssueRequestTo;
import com.shpak.dto.out.IssueResponseTo;
import com.shpak.mapper.IssueDto;
import com.shpak.model.Editor;
import com.shpak.model.Issue;
import com.shpak.model.IssueLabel;
import com.shpak.model.Label;
import com.shpak.repository.impl.EditorRepoImpl;
import com.shpak.repository.impl.IssueLabelRepoImpl;
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
    private final IssueLabelRepoImpl issueLabelRepo;
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
        Issue issue = repoImpl.findByIdWithLabels(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found"));
        return mapper.out(issue);
    }

    @Transactional
    public IssueResponseTo create(IssueRequestTo input) {
        if (repoImpl.existsByTitle(input.getTitle())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Issue issue = mapper.in(input);

        if (input.getEditorId() != null) {
            Editor editor = editorRepo.findById(input.getEditorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found"));
            issue.setEditor(editor);
        }
        // save Issue to get ID
        Issue savedIssue = repoImpl.save(issue);

        if (input.getLabels() != null && !input.getLabels().isEmpty()) {
            for (String labelName : input.getLabels()) {
                findOrCreateLabel(savedIssue, labelName);
            }
            // save issue with all links
            savedIssue = repoImpl.save(savedIssue);
        }
        return mapper.out(savedIssue);
    }

    @Transactional
    public IssueResponseTo update(IssueRequestTo input) {
        Issue existingIssue = repoImpl.findByIdWithLabels(input.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Issue not found"));

        existingIssue.setTitle(input.getTitle());
        existingIssue.setContent(input.getContent());

        // if editor changed, update
        if (input.getEditorId() != null &&
                (existingIssue.getEditor() == null || !existingIssue.getEditor().getId().equals(input.getEditorId()))) {
            Editor editor = editorRepo.findById(input.getEditorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editor not found"));
            existingIssue.setEditor(editor);
        }

        updateLabels(input, existingIssue);
        Issue updatedIssue = repoImpl.save(existingIssue);
        return mapper.out(updatedIssue);
    }

    private void updateLabels(IssueRequestTo input, Issue existingIssue) {
        if (input.getLabels() != null) {
            // delete all labels
            Set<IssueLabel> existingLabels = new HashSet<>(existingIssue.getIssueLabels());
            for (IssueLabel existingLabel : existingLabels) {
                existingIssue.removeIssueLabel(existingLabel);
                issueLabelRepo.delete(existingLabel);
            }
            for (String labelName : input.getLabels()) {
                findOrCreateLabel(existingIssue, labelName);
            }
        }
    }

    private void findOrCreateLabel(Issue existingIssue, String labelName) {
        Label label = labelRepo
                .findByName(labelName)
                .orElseGet(() ->
                        labelRepo.save(Label.builder()
                                .name(labelName)
                                .build()));

        IssueLabel issueLabel = IssueLabel.builder()
                .issue(existingIssue)
                .label(label)
                .build();

        existingIssue.addIssueLabel(issueLabel);
        label.addIssueLabel(issueLabel);
        issueLabelRepo.save(issueLabel);
    }

    @Transactional
    public boolean delete(Long id) {
        Issue issue = repoImpl.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));

        Set<Label> labelsToDelete = new HashSet<>();
        issue.getIssueLabels().forEach(link ->
                labelRepo.delete(link.getLabel())
        );

        repoImpl.delete(issue);
        labelRepo.deleteAll(labelsToDelete);
        return true;
    }
}
