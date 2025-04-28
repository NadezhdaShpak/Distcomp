package com.shpak.mapper;

import com.shpak.dto.in.IssueRequestTo;
import com.shpak.dto.out.IssueResponseTo;
import com.shpak.model.Issue;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IssueDto {
    IssueResponseTo out(Issue entity);

    Issue in(IssueRequestTo inputDto);
}