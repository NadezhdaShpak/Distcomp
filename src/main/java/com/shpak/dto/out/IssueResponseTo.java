package com.shpak.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponseTo {
    Long id;
    Long editorId;
    String title;
    String content;
    LocalDateTime created;
    LocalDateTime modified;
    private List<String> labels;
}
