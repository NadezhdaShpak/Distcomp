package com.shpak.mapper;

import com.shpak.dto.in.LabelRequestTo;
import com.shpak.dto.out.LabelResponseTo;
import com.shpak.model.Label;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabelDto {
    LabelResponseTo out(Label entity);

    Label in(LabelRequestTo inputDto);
}