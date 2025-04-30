package com.shpak.mapper;

import com.shpak.model.Issue;
import com.shpak.repository.impl.IssueRepoImpl;
import org.mapstruct.Named;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class NoteMapperHelper {

    private final IssueRepoImpl issueRepository;

    public NoteMapperHelper(IssueRepoImpl issueRepository) {
        this.issueRepository = issueRepository;
    }

    @Named("issueIdToIssue")
    public Issue issueIdToIssue(Long issueId) {
        return issueRepository.findById(issueId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}