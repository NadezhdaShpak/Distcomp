package com.shpak.service;

import com.shpak.dto.in.IssueRequestTo;
import com.shpak.dto.out.IssueResponseTo;
import com.shpak.mapper.IssueDto;
import com.shpak.repository.impl.IssueRepoImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class IssueService {

    public final IssueRepoImpl repoImpl;
    public final IssueDto mapper;

    public List<IssueResponseTo> getAll() {
        return repoImpl
                .getAll()
                .map(mapper::out)
                .toList();
    }

    public IssueResponseTo get(Long id) {
        return repoImpl
                .get(id)
                .map(mapper::out)
                .orElseThrow();
    }

    public IssueResponseTo create(IssueRequestTo input) {
        return repoImpl
                .create(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public IssueResponseTo update(IssueRequestTo input) {
        return repoImpl
                .update(mapper.in(input))
                .map(mapper::out)
                .orElseThrow();
    }

    public boolean delete(Long id) {
        return repoImpl.delete(id);
    }
}
