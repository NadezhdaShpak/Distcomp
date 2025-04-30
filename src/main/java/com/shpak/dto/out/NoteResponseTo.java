package com.shpak.dto.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponseTo {
    Long id;
    Long issueId;
    String content;
    private LocalDateTime created;
    private LocalDateTime modified;
}
