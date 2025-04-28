package com.shpak.mapper;

import com.shpak.dto.in.EditorRequestTo;
import com.shpak.dto.out.EditorResponseTo;
import com.shpak.model.Editor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EditorDto {
    EditorResponseTo out(Editor entity);

    Editor in(EditorRequestTo inputDto);
}