package com.shpak.mapper;

import com.shpak.dto.in.LabelRequestTo;
import com.shpak.dto.out.LabelResponseTo;
import com.shpak.model.IssueLabel;
import com.shpak.model.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface LabelDto {
    @Mapping(source = "labelIssues",
            target = "issues",
            qualifiedByName = "mapIssues")
    LabelResponseTo out(Label entity);

    @Mapping(target = "labelIssues", ignore = true)
    Label in(LabelRequestTo inputDto);

    @Named("mapIssues")
    static List<Long> mapIssues(Set<IssueLabel> labelIssues) {
        return labelIssues.stream()
                .map(issueLabel -> issueLabel.getIssue().getId())
                .collect(Collectors.toList());
    }
}
