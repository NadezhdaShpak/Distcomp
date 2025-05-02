package com.shpak.mapper;

import com.shpak.dto.in.IssueRequestTo;
import com.shpak.dto.out.IssueResponseTo;
import com.shpak.model.Editor;
import com.shpak.model.Issue;
import com.shpak.model.IssueLabel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IssueDto {
    @Mapping(source = "editor.id", target = "editorId")
    @Mapping(source = "issueLabels", target = "labels", qualifiedByName = "mapLabels")
    IssueResponseTo out(Issue entity);

    @Mapping(target = "issueLabels", ignore = true) // miss when create from DTO
    @Mapping(target = "editor", source = "editorId", qualifiedByName = "idToEditor")
    Issue in(IssueRequestTo inputDto);

    @Named("mapLabels")
    static List<String> mapLabels(Set<IssueLabel> issueLabels) {
        return issueLabels.stream()
                .map(il ->
                        il.getLabel().getName()
                )
                .collect(Collectors.toList());
    }

    @Named("idToEditor")
    static Editor idToEditor(Long id) {
        if (id == null) return null;
        return Editor.builder().id(id).build();
    }
}
