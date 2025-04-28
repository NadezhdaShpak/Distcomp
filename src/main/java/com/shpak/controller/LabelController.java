package com.shpak.controller;

import com.shpak.dto.in.LabelRequestTo;
import com.shpak.dto.out.LabelResponseTo;
import com.shpak.service.LabelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/labels")
public class LabelController {

    private final LabelService labelService;

    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @GetMapping
    public Collection<LabelResponseTo> getAll() {
        return labelService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LabelResponseTo create(@RequestBody @Valid LabelRequestTo inputDto) {
        return labelService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public LabelResponseTo update(@RequestBody @Valid LabelRequestTo inputDto) {
        try {
            return labelService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public LabelResponseTo read(@PathVariable long id) {
        return labelService.get(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = labelService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}