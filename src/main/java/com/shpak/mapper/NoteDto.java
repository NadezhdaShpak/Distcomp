package com.shpak.mapper;

import com.shpak.dto.in.NoteRequestTo;
import com.shpak.dto.out.NoteResponseTo;
import com.shpak.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteDto {
    NoteResponseTo out(Note entity);

    Note in(NoteRequestTo inputDto);
}