package com.shpak.repository.impl;

import com.shpak.model.Label;
import com.shpak.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class LabelRepoImpl implements Repo<Label> {

    Map<Long, Label> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Label> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Label> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Label> create(Label input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Label> update(Label input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}