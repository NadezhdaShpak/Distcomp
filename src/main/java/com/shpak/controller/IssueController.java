package com.shpak.controller;

import com.shpak.dto.in.IssueRequestTo;
import com.shpak.dto.out.IssueResponseTo;
import com.shpak.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public Collection<IssueResponseTo> getAll() {
        return issueService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IssueResponseTo create(@RequestBody @Valid IssueRequestTo inputDto) {
        return issueService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public IssueResponseTo update(@RequestBody @Valid IssueRequestTo inputDto) {
        try {
            return issueService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public IssueResponseTo read(@PathVariable long id) {
        return issueService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = issueService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}