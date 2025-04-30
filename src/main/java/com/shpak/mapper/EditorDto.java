package com.shpak.mapper;

import com.shpak.dto.in.EditorRequestTo;
import com.shpak.dto.out.EditorResponseTo;
import com.shpak.model.Editor;
import com.shpak.model.Issue;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EditorDto {
    @Mapping(source = "issues", target = "issueIds", qualifiedByName = "mapIssueIds")
    EditorResponseTo out(Editor entity);

    @Mapping(target = "issues", ignore = true)
    Editor in(EditorRequestTo inputDto);

    @Named("mapIssueIds")
    static List<Long> mapIssueIds(List<Issue> issues) {
        return issues.stream()
                .map(Issue::getId)
                .collect(Collectors.toList());
    }

    default Editor applyPatch(EditorRequestTo dto, Editor editor) {
        if (dto.getLogin() != null) {
            editor.setLogin(dto.getLogin());
        }
        if (dto.getFirstname() != null) {
            editor.setFirstname(dto.getFirstname());
        }
        if (dto.getLastname() != null) {
            editor.setLastname(dto.getLastname());
        }
        if (dto.getPassword() != null) {
            editor.setPassword(dto.getPassword());
        }
        return editor;
    }
}