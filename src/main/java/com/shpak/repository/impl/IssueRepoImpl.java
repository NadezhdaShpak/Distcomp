package com.shpak.repository.impl;

import com.shpak.model.Issue;
import com.shpak.repository.Repo;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Repository
public class IssueRepoImpl implements Repo<Issue> {

    Map<Long, Issue> memoryDatabase = new ConcurrentHashMap<>();

    @Override
    public Stream<Issue> getAll() {
        return memoryDatabase.values().stream();
    }

    @Override
    public Optional<Issue> get(Long id) {
        return Optional.ofNullable(memoryDatabase.get(id));
    }

    @Override
    public Optional<Issue> create(Issue input) {
        long id = idGenerator.incrementAndGet();
        input.setId(id);
        memoryDatabase.put(id, input);
        return Optional.of(input);
    }

    @Override
    public Optional<Issue> update(Issue input) {
        memoryDatabase.put(input.getId(), input);
        return Optional.of(input);
    }

    @Override
    public boolean delete(Long id) {
        return memoryDatabase.remove(id) != null;
    }
}