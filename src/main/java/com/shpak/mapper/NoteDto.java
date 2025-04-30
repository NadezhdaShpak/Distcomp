package com.shpak.mapper;

import com.shpak.dto.in.NoteRequestTo;
import com.shpak.dto.out.NoteResponseTo;
import com.shpak.model.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {NoteMapperHelper.class})
public interface NoteDto {

    @Mapping(source = "issue.id", target = "issueId")
    @Mapping(source = "created", target = "created")
    @Mapping(source = "modified", target = "modified")
    NoteResponseTo out(Note entity);

    @Mapping(
            target = "issue",
            source = "issueId",
            qualifiedByName = "issueIdToIssue"
    )
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "modified", ignore = true)
    Note in(NoteRequestTo inputDto);
}