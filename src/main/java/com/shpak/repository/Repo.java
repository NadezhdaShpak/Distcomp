package com.shpak.repository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public interface Repo<T> {
    AtomicLong idGenerator = new AtomicLong();

    Stream<T> getAll();

    Optional<T> get(Long id);

    Optional<T> create(T input);

    Optional<T> update(T input);

    boolean delete(Long id);
}