package com.shpak.controller;

import com.shpak.dto.in.EditorRequestTo;
import com.shpak.dto.out.EditorResponseTo;
import com.shpak.service.EditorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1.0/editors")
public class EditorController {

    private final EditorService editorService;

    public EditorController(EditorService editorService) {
        this.editorService = editorService;
    }

    @GetMapping
    public Collection<EditorResponseTo> getAll() {
        return editorService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EditorResponseTo create(@RequestBody @Valid EditorRequestTo inputDto) {
        return editorService.create(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public EditorResponseTo update(@RequestBody @Valid EditorRequestTo inputDto) {
        try {
            return editorService.update(inputDto);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public EditorResponseTo read(@PathVariable long id) {
        try {
            return editorService.get(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        boolean delete = editorService.delete(id);
        if (!delete) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public EditorResponseTo patchEditor(
            @PathVariable Long id,
            @RequestBody EditorRequestTo patchRequest
    ) {
        return editorService.patch(id, patchRequest);
    }

}